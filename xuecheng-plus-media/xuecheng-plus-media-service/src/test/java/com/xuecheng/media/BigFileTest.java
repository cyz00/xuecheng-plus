package com.xuecheng.media;

import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @description 大文件处理测试
 * @author zcy
 * @date 2023/7/6
 */

public class BigFileTest {
    //分块测试
    @Test
    public void testChunk() throws IOException {
        //源文件
        File sourceFile = new File("C:\\Users\\tianlei\\Desktop\\zcy\\我的资源\\恐怖\\撒D的恶魔\\撒旦的奴隶2 2022 1080P（中字）.mp4");
        //分块文件存储路径
        String chunkFilePath = "F:\\zcy\\mdia\\chunk\\";
        //分块文件大小
        int chunkSize = 1024 * 1024 * 100;
        //分块文件个数
        int chunkNum = (int) Math.ceil(sourceFile.length()*1.0/chunkSize);
        //使用流从原文件读数据，向分块文件中写数据
        RandomAccessFile raf_r = new RandomAccessFile(sourceFile,"r");
        //缓存区
        byte[] bytes = new byte[1024];
        for (int i = 0; i < chunkNum; i++) {
            File chunkFile = new File(chunkFilePath+i);
            //分块写入流
            RandomAccessFile raf_rw = new RandomAccessFile(chunkFile,"rw");
            int len = -1;
            while((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
                if(chunkFile.length()>=chunkSize){
                    break;
                }
            }
            raf_rw.close();
        }
        raf_r.close();
    }
    //合并
    @Test
    public void testMerge() throws IOException {
        //块文件目录
        File chunkFolder = new File("F:\\zcy\\mdia\\chunk\\");
        //源文件
        File sourceFile = new File("C:\\Users\\tianlei\\Desktop\\zcy\\我的资源\\恐怖\\撒D的恶魔\\撒旦的奴隶2 2022 1080P（中字）.mp4");
        //合并后的文件
        File mergeFile = new File("F:\\zcy\\mdia\\video\\撒旦的奴隶_1.mp4");
        //取出所有的分块文件
        File[] files = chunkFolder.listFiles();
        //将数组转成list
        List<File> fileList = Arrays.asList(files);
        //对分块文件排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
            }
        });
//        Collections.sort(fileList,(o1, o2) ->{
//            return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
//        });
        //向合并文件写的流
        RandomAccessFile raf_rw = new RandomAccessFile(mergeFile,"rw");
        //缓存区
        byte[] bytes = new byte[1024];
        //遍历文件块
        for (File file:fileList) {
            //分块读流
            RandomAccessFile raf_r = new RandomAccessFile(file,"r");
            int len=-1;
            while ((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
            }
            raf_r.close();
        }
        raf_rw.close();
        //校验
        FileInputStream fileInputStream_merge = new FileInputStream(mergeFile);
        FileInputStream fileInputStream_source = new FileInputStream(sourceFile);
        String md5_merge = DigestUtils.md5Hex(fileInputStream_merge);
        String md5_source = DigestUtils.md5Hex(fileInputStream_source);
        if(md5_merge.equals(md5_source)){
            System.out.println("合并成果");
        }else{
            System.out.println("丢失文件");
        }
    }
    //将分块文件上传到minio
    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
    @Test
    public void uploadChunk(){
        String chunkFolderPath = "F:\\zcy\\mdia\\chunk\\";
        File chunkFolder = new File(chunkFolderPath);
        //分块文件
        File[] files = chunkFolder.listFiles();
        //将分块文件上传至minio
        for (int i = 0; i < files.length; i++) {
            try {
                UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket("testbucket").object("chunk/" + i).filename(files[i].getAbsolutePath()).build();
                minioClient.uploadObject(uploadObjectArgs);
                System.out.println("上传分块成功"+i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //调用minio接口合并，minio默认的合并人文件最小为5M
    @Test
    public void test_merge() throws Exception {
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i)
                .limit(19)
                .map(i -> ComposeSource.builder()
                        .bucket("testbucket")
                        .object("chunk/".concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());

        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket("testbucket")
                .object("merge撒旦的奴隶.mp4")
                .sources(sources).build();
        minioClient.composeObject(composeObjectArgs);
    }


    //清理分块
}
