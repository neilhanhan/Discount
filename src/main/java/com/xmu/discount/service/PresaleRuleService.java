package com.xmu.discount.service;

import com.xmu.discount.domain.Order;
import com.xmu.discount.domain.Payment;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.vo.PresaleRuleVo;

import java.util.List;

/**
 * @author Liuwenhan
 */
public interface PresaleRuleService {

    /**
     * 返回上架中的预售规则
     * @param id GoodsId
     * @return
     */
    PresaleRule getPresaleRuleByGoodsId(Integer id);

    /**
     * 为order创建定金和尾款的两个payment
     * @param order
     * @param presaleRule
     * @return
     */
    List<Payment> presaleRulePayment(Order order, PresaleRule presaleRule);

    /**
     * 下架预售规则
     * @param id 预售规则Id
     * @return Object
     */
    Object downPresaleRuleById(Integer id);

    /**
     * 管理员获取预售规则
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    Object adminGetPresaleRule(Integer page, Integer limit);

    /**
     * 用户获取预售规则
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    Object customerGetPresaleRule(Integer page, Integer limit);

    /**
     * 删除预售规则
     * @param id 预售规则ID
     * @return Boolean
     */
    Object deletePresaleRule(Integer id);

    /**
     * 获取团购规则
     * @param id 预售规则ID
     * @return PresaleRuleVo
     */
    Object getPresaleRuleById(Integer id);

    /**
     * 数据库中增加一个PresaleRule
     *
     * @param presaleRule
     * @return
     */
    public PresaleRule addPresaleRule(PresaleRule presaleRule);

    /**
     * 通过goodsId获取presaleRules
     *
     * @param goodsId
     * @param page
     * @param limit
     * @return
     */
    public List<PresaleRuleVo> findPresaleRuleVosByGoodsId(Integer goodsId, Integer page, Integer limit);

    /**
     * 修改预售信息
     *
     * @param id
     * @param presaleRule
     * @return
     */
    public PresaleRule updatePresaleRuleById(Integer id, PresaleRule presaleRule);

    /**
     * 通过id获得PresaleRuleVo
     * @param id
     * @return
     */
    public PresaleRuleVo findPresaleRuleVoById(Integer id);
}
