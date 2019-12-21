package com.xmu.discount.dao;

import com.xmu.discount.domain.GrouponRule;
import com.xmu.discount.domain.GrouponRulePo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liuwenhan
 */
@Repository
public interface GrouponRuleDao {


    /**
     * 通过商品ID获取团购规则列表
     * @param id 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    Object getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit);

    /**
     * 添加团购规则
     * @param grouponRulePo 团购规则ID
     * @return Object
     */
    Object addGrouponRule(GrouponRulePo grouponRulePo);

    /**
     * 用户通过团购规则ID获取团购活动
     * @param id 团购规则ID
     * @return Object
     */
    Object getGrouponRuleById(Integer id);

    /**
     * 管理员通过团购规则ID获取团购规则详情
     * @param id 团购规则ID
     * @return Object
     */
    Object adminGetGrouponRuleById(Integer id);

    /**
     * 更新团购规则
     * @param id 团购规则ID
     * @param grouponRulePo GrouponRulePo
     * @return Object
     */
    Object updateGrouponRule(Integer id,GrouponRulePo grouponRulePo);

    /**
     * 删除团购规则
     * @param id 团购规则ID
     * @return Object
     */
    Object deleteGrouponRule(Integer id);

    /**
     * 普通用户获取团购规则列表（未删除且上架中）
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    Object customerGetGrouponRule(Integer page,Integer limit);

    /**
     * 管理员获取团购规则列表（全部）
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    Object adminGetGrouponRule(Integer page,Integer limit);

    /**
     * 下架团购活动
     * @param id 团购规则ID
     * @return Object
     */
    Object downGrouponRuleById(Integer id);

    /**
     * 检查已完成的团购活动并退款
     */
    void checkFinishedGrouponRule();

    /**
     * 解析List<GrouponRulePo>并返回List<GrouponRule>
     * @param grouponRulePoList List<GrouponRulePo>
     * @return Object
     */
    List<GrouponRule> getGrouponRuleList(List<GrouponRulePo> grouponRulePoList);

    /**
     * 给Goods模块调用的接口，返回GrouponRule
     * @param goodsId 商品ID
     * @return Object
     */
    GrouponRule getGrouponRuleOnshelve(Integer goodsId);
}
