package com.qingmuy.model.dto.userinterfaceinfo;

import com.qingmuy.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 调用用户id
     */
    private Long userId;

    /**
     * 接口id
     */
    private Long interfaceInfoId;

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

}
