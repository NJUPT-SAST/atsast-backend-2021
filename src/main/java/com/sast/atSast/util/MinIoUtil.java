package com.sast.atSast.util;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: 風楪fy
 * @create: 2021-09-12 18:01
 **/
@Component
public class MinIoUtil {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Autowired
    private MinioClient minioClient;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }


    /**
     * 查看存储bucket是否存在
     *
     * @param bucketName 存储bucket
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public void makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }


    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }

    /**
     * 获取文件下载外链
     */
    public String getDownloadURL(String bucketName, String fileName) {
        try {
            GetPresignedObjectUrlArgs objectArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(2, TimeUnit.HOURS)
                    .build();
            return minioClient.getPresignedObjectUrl(objectArgs);
        } catch (Exception e){
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }

    /**
     * 文件上传
     *
     * @param file       文件
     * @param bucketName 存储bucket
     * @return Boolean
     */
    public void upload(MultipartFile file, String bucketName) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName 存储bucket名称
     * @param fileName   文件名称
     * @param res        response
     * @return Boolean
     */
    public void download(String bucketName, String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("UTF-8");
                //设置强制下载不打开
                res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }


    /**
     * 批量删除文件对象
     *
     * @param bucketName 存储bucket名称
     * @param objects    对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
        try {
            List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
            return results;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }
    }

}
