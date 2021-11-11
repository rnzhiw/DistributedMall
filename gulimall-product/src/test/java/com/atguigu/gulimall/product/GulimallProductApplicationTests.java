package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

//    @Autowired
//    OSSClient ossClient;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void redisson() {
        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello","world_" + UUID.randomUUID().toString());
        String hello = ops.get("hello");
        System.out.println(hello);
    }

    @Test
    public void testFindPath() {
        Long[] catelogPath = categoryService.findCatelogPath(165L);
        log.info("生成的完整的路径:{}", Arrays.asList(catelogPath));
    }

//    @Test
//    public void testUpload() throws FileNotFoundException {
////        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
////        String endpoint = "oss-cn-shanghai.aliyuncs.com";
////        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
////        String accessKeyId = "LTAI5tK2J2G7wpqgeoRrGRiR";
////        String accessKeySecret = "JQF8NEs8WvbJyCC1udMgy6KLwqnMuQ";
////
////        // 创建OSSClient实例。
////        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//
//
//        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        InputStream inputStream = new FileInputStream("D:\\本科时期材料\\要打包的材料\\本科杂七杂八的文档\\个人照片\\IMG_0224.JPG");
//        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
//        ossClient.putObject("gulimall-rnzhiw", "IMG_0224.JPG", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//
//        System.out.println("上传完成");
//    }

    @Test
    public void contextLoads() {

//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("aaa");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        list.forEach((item)->{
            System.out.println(item);
        });

    }

}
