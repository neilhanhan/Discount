package com.xmu.discount.service;

import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.vo.PresaleRuleVo;

import java.util.List;

/**
 * @author Liuwenhan
 */
public interface PresaleRuleService {

    /**
     * 管理员获取预售规则
     * @param page 页数
     * @param limit 分页大小
     * @return List<PresaleRuleVo>
     */
    List<PresaleRuleVo> adminGetPresaleRule(Integer page, Integer limit);

    /**
     * 用户获取预售规则
     * @param page 页数
     * @param limit 分页大小
     * @return ist<PresaleRuleVo>
     */
    List<PresaleRuleVo> customerGetPresaleRule(Integer page, Integer limit);

    /**
     * 删除预售规则
     * @param id 预售规则ID
     * @return Boolean
     */
    boolean deletePresaleRule(Integer id);

    /**
     * 获取团购规则
     * @param id 预售规则ID
     * @return PresaleRuleVo
     */
    PresaleRuleVo getPresaleRuleById(Integer id);

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

    /**
     * 下架预售规则
     * @param id 预售规则Id
     * @return Boolean
     */
    Boolean downPresaleRuleById(Integer id);
}
