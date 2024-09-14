package com.qingmuy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户新AccessKey和SecretKey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAKSKVO implements Serializable {
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;
}