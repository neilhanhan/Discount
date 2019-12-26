package com.xmu.discount.service;

import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.domain.PresaleRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

/**
 * @author Liuwenhan
 */
@Component
@FeignClient("orderService")
public interface OrdersService {

    /**
     * 获取团购订单数量
     * @param grouponRulePo
     * @return
     */
    @GetMapping("")
    Integer getGrouponOrders(GrouponRulePo grouponRulePo);

    /**
     * 通过订单进行团购退款
     * @param grouponRulePo
     * @param rate
     * @return
     */
    @GetMapping("")
    Boolean refundOfGrouponRule(GrouponRulePo grouponRulePo, BigDecimal rate);

    /**
     * 通过订单进行预售退款
     * @param presaleRule
     * @return
     */
    @GetMapping("")
    Boolean refundOfPresaleRule(PresaleRule presaleRule);
}
