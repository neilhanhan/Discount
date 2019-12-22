package com.xmu.discount.service;

import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.domain.PresaleRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Liuwenhan
 */
@Component
@FeignClient(name = "orderService")
public interface OrderService {

    /**
     * 获取团购订单数量
     * @param grouponRulePo
     * @return
     */

    Integer getGrouponOrders(GrouponRulePo grouponRulePo);

    /**
     * 通过订单进行团购退款
     * @param grouponRulePo
     * @param rate
     * @return
     */

    Boolean refundOfGrouponRule(GrouponRulePo grouponRulePo, BigDecimal rate);

    /**
     * 通过订单进行预售退款
     * @param presaleRule
     * @return
     */

    Boolean refundOfPresaleRule(PresaleRule presaleRule);
}
