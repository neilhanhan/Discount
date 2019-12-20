package com.xmu.discount.service;

import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.vo.GrouponRuleVo;
import java.util.List;


/**
 * @author Liuwenhan
 */
public interface GrouponRuleService {

    /**
     * 获取GrouponRule
     * @param id 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return
     */
    List<GrouponRuleVo> getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit);

    /**
     * 添加团购规则
     * @param grouponRulePo 团购规则Po
     * @return Boolean
     */
    Boolean addGrouponRule(GrouponRulePo grouponRulePo);

    /**
     * 获取团购规则
     * @param id 团购规则ID
     * @return GrouponRuleVo
     */
    GrouponRuleVo getGrouponRuleById(Integer id);

    /**
     * 管理员获取团购规则
     * @param id 团购规则Id
     * @return GrouponRuleVo
     */
    GrouponRuleVo adminGetGrouponRuleById(Integer id);

    /**
     * 更新团购规则
     * @param grouponRulePo 团购规则Po
     * @return Boolean
     */
    Boolean updateGrouponRule(GrouponRulePo grouponRulePo);

    /**
     * 删除团购规则
     * @param id 团购规则Id
     * @return Boolean
     */
     Boolean deleteGrouponRule(Integer id);

    /**
     * 用户获取团购规则
     * @param page 页数
     * @param limit 分页大小
     * @return List<GrouponRuleVo>
     */
    List<GrouponRuleVo> customerGetGrouponRule(Integer page, Integer limit);

    /**
     * 管理员获取团购规则
     * @param page 页数
     * @param limit 分页大小
     * @return List<GrouponRuleVo>
     */
    List<GrouponRuleVo> adminGetGrouponRule(Integer page, Integer limit);


    /**
     * 下架团购规则
     * @param id 团购规则Id
     * @return Boolean
     */
    Boolean downGrouponRuleById(Integer id);

    /**
     * 检查完成的团购活动
     */
    void checkFinishedGrouponRule();
}
