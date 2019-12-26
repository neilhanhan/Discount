package com.xmu.discount.dao;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmu.discount.domain.*;
import com.xmu.discount.mapper.PresaleRuleMapper;
import com.xmu.discount.service.GoodsService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.PageUtil;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuwenhan
 */
@Repository
public class PresaleRuleDao {

    @Autowired
    PresaleRuleMapper presaleRuleMapper;

    @Autowired
    GoodsService goodsService;

//    @Autowired
//    OrdersService ordersService;

    public Object downPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleMapper.getPresaleRuleById(id);
        LocalDateTime modifiedTime= LocalDateTime.now();
        if(presaleRuleMapper.findPresaleRuleById(id) == null){
            return ResponseUtil.presaleRuleUnknown();
        }else if(presaleRule==null){
            return ResponseUtil.fail(736,"预售活动下架失败");
        }else if(presaleRuleMapper.downPresaleRuleById(id,modifiedTime)){
//            ordersService.refundOfPresaleRule(presaleRule);
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail(736,"预售活动下架失败");
        }
    }

    public Object deletePresaleRule(Integer id) {
        LocalDateTime time = LocalDateTime.now();
        if(presaleRuleMapper.deletePresaleRule(id,time)){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.grouponDeleteFail();
        }
    }

    public Object adminGetPresaleRule(Integer page, Integer limit) {
        List<PresaleRule> presaleRuleList = presaleRuleMapper.adminGetPresaleRule();
        if (presaleRuleList.size()==0) {
            return ResponseUtil.presaleRuleUnknown();
        }
        List<PresaleRuleVo> presaleRuleVoList = new ArrayList<>();
        for (PresaleRule presaleRule : presaleRuleList) {
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null) {
                continue;
            }
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVoList.add(presaleRuleVo);
        }
        int pagecount=presaleRuleVoList.size()/limit;
        int remain=presaleRuleVoList.size()%limit;
        if(remain>0){
            pagecount++;
        }
        if(page>pagecount) {
            return ResponseUtil.invaildParameter();
        }
        List<PresaleRuleVo> subList;
        if(remain==0) {
            subList=presaleRuleVoList.subList((page-1)*limit,page*limit);
        }
        else{
            if (page==pagecount){
                subList=presaleRuleVoList.subList((page-1)*limit,presaleRuleVoList.size());
            }else{
                subList=presaleRuleVoList.subList((page-1)*limit,page*limit);
            }
        }
        return ResponseUtil.okList(subList);
    }

    public Object customerGetPresaleRule(Integer page, Integer limit) {
        List<PresaleRule> presaleRuleList = presaleRuleMapper.customerGetPresaleRule();
        if (presaleRuleList.size()==0) {
            return ResponseUtil.presaleRuleUnknown();
        }
        List<PresaleRuleVo> presaleRuleVoList = new ArrayList<>();
        for (PresaleRule presaleRule : presaleRuleList) {
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null) {
                continue;
            }
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVoList.add(presaleRuleVo);
        }
        int pagecount=presaleRuleVoList.size()/limit;
        int remain=presaleRuleVoList.size()%limit;
        if(remain>0){
            pagecount++;
        }
        if(page>pagecount) {
            return ResponseUtil.invaildParameter();
        }
        List<PresaleRuleVo> subList;
        if(remain==0) {
            subList=presaleRuleVoList.subList((page-1)*limit,page*limit);
        }
        else{
            if (page==pagecount){
                subList=presaleRuleVoList.subList((page-1)*limit,presaleRuleVoList.size());
            }else{
                subList=presaleRuleVoList.subList((page-1)*limit,page*limit);
            }
        }
        return ResponseUtil.okList(subList);
    }

    public Object getPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleMapper.getPresaleRuleById(id);
        if(presaleRule == null){
            return ResponseUtil.presaleRuleUnknown();
        }else {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo = new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVo.setPresaleRule(presaleRule);
            return ResponseUtil.ok(presaleRuleVo);
        }

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
        List<PresaleRule> presaleRulesByGoodsId = presaleRuleMapper.findPresaleRulesByGoodsId(goodsId);
        if (presaleRulesByGoodsId.size()==0) {
            return new ArrayList<PresaleRule>();
        }
        List<Object> presaleRuleObjects = new ArrayList<>(presaleRulesByGoodsId.size());
        for (PresaleRule presaleRule: presaleRulesByGoodsId) {
            presaleRuleObjects.add(presaleRule);
        }
        presaleRuleObjects=PageUtil.pageStart(page, limit,presaleRuleObjects);
        presaleRulesByGoodsId.clear();
        if (presaleRuleObjects.size()==0) {
            return presaleRulesByGoodsId;
        }
        for (Object presaleRuleObject : presaleRuleObjects) {
            presaleRulesByGoodsId.add((PresaleRule)presaleRuleObject);
        }
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

    public PresaleRule getPresaleRuleByGoodsId(Integer id) {
        return presaleRuleMapper.getPresaleRuleByGoodsId(id);
    }

    public List<Payment> presaleRulePayment(Order order, PresaleRule presaleRule) {
        BigDecimal prePay = BigDecimal.ZERO;
        BigDecimal finalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        for (OrderItem item: order.getOrderItemList()){
            prePay = prePay.add(presaleRule.getDeposit());
            finalPay = finalPay.add(presaleRule.getFinalPayment());
        }

        Payment prePayment = new Payment();
        prePayment.setActualPrice(prePay);
        //默认最大付款间隔是30min
        prePayment.setEndTime(now.plusMinutes(30));
        prePayment.setOrderId(order.getId());

        Payment finalPayment = new Payment();
        finalPayment.setActualPrice(finalPay);
        finalPayment.setBeginTime(presaleRule.getFinalStartTime());
        finalPayment.setEndTime(prePayment.getEndTime());
        finalPayment.setOrderId(order.getId());

        List<Payment> ret = new ArrayList<>(2);
        ret.add(prePayment);
        ret.add(finalPayment);
        return ret;
    }
}
