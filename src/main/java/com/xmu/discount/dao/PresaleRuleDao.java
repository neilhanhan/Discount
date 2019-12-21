package com.xmu.discount.dao;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmu.discount.domain.Goods;
import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.mapper.PresaleRuleMapper;
import com.xmu.discount.service.GoodsService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.Packing;
import com.xmu.discount.util.PageUtil;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
//    OrderService orderService;

    public Object downPresaleRuleById(Integer id) {
        LocalDateTime modifiedTime= LocalDateTime.now();
        if(presaleRuleMapper.findPresaleRuleById(id) == null){
            return ResponseUtil.presaleRuleUnknown();
        }else if(presaleRuleMapper.downPresaleRuleById(id,modifiedTime)){
//            orderService.refundOfPresaleRule(presaleRule);
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail(736,"预售活动下架失败");
        }
    }

    public Object deletePresaleRule(Integer id) {
        if(presaleRuleMapper.deletePresaleRule(id)){
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
        List<PresaleRuleVo> presaleRuleVoList = Packing.presaleRulePacking(presaleRuleList);
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
        return ResponseUtil.ok(subList);
    }

    public Object customerGetPresaleRule(Integer page, Integer limit) {
        List<PresaleRule> presaleRuleList = presaleRuleMapper.customerGetPresaleRule();
        if (presaleRuleList.size()==0) {
            return ResponseUtil.presaleRuleUnknown();
        }
        List<PresaleRuleVo> presaleRuleVoList = Packing.presaleRulePacking(presaleRuleList);
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
        return ResponseUtil.ok(subList);
    }

    public Object getPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleMapper.getPresaleRuleById(id);
        if(presaleRule == null){
            return ResponseUtil.presaleRuleUnknown();
        }else {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
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
}
