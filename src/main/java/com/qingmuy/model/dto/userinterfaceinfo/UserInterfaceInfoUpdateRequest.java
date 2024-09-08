package com.qingmuy.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-关闭，1-禁用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
