package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.*;
import com.xmu.discount.service.GoodsService;
import com.xmu.discount.service.PresaleRuleService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuwenhan
 */
@Service
public class PresaleRuleServiceImpl implements PresaleRuleService {
    @Autowired
    PresaleRuleDao presaleRuleDao;

    @Autowired
    GoodsService goodsService;

    /**
     * 为order创建定金和尾款的两个payment
     *
     * @param order
     * @param presaleRule
     * @return
     */
    @Override
    public List<Payment> presaleRulePayment(Order order, PresaleRule presaleRule) {
        return presaleRuleDao.presaleRulePayment(order,presaleRule);
    }

    @Override
    public Object adminGetPresaleRule(Integer page, Integer limit) {
        return presaleRuleDao.adminGetPresaleRule(page,limit);
    }

    @Override
    public Object customerGetPresaleRule(Integer page, Integer limit) {
        return presaleRuleDao.customerGetPresaleRule(page,limit);
    }

    @Override
    public Object deletePresaleRule(Integer id) {
        return presaleRuleDao.deletePresaleRule(id);
    }

    @Override
    public Object getPresaleRuleById(Integer id) {
        return presaleRuleDao.getPresaleRuleById(id);
//        PresaleRule presaleRule = presaleRuleDao.getPresaleRuleById(id);
//        PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
//        Integer goodsId = presaleRule.getGoodsId();
//        String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
//        Map map = (Map) JSON.parse(str);
//        String data = map.get("data").toString();
//        GoodsPo goodsPo=new GoodsPo();
//        try{
//            goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        presaleRuleVo.setGoodsPo(goodsPo);
//        presaleRuleVo.setPresaleRule(presaleRule);
//        return presaleRuleVo;
    }


    /**
     * 数据库中增加一个PresaleRule
     *
     * @param presaleRule 预售规则
     * @return PresaleRule 预售规则
     */
    @Override
    public PresaleRule addPresaleRule(PresaleRule presaleRule) {
        PresaleRule presaleRule1 = presaleRuleDao.addPresaleRule(presaleRule);
        return presaleRule1;
    }

    /**
     * 通过goodsId获取presaleRules
     *
     * @param goodsId 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return List<PresaleRuleVo>
     */
    @Override
    public List<PresaleRuleVo> findPresaleRuleVosByGoodsId(Integer goodsId, Integer page, Integer limit) {
        /**
         * 获得List<PresaleRule>和GoodsPo对象
         */
        List<PresaleRule> presaleRules = presaleRuleDao.findPresaleRulesByGoodsId(goodsId, page, limit);
        if (presaleRules.size()==0) {
            return new ArrayList<PresaleRuleVo>();
        }
        String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
        Map map = (Map) JSON.parse(str);
        String data = map.get("data").toString();
        GoodsPo goodsPo=new GoodsPo();
        try{
            goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        List<PresaleRuleVo> presaleRuleVos = new ArrayList<>(presaleRules.size());

        for (PresaleRule presaleRule : presaleRules) {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVos.add(presaleRuleVo);
        }
        return presaleRuleVos;
    }

    /**
     * 修改预售信息
     *
     * @param id 预售规则ID
     * @param presaleRule
     * @return
     */
    @Override
    public PresaleRule updatePresaleRuleById(Integer id, PresaleRule presaleRule) {
        PresaleRule presaleRule1 = presaleRuleDao.updatePresaleRuleById(id, presaleRule);
        return presaleRule1;
    }

    /**
     * 通过id获得PresaleRuleVo
     * @param id 预售规则ID
     * @return PresaleVo
     */
    @Override
    public PresaleRuleVo findPresaleRuleVoById(Integer id) {
        PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
        PresaleRule presaleRuleById = presaleRuleDao.findPresaleRuleById(id);
        Integer goodsId = presaleRuleById.getGoodsId();
        String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
        Map map = (Map) JSON.parse(str);
        String data = map.get("data").toString();
        GoodsPo goodsPo=new GoodsPo();
        try{
            goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (presaleRuleById.equals(null)||goodsPo.equals(null)) {
            return null;
        }
        presaleRuleVo.setGoodsPo(goodsPo);
        presaleRuleVo.setPresaleRule(presaleRuleById);
        return presaleRuleVo;
    }

    @Override
    public PresaleRule getPresaleRuleByGoodsId(Integer id) {
        return presaleRuleDao.getPresaleRuleByGoodsId(id);
    }

    /**
     * 下架预售规则
     * @param id 预售规则ID
     * @return Boolean
     */
    @Override
    public Object downPresaleRuleById(Integer id) {
        return presaleRuleDao.downPresaleRuleById(id);
    }

}
