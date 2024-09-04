package com.qingmuy.muyapiclientsdk;

import com.qingmuy.muyapiclientsdk.client.MuyApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties("muy.client")
@Data
@Component
public class MuyApiClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public MuyApiClient muyApiClient() {
        return new MuyApiClient(accessKey, secretKey);
    }
}
