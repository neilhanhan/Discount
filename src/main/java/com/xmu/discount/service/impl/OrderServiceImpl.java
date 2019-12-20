package com.xmu.discount.service.impl;

import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Liuwenhan
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Integer getGrouponOrders(GrouponRulePo grouponRulePo) {
        return null;
    }

    @Override
    public Boolean refundOfGrouponRule(GrouponRulePo grouponRulePo, BigDecimal rate) {
        return null;
    }

    @Override
    public Boolean refundOfPresaleRule(PresaleRule presaleRule) {
        return null;
    }
}
