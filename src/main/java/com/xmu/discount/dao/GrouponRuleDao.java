package com.xmu.discount.dao;

import com.github.pagehelper.PageHelper;
import com.xmu.discount.domain.GrouponRule;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.mapper.GrouponRuleMapper;
import com.xmu.discount.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuwenhan
 */
@Repository
public class GrouponRuleDao {

    @Autowired
    private GrouponRuleMapper grouponRuleMapper;

    public List<GrouponRulePo> getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.getGrouponRuleByGoodsId(id);
        return grouponRulePoList;
    }

    public Boolean addGrouponRule(GrouponRulePo grouponRulePo){
        return grouponRuleMapper.insertGrouponRule(grouponRulePo);
    }

    public GrouponRulePo getGrouponRuleById(Integer id){
        return grouponRuleMapper.getGrouponRuleById(id);
    }

    public GrouponRulePo adminGetGrouponRuleById(Integer id) {
        return grouponRuleMapper.adminGetGrouponRuleById(id);
    }

    public Boolean updateGrouponRule(GrouponRulePo grouponRulePo){
        LocalDateTime modifiedTime= LocalDateTime.now();
        grouponRulePo.setGmtModified(modifiedTime);
        return grouponRuleMapper.updateGrouponRule(grouponRulePo);
    }

    public Boolean deleteGrouponRule(Integer id){
        LocalDateTime modifiedTime= LocalDateTime.now();
        return grouponRuleMapper.deleteGrouponRule(id,modifiedTime);
    }

    public List<GrouponRulePo> customerGetGrouponRule(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.customerGetGrouponRule();
        return grouponRulePoList;
    }

    public List<GrouponRulePo> adminGetGrouponRule(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.adminGetGrouponRule();
        return  grouponRulePoList;
    }

    public Boolean downGrouponRuleById(Integer id) {
        LocalDateTime modifiedTime= LocalDateTime.now();
        return grouponRuleMapper.downGrouponRuleById(id,modifiedTime);
    }

    public List<GrouponRulePo> checkFinishedGrouponRule() {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime yesterday = today.minusHours(24);
        return grouponRuleMapper.checkFinishedGrouponRule(today,yesterday);
    }

    public List<GrouponRule> getGrouponRuleList(List<GrouponRulePo> grouponRulePoList) {
        List<GrouponRule> grouponRuleList;
        grouponRuleList = new ArrayList<>();
        for(GrouponRulePo grouponRulePo:grouponRulePoList){
            GrouponRule grouponRule = new GrouponRule();
            List<GrouponRule.Strategy> strategy = JacksonUtil.getGrouponRuleStrategy(grouponRulePo.getGrouponLevelStrategy());
            grouponRule.setId(grouponRulePo.getId());
            grouponRule.setGoodsId(grouponRulePo.getGoodsId());
            grouponRule.setStartTime(grouponRulePo.getStartTime());
            grouponRule.setEndTime(grouponRulePo.getEndTime());
            grouponRule.setStrategyList(strategy);
            grouponRule.setGmtModified(grouponRulePo.getGmtModified());
            grouponRule.setGmtCreate(grouponRulePo.getGmtCreate());
            grouponRule.setBeDeleted(grouponRulePo.getBeDeleted());
            grouponRule.setStatusCode(grouponRulePo.getStatusCode());
            grouponRuleList.add(grouponRule);
        }
        return grouponRuleList;
    }
}
