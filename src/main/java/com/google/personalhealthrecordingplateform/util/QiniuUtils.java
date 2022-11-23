package com.google.personalhealthrecordingplateform.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
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

    /**
     * 参考文献：https://blog.csdn.net/qq_25046827/article/details/123640737
     */
    private Auth auth;

    private UploadManager uploadManager;

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.path}")
    private String url;

    @Autowired
    public QiniuUtils(UploadManager uploadManager, Auth auth) {
        this.uploadManager = uploadManager;
        this.auth = auth;
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
//        return url + "/" + fileName;
        return fileName;

    }

}
