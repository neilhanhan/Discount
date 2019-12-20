package com.xmu.discount.vo;

import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.domain.GrouponRulePo;

/**
 * @author Liuwenhan
 */
public class GrouponRuleVo {
    private GrouponRulePo grouponRulePo;
    private GoodsPo goodsPo;

    public GrouponRulePo getGrouponRulePo() {
        return grouponRulePo;
    }

    public void setGrouponRulePo(GrouponRulePo grouponRulePo) {
        this.grouponRulePo = grouponRulePo;
    }

    public GoodsPo getGoodsPo() {
        return goodsPo;
    }

    public void setGoodsPo(GoodsPo goodsPo) {
        this.goodsPo = goodsPo;
    }

    @Override
    public String toString() {
        return "GrouponRuleVo{" +
                "grouponRulePo=" + grouponRulePo +
                ", goodsPo=" + goodsPo +
                '}';
    }
}