package com.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.community.exception.CustomizeErrorCode;
import com.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class OssProvider {

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;


    public String upload(InputStream inputStream, String uploadObjectName) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 设置文件可公共读
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);

        // 创建PutObjectRequest对象。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。

        // 上传文件重名判断
        String uploadFileName= UUID.randomUUID().toString() +uploadObjectName;

        // 上传文件流。
        PutObjectResult result = ossClient.putObject(bucketName, uploadFileName, inputStream,metadata);
        if (result != null && result.getETag() != null) {
            Date expiration = new Date(new Date().getTime() + 3600 * 24 * 1000);
            String url = ossClient.generatePresignedUrl(bucketName, uploadFileName, expiration).toString();
            // 关闭OSSClient。
            ossClient.shutdown();
            return url;
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
    }
}
