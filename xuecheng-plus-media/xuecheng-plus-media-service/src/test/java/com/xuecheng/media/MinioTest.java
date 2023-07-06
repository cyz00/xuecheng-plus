package com.xuecheng.media;

import com.alibaba.nacos.common.http.param.MediaType;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;

/**
 * @description 测试minio
 * @author zcy
 * @date 2023/7/4
 */
public class MinioTest {
    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
    @Test
    public void test_upload() throws Exception{
        //通过扩展名得到媒体资源类型
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".avi");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM;//通用mimeType
        if(extensionMatch!=null){
           mimeType = extensionMatch.getMimeType();
        }
        //上传文件的参数信息
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket("testbucket") //桶
                .filename("C:\\Users\\tianlei\\Desktop\\DB_demo\\src\\out.avi")//指定本地文件的路径
                .object("out.avi")//对象名
//                .contentType("video/avi")
                .contentType(mimeType)
                .build();
        //上传文件
        minioClient.uploadObject(uploadObjectArgs);
    }
    //删除文件
    @Test
    public void test_delete() throws Exception{
        //删除文件的参数信息
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket("testbucket").object("out.avi").build();
        //删除文件
        minioClient.removeObject(removeObjectArgs);
    }
    //查询文件，从minio中下载
    @Test
    public void test_getFile() throws Exception{
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("out.avi").build();
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        //指定输出流
        FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\tianlei\\Videos\\out.avi"));
        IOUtils.copy(inputStream,outputStream);
        //校验文件完整性，对文件进行md5
        String source_md5 = DigestUtils.md5Hex(inputStream);//minio5文件中的md5
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\tianlei\\Videos\\out.avi"));
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if(source_md5.equals(local_md5)){
            System.out.println("下载成功");
        }
    }
}
