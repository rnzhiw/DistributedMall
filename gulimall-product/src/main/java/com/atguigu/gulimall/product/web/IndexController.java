package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping({"/","/index.html"})
    public String index(Model model) {

        // 查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();

        model.addAttribute("categorys",categoryEntities);

        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> map = categoryService.getCatalogJson();

        return map;
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {

        // 获取一把锁，只要锁的名字一样，就是一把锁
        RLock lock = redissonClient.getLock("my-lock");

        // 加锁,阻塞式等待
        lock.lock();
        try {
            System.out.println("加锁成功，执行业务" + Thread.currentThread().getId());
            Thread.sleep(3000);
        } catch (Exception e) {

        } finally {
            // 解锁
            System.out.println("释放锁" + Thread.currentThread().getId());
            lock.unlock();
        }

        return "hello";
    }

    @GetMapping("/write")
    @ResponseBody
    public String write() {

        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        String s = "";
        // 改数据加写锁，读数据加读锁
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
            System.out.println("写锁加锁成功");
            s = UUID.randomUUID().toString();
            stringRedisTemplate.opsForValue().set("write",s);
            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
            rLock.unlock();
        }

        return s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String read() {

        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");

        String s = "";
        // 加读锁
        RLock rLock = lock.readLock();
        rLock.lock();
        System.out.println("读锁加锁成功");
        try {
            s = stringRedisTemplate.opsForValue().get("write");
            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
            rLock.unlock();
        }

        return s;
    }

    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        park.acquire();
        return "ok";
    }

    @ResponseBody
    @GetMapping("/go")
    public String go() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        park.release();
        return "ok";
    }

    @ResponseBody
    @GetMapping("/lockDoor")
    public String lockDoor() throws InterruptedException {

        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.trySetCount(5);
        door.await(); // 等待闭锁都完成

        return "放假了";
    }

    @ResponseBody
    @GetMapping("/gogogo/{id}")
    public String gogogo(@PathVariable("id") Long id) {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.countDown();//计数减一
        return id + "班的人都走了";
    }

}
