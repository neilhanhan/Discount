package com.xmu.discount.dao;

import com.github.pagehelper.PageHelper;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.mapper.PresaleRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Liuwenhan
 */
@Repository
public class PresaleRuleDao {

    @Autowired
    PresaleRuleMapper presaleRuleMapper;

    public boolean deletePresaleRule(Integer id) {
        return presaleRuleMapper.deletePresaleRule(id);
    }

    public List<PresaleRule> adminGetPresaleRule(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<PresaleRule> presaleRuleList = presaleRuleMapper.adminGetPresaleRule();
        return presaleRuleList;
    }

    public List<PresaleRule> customerGetPresaleRule(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<PresaleRule> presaleRuleList = presaleRuleMapper.customerGetPresaleRule();
        return presaleRuleList;
    }

    public PresaleRule getPresaleRuleById(Integer id) {
        return presaleRuleMapper.getPresaleRuleById(id);
    }

    /**
     * 数据库中增加一个PresaleRule
     *
     * @param presaleRule
     * @return
     */
    public PresaleRule addPresaleRule(PresaleRule presaleRule) {
        LocalDateTime localDateTime = LocalDateTime.now();
        presaleRule.setGmtCreate(localDateTime);
        presaleRule.setGmtModified(localDateTime);
        Integer integer = presaleRuleMapper.addPresaleRule(presaleRule);
        if (integer==1) {
            presaleRule.setBeDeleted(false);
            return presaleRule;
        }
        return null;
    }

    public List<PresaleRule> findPresaleRulesByGoodsId(Integer goodsId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<PresaleRule> presaleRulesByGoodsId = presaleRuleMapper.findPresaleRulesByGoodsId(goodsId);
        return presaleRulesByGoodsId;
    }

    /**
     * 修改预售信息
     *
     * @param id
     * @param presaleRule
     * @return
     */
    public PresaleRule updatePresaleRuleById(Integer id, PresaleRule presaleRule) {
        presaleRule.setId(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        presaleRule.setGmtModified(localDateTime);
        Integer integer = presaleRuleMapper.updatePresaleRuleById(presaleRule);
        if (integer==1) {
            return presaleRule;
        }
        return null;
    }

    /**
     * 通过id查找PresaleRule
     * @param id
     * @return
     */
    public PresaleRule findPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleMapper.findPresaleRuleById(id);
        return  presaleRule;
    }

    public Boolean downPresaleRuleById(Integer id) {
        LocalDateTime modifiedTime= LocalDateTime.now();
        return presaleRuleMapper.downPresaleRuleById(id,modifiedTime);
    }
}
