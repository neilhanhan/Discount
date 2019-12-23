package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.service.GrouponRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Liuwenhan
 */

@Service
public class GrouponRuleServiceImpl implements GrouponRuleService {
    @Autowired
    public GrouponRuleDao grouponRuleDao;

    @Override
    public Object getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit){
        return grouponRuleDao.getGrouponRuleByGoodsId(id,page,limit);
    }

    @Override
    public Object addGrouponRule(GrouponRulePo grouponRulePo){
        return grouponRuleDao.addGrouponRule(grouponRulePo);
    }

    @Override
    public Object getGrouponRuleById(Integer id){
        return grouponRuleDao.getGrouponRuleById(id);
    }

    @Override
    public Object adminGetGrouponRuleById(Integer id) {
        return grouponRuleDao.adminGetGrouponRuleById(id);
    }

    @Override
    public Object updateGrouponRule(Integer id,GrouponRulePo grouponRulePo){
        return grouponRuleDao.updateGrouponRule(id,grouponRulePo);
    }

    @Override
    public Object deleteGrouponRule(Integer id){
        return grouponRuleDao.deleteGrouponRule(id);
    }

    @Override
    public Object customerGetGrouponRule(Integer page, Integer limit) {
        return  grouponRuleDao.customerGetGrouponRule(page,limit);
    }

    @Override
    public Object adminGetGrouponRule(Integer page, Integer limit) {
        return grouponRuleDao.adminGetGrouponRule(page, limit);
    }

    @Override
    public Object downGrouponRuleById(Integer id) {
        return grouponRuleDao.downGrouponRuleById(id);
    }

    @Override
    public void checkFinishedGrouponRule() {
        grouponRuleDao.checkFinishedGrouponRule();
    }

    @Override
    public GrouponRulePo getGrouponRuleOnshelve(Integer goodsId) {
        return grouponRuleDao.getGrouponRuleOnshelve(goodsId);
    }

}
