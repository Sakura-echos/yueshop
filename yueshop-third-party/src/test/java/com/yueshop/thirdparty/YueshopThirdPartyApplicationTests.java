package com.yueshop.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class YueshopThirdPartyApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-guangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tA1zWkqZPUUuSUjATNZ";
        String accessKeySecret = "KD301L5rRCvZV0cLXvx2jqJbus8ITy";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写字符串。
        //        String content = "Hello OSS";

        FileInputStream stream = new FileInputStream("E:\\Program Files\\YQ\\file\\OIP.jpg");
        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        PutObjectRequest putObjectRequest = new PutObjectRequest("alen-bucket", "exampledir/exampleobject.txt", stream);

        /*
         如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
         ObjectMetadata metadata = new ObjectMetadata();
         metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
         metadata.setObjectAcl(CannedAccessControlList.Private);
         putObjectRequest.setMetadata(metadata);
         上传字符串。
        */

        ossClient.putObject(putObjectRequest);
        System.out.println("上传成功！！！！！！！");

// 关闭OSSClient。
        ossClient.shutdown();
    }

    @Autowired
    OSSClient ossClient;

    @Test
    public void ossClientUpload() throws FileNotFoundException {
        //创建文件流
        FileInputStream stream = new FileInputStream("C:\\Users\\Alen\\Desktop\\静态模板\\dergsfdbhgds\\img\\blog-3.jpg");
        //传输数据导存储空间
        ossClient.putObject("alen-bucket", "blog-3.jpg", stream);
        //关闭数据流
        ossClient.shutdown();
        System.out.println("提交成功");

    }

}
