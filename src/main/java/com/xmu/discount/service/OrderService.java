package com.xmu.discount.service;

import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.domain.PresaleRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Liuwenhan
 */
@Service
public interface OrderService {

    /**
     * @param grouponRulePo
     * @return
     */
    Integer getGrouponOrders(GrouponRulePo grouponRulePo);

    /**
     * @param grouponRulePo
     * @param rate
     * @return
     */
    Boolean refundOfGrouponRule(GrouponRulePo grouponRulePo, BigDecimal rate);

    /**
     * @param presaleRule
     * @return
     */
    Boolean refundOfPresaleRule(PresaleRule presaleRule);
}
