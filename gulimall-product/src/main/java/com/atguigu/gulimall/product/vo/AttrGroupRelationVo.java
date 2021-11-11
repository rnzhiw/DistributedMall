package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * 修改属性组与属性的关联关系
 */
@Data
public class AttrGroupRelationVo {

    // "attrId":1,"attrGroupId":2
    private Integer attrId;
    private Integer attrGroupId;
}
