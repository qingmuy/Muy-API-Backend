package com.qingmuy.model.vo;

import com.qingmuy.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kbws
 * @date 2024/7/27
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoVO extends InterfaceInfo {
    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;

}