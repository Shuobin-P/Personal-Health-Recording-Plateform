package com.google.personalhealthrecordingplateform.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/21 21:45
 */
@Slf4j
@Component
public class QiniuUtils {

    private Auth auth;
    private UploadManager uploadManager;
    private BucketManager bucketManager;

    @Value("${qiniu.bucketName}")
    private String bucketName;


    @Autowired
    public QiniuUtils(UploadManager uploadManager, Auth auth, BucketManager bucketManager) {
        this.uploadManager = uploadManager;
        this.auth = auth;
        this.bucketManager = bucketManager;
    }

    /**
     * 上传文件，并获得文件的url
     *
     * @return 文件url
     */
    public String upload(FileInputStream file, String fileName) throws QiniuException {
        String token = auth.uploadToken(bucketName);
        Response res = uploadManager.put(file, fileName, token, null, null);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return fileName;

    }

    /**
     * 删除七牛云头像文件
     *
     * @param fileName 头像文件名
     * @throws QiniuException 七牛云异常
     */
    public void delete(String fileName) throws QiniuException {
        bucketManager.delete(bucketName, fileName);
    }

}
