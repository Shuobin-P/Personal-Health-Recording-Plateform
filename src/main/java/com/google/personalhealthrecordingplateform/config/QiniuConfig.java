package com.google.personalhealthrecordingplateform.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/22 15:43
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class QiniuConfig {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public Configuration getConfiguration() {
        return new Configuration(Region.region2());
    }

    @Bean
    @Qualifier("qiniuUploadManager")
    public UploadManager getUploadManager(Configuration cfg) {
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        return new UploadManager(cfg);
    }

    @Bean
    public BucketManager getBucketManager(Auth auth, Configuration cfg) {
        return new BucketManager(auth, cfg);
    }
}
