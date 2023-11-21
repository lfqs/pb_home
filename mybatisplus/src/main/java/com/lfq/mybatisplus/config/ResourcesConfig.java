package com.lfq.mybatisplus.config;

import com.lfq.common.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Configuration
@RequiredArgsConstructor
public class ResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* 本地文件上传路径 */
        registry.addResourceHandler("/" + Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + PbHomeConfig.getFileBaseDir() + "/");
        System.out.println("------------------"+PbHomeConfig.getFileBaseDir());
    }

}
