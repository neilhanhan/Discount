
package com.xmu.discount.util;

import com.xmu.discount.domain.OrderItem;
/**
 * @Author Zhang BingYuan
 * @Date 2019/12/22 18:49
 */
public class OrderItemCloneUtil {
    public static OrderItem clone(OrderItem object) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(object.getProduct());
        orderItem.setId(object.getId());
        orderItem.setOrderId(object.getOrderId());
        orderItem.setItemType(object.getItemType());
        orderItem.setStatusCode(object.getStatusCode());
        orderItem.setNumber(object.getNumber());
        orderItem.setPrice(object.getPrice());
        orderItem.setDealPrice(object.getDealPrice());
        orderItem.setProductId(object.getProductId());
        orderItem.setGoodsId(object.getGoodsId());
        orderItem.setNameWithSpecifications(object.getNameWithSpecifications());
        orderItem.setPicUrl(object.getPicUrl());
        orderItem.setGmtCreate(object.getGmtCreate());
        orderItem.setGmtModified(object.getGmtModified());
        orderItem.setBeDeleted(object.getBeDeleted());
        return orderItem;
    }

}