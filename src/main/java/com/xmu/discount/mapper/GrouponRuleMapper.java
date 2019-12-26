package com.xmu.discount.mapper;

import com.xmu.discount.domain.GrouponRulePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Liuwenhan
 */
@Mapper
@Repository
public interface GrouponRuleMapper {

    /**
     * 获取团购规则列表
     * @param id
     * @return List<GrouponRulePo>
     */
    List<GrouponRulePo> getGrouponRuleByGoodsId(Integer id);

    /**
     * 创建一个新的团购规则
     * @param grouponRulePo
     * @return Boolean
     */
    Boolean insertGrouponRule(GrouponRulePo grouponRulePo);

    /**
     * 用户获取团购规则详细信息
     *
     * @param id
     * @return GrouponRulePo
     */
    GrouponRulePo getGrouponRuleById(Integer id);

    /**
     * 管理员获取团购规则详细信息
     *
     * @param id
     * @return GrouponRulePo
     */
    GrouponRulePo adminGetGrouponRuleById(Integer id);

    /**
     * 修改团购规则信息
     * @param grouponRulePo
     * @return Boolean
     */
    Boolean updateGrouponRule(GrouponRulePo grouponRulePo);

    /**
     * 删除团购规则信息
     * @param id 团购规则ID
     * @param time 更改时间
     * @return Boolean
     */
    Boolean deleteGrouponRule(Integer id, LocalDateTime time);

    /**
     * 用户获取团购活动列表
     * @return List<GrouponRulePo>
     */
    List<GrouponRulePo> customerGetGrouponRule();

    /**
     * 管理员获取团购活动列表
     * @return List<GrouponRulePo>
     */
    List<GrouponRulePo> adminGetGrouponRule();

    /**
     * 下架团购规则
     * @param id 团购规则ID
     * @param time 最新修改时间
     * @return Boolean
     */
    Boolean downGrouponRuleById(Integer id,LocalDateTime time);

    /**
     * 检查完成的团购活动并退款
     *
     * @param today
     * @param yesterday
     * @return List<GrouponRulePo>
     */
    List<GrouponRulePo> checkFinishedGrouponRule(LocalDateTime today, LocalDateTime yesterday);

    /**
     * 获取上架中的团购活动
     * @param goodsId 商品ID
     * @return GrouponRulePo
     */
    GrouponRulePo getGrouponRuleOnshelve(Integer goodsId);
}
