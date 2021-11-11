package com.atguigu.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import com.atguigu.gulimall.thirdparty.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GulimallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testSendSms() {
        smsComponent.sendSmsCode("13588891714","123");
    }

    @Test
    public void sendSms() {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "f9440577f83c4c2c877e27b2d49868fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:1313,expire_at:5");
        bodys.put("phone_number", "13588891714");
        bodys.put("template_id", "TPL_0001");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpload() throws FileNotFoundException {
//        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "oss-cn-shanghai.aliyuncs.com";
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "LTAI5tK2J2G7wpqgeoRrGRiR";
//        String accessKeySecret = "JQF8NEs8WvbJyCC1udMgy6KLwqnMuQ";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);



        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("D:\\本科时期材料\\要打包的材料\\本科杂七杂八的文档\\个人照片\\IMG_0224.JPG");
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("gulimall-rnzhiw", "hahaha.JPG", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传完成");
    }

}
