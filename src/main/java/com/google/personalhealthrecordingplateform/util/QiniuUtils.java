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

import java.io.*;

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
    public QiniuUtils(Auth auth, UploadManager uploadManager, BucketManager bucketManager) {
        this.auth = auth;
        this.uploadManager = uploadManager;
        this.bucketManager = bucketManager;
    }

    /**
     * 上传文件，并获得文件的url
     *
     * @param file
     * @param fileName 包括文件类型后缀的完整文件名
     * @return 文件url
     * @throws QiniuException
     */
    public String upload(InputStream file, String fileName) throws QiniuException {
        fileName = StringUtils.getRandomImgName(fileName);
        String token = auth.uploadToken(bucketName);
        Response res = uploadManager.put(file, fileName, token, null, null);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return fileName;
    }

    /**
     * 上传文件，并获得文件的url
     *
     * @param file
     * @param fileName 包括文件类型后缀的完整文件名
     * @return 文件url
     * @throws QiniuException
     */
    public String upload(FileInputStream file, String fileName) throws QiniuException {
        fileName = StringUtils.getRandomImgName(fileName);
        String token = auth.uploadToken(bucketName);
        Response res = uploadManager.put(file, fileName, token, null, null);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return fileName;
    }

    /**
     * @param uploadBytes
     * @param fileName    包括文件类型后缀的完整文件名
     * @return
     * @throws IOException
     */
    public String upload(byte[] uploadBytes, String fileName) throws IOException {
        fileName = StringUtils.getRandomImgName(fileName);
        String token = auth.uploadToken(bucketName);
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Response res = uploadManager.put(byteInputStream, fileName, token, null, null);
        byteInputStream.close();
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return fileName;
    }


    /**
     * 删除七牛云文件
     *
     * @param fileName 头像文件名
     * @throws QiniuException 七牛云异常
     */
    public void delete(String fileName) throws QiniuException {
        bucketManager.delete(bucketName, fileName);
    }

}
