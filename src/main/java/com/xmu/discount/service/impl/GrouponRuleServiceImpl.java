package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.Goods;
import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.domain.GrouponRule;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.service.GoodsInfoService;
import com.xmu.discount.service.GrouponRuleService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.vo.GrouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author Liuwenhan
 */

@Service
public class GrouponRuleServiceImpl implements GrouponRuleService {
    @Autowired
    public GrouponRuleDao grouponRuleDao;

//    @Autowired
//    public OrderService orderService;

    @Autowired
    public GoodsInfoService goodsInfoService;

    @Override
    public List<GrouponRuleVo> getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit){
        List<GrouponRulePo> grouponRuleList = grouponRuleDao.getGrouponRuleByGoodsId(id,page,limit);
        Page<GrouponRuleVo> grouponList = new Page<GrouponRuleVo>();
        for (GrouponRulePo grouponRulePo : grouponRuleList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsInfoService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null){
                continue;
              }
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponList.add(grouponRuleVo);
        }
        return grouponList;
    }

    @Override
    public Boolean addGrouponRule(GrouponRulePo grouponRulePo){
        return grouponRuleDao.addGrouponRule(grouponRulePo);
    }

    @Override
    public GrouponRuleVo getGrouponRuleById(Integer id){
        GrouponRulePo grouponRulePo = grouponRuleDao.getGrouponRuleById(id);
        GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
        Integer goodsId = grouponRulePo.getGoodsId();
        String str = JacksonUtil.toJson(goodsInfoService.getGoodsById(goodsId));
        Map map = (Map) JSON.parse(str);
        String data = map.get("data").toString();
        GoodsPo goodsPo=new GoodsPo();
        try{
            goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        return grouponRuleVo;
    }

    @Override
    public GrouponRuleVo adminGetGrouponRuleById(Integer id) {
        GrouponRulePo grouponRulePo = grouponRuleDao.adminGetGrouponRuleById(id);
        GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
        Integer goodsId = grouponRulePo.getGoodsId();
        String str = JacksonUtil.toJson(goodsInfoService.getGoodsById(goodsId));
        Map map = (Map) JSON.parse(str);
        String data = map.get("data").toString();
        GoodsPo goodsPo=new GoodsPo();
        try{
            goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        return grouponRuleVo;
    }

    @Override
    public Boolean updateGrouponRule(GrouponRulePo grouponRulePo){
        return grouponRuleDao.updateGrouponRule(grouponRulePo);
    };

    @Override
    public Boolean deleteGrouponRule(Integer id){
        return grouponRuleDao.deleteGrouponRule(id);
    };

    @Override
    public List<GrouponRuleVo> customerGetGrouponRule(Integer page, Integer limit) {
        List<GrouponRulePo> grouponRuleList = grouponRuleDao.customerGetGrouponRule(page,limit);
        Page<GrouponRuleVo> grouponList = new Page<GrouponRuleVo>();

        for (GrouponRulePo grouponRulePo : grouponRuleList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsInfoService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null)
                continue;
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponList.add(grouponRuleVo);
        }
        return grouponList;
    }

    @Override
    public List<GrouponRuleVo> adminGetGrouponRule(Integer page, Integer limit) {
        List<GrouponRulePo> grouponRuleList = grouponRuleDao.adminGetGrouponRule(page,limit);
        Page<GrouponRuleVo> grouponList = new Page<GrouponRuleVo>();

        for (GrouponRulePo grouponRulePo : grouponRuleList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsInfoService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data,Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null) {
                continue;
            }
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponList.add(grouponRuleVo);
        }
        return grouponList;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean downGrouponRuleById(Integer id) {
        return grouponRuleDao.downGrouponRuleById(id);
    }

    /**
     *
     */
    @Override
    public void checkFinishedGrouponRule() {
        List<GrouponRulePo> grouponRulePoList = grouponRuleDao.checkFinishedGrouponRule();
        List<GrouponRule> grouponRuleList = grouponRuleDao.getGrouponRuleList(grouponRulePoList);
//        for(int i=0;i<grouponRulePoList.size();i++){
//            List<GrouponRule.Strategy> strategyList = grouponRuleList.get(i).getStrategyList();
//            //调用orderService模块获取完成的订单数
//            Integer num = orderService.getGrouponOrders(grouponRulePoList.get(i));
//            for(GrouponRule.Strategy strategy:strategyList){
//                if(num>=strategy.getLowerBound()) {
//                    if (num <= strategy.getUpperBound() || strategy.getUpperBound() == null) {
//                        BigDecimal rate = strategy.getDiscountRate();
//                        //调用orderService模块进行退款
//                        orderService.refundOfGrouponRule(grouponRulePoList.get(i), rate);
//                        break;
//                    }
//                }
//            }
//        }
    }

}
