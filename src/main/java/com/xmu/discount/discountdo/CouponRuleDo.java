package com.xmu.discount.discountdo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 用来与数据库交互中封装CouponRule的id和goodsListn
 * @Author Zhang BingYuan
 * @Date 2019/12/17 19:03
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Alias("couponRuleDo")
public class CouponRuleDo {
    Integer id;
    /**
     * 存放适用于本优惠券规则的所有商品ID
     * JSON格式:{"goodsIds": [xxx,xxx,xxx,xxx,xxx]}
     * eg. {"goodsIds": [1, 2, 3, 4, 5]}
     */
    private String goodsList1;
    /**
     * 存放适用于本优惠券规则的所有商品ID(商品可能很多，可能需要多个list存放)
     * JSON格式:{"goodsIds": [xxx,xxx,xxx,xxx,xxx]}
     * eg. {"goodsIds": [1, 2, 3, 4, 5]}
     */
    private String goodsList2;
}
