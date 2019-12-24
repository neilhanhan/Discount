package com.xmu.discount.dao;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmu.discount.domain.*;
import com.xmu.discount.mapper.GrouponRuleMapper;
import com.xmu.discount.service.GoodsService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.GrouponRuleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuwenhan
 */
@Repository
public class GrouponRuleDao{

    @Autowired
    GrouponRuleMapper grouponRuleMapper;

    @Autowired
    GoodsService goodsService;

//    @Autowired
//    OrderService orderService;

    public Object getGrouponRuleByGoodsId(Integer id, Integer page, Integer limit){
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.getGrouponRuleByGoodsId(id);
        List<GrouponRuleVo> grouponRuleVoList = new ArrayList<>();
        for (GrouponRulePo grouponRulePo : grouponRulePoList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            System.out.println(goodsId);
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null){
                continue;
            }
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            System.out.println(grouponRuleVo);
            grouponRuleVoList.add(grouponRuleVo);
        }
        int pagecount=grouponRuleVoList.size()/limit;
        int remain=grouponRuleVoList.size()%limit;
        if(remain>0){
            pagecount++;
        }
        if(page>pagecount) {
            return ResponseUtil.invaildParameter();
        }
        List<GrouponRuleVo> subList;
        if(remain==0&&page==0){
            return ResponseUtil.grouponRuleUnknown();
        }
        if(remain==0) {
            subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
        }
        else{
            if (page==pagecount){
                subList=grouponRuleVoList.subList((page-1)*limit,grouponRuleVoList.size());
            }else{
                subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
            }
        }
        return ResponseUtil.ok(subList);
    }

    public Object addGrouponRule(GrouponRulePo grouponRulePo){
        grouponRulePo.setGmtCreate(LocalDateTime.now());
        grouponRulePo.setGmtModified(LocalDateTime.now());
        grouponRulePo.setBeDeleted(false);
        grouponRulePo.setStatusCode(true);
        if(grouponRuleMapper.insertGrouponRule(grouponRulePo)){
            return ResponseUtil.ok(grouponRulePo);
        }else {
            return ResponseUtil.grouponInsertFail();
        }
    }

    public Object getGrouponRuleById(Integer id){
        GrouponRulePo grouponRulePo = grouponRuleMapper.getGrouponRuleById(id);
        if(grouponRulePo==null){
            return ResponseUtil.grouponRuleUnknown();
        }else {
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo= new ObjectMapper().readValue(data, GoodsPo.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            return ResponseUtil.ok(grouponRuleVo);
        }
    }

    public Object adminGetGrouponRuleById(Integer id) {
        GrouponRulePo grouponRulePo = grouponRuleMapper.adminGetGrouponRuleById(id);
        if(grouponRulePo == null){
            return ResponseUtil.grouponRuleUnknown();
        }else {
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo = new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            return ResponseUtil.ok(grouponRuleVo);
        }
    }

    public Object updateGrouponRule(Integer id, GrouponRulePo grouponRulePo){
        if(grouponRuleMapper.adminGetGrouponRuleById(id)==null){
            return ResponseUtil.grouponRuleUnknown();
        }
        LocalDateTime modifiedTime= LocalDateTime.now();
        grouponRulePo.setGmtModified(modifiedTime);
        grouponRulePo.setId(id);
        if(grouponRuleMapper.updateGrouponRule(grouponRulePo)){
            return ResponseUtil.ok(grouponRulePo);
        }else {
            return ResponseUtil.grouponUpdateFail();
        }
    }

    public Object deleteGrouponRule(Integer id){
        LocalDateTime modifiedTime= LocalDateTime.now();
        if(grouponRuleMapper.adminGetGrouponRuleById(id) == null){
            return ResponseUtil.grouponRuleUnknown();
        }else if(grouponRuleMapper.deleteGrouponRule(id,modifiedTime)){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.grouponDeleteFail();
        }
    }

    public Object customerGetGrouponRule(Integer page, Integer limit) {
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.customerGetGrouponRule();
        List<GrouponRuleVo> grouponRuleVoList = new ArrayList<>();
        for (GrouponRulePo grouponRulePo : grouponRulePoList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            System.out.println(goodsService.getGoodsPoById(goodsId));
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null){
                continue;
            }
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponRuleVoList.add(grouponRuleVo);
        }
        int pagecount=grouponRuleVoList.size()/limit;
        int remain=grouponRuleVoList.size()%limit;
        if(remain>0){
            pagecount++;
        }
        if(page>pagecount) {
            return ResponseUtil.invaildParameter();
        }
        List<GrouponRuleVo> subList;
        if(remain==0&&page==0){
            return ResponseUtil.grouponRuleUnknown();
        }
        if(remain==0) {
            subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
        }
        else{
            if (page==pagecount){
                subList=grouponRuleVoList.subList((page-1)*limit,grouponRuleVoList.size());
            }else{
                subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
            }
        }
        return ResponseUtil.ok(subList);
    }

    public Object adminGetGrouponRule(Integer page, Integer limit) {
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.adminGetGrouponRule();
        List<GrouponRuleVo> grouponRuleVoList = new ArrayList<>();
        for (GrouponRulePo grouponRulePo : grouponRulePoList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            System.out.println(goodsService.getGoodsPoById(goodsId));
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null){
                continue;
            }
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(grouponRulePo);
            grouponRuleVo.setGoodsPo(goodsPo);
            grouponRuleVoList.add(grouponRuleVo);
        }
        int pagecount=grouponRuleVoList.size()/limit;
        int remain=grouponRuleVoList.size()%limit;
        if(remain>0){
            pagecount++;
        }
        if(page>pagecount) {
            return ResponseUtil.invaildParameter();
        }
        List<GrouponRuleVo> subList;
        if(remain==0&&page==0){
            return ResponseUtil.grouponRuleUnknown();
        }
        if(remain==0) {
            subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
        }
        else{
            if (page==pagecount){
                subList=grouponRuleVoList.subList((page-1)*limit,grouponRuleVoList.size());
            }else{
                subList=grouponRuleVoList.subList((page-1)*limit,page*limit);
            }
        }
        return ResponseUtil.ok(subList);
    }

    public Object downGrouponRuleById(Integer id) {
        LocalDateTime modifiedTime = LocalDateTime.now();
        if(grouponRuleMapper.adminGetGrouponRuleById(id) == null){
            return ResponseUtil.grouponRuleUnknown();
        }else if(grouponRuleMapper.downGrouponRuleById(id,modifiedTime)){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail(725,"下架操作失败");
        }
    }

    public void checkFinishedGrouponRule() {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime yesterday = today.minusHours(24);
        List<GrouponRulePo> grouponRulePoList = grouponRuleMapper.checkFinishedGrouponRule(today,yesterday);
        List<GrouponRule> grouponRuleList = getGrouponRuleList(grouponRulePoList);
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

    public List<GrouponRule> getGrouponRuleList(List<GrouponRulePo> grouponRulePoList) {
        List<GrouponRule> grouponRuleList = new ArrayList<>();
        for(GrouponRulePo grouponRulePo:grouponRulePoList){
            GrouponRule grouponRule = new GrouponRule();
            List<GrouponRule.Strategy> strategy = JacksonUtil.getGrouponRuleStrategy(grouponRulePo.getGrouponLevelStrategy());
            BeanUtils.copyProperties(grouponRulePo,grouponRule);
            grouponRule.setStrategyList(strategy);
            grouponRuleList.add(grouponRule);
        }
        return grouponRuleList;
    }

    public GrouponRulePo getGrouponRuleOnshelve(Integer goodsId) {
        GrouponRulePo grouponRulePo = grouponRuleMapper.getGrouponRuleOnshelve(goodsId);
        GrouponRule grouponRule = new GrouponRule();
        List<GrouponRule.Strategy> strategy = JacksonUtil.getGrouponRuleStrategy(grouponRulePo.getGrouponLevelStrategy());
        BeanUtils.copyProperties(grouponRulePo,grouponRule);
        grouponRule.setStrategyList(strategy);
        return grouponRulePo;
    }
}
