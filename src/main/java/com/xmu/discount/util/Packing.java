package com.xmu.discount.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmu.discount.domain.Goods;
import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.service.GoodsService;
import com.xmu.discount.vo.GrouponRuleVo;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuwenhan
 */
public class Packing {

    @Autowired
    static GoodsService goodsService;

    public static List<GrouponRuleVo> grouponRulePoPacking(List<GrouponRulePo> grouponRulePoList){
        List<GrouponRuleVo> grouponRuleVoList = new ArrayList<>();
        for (GrouponRulePo grouponRulePo : grouponRulePoList) {
            Integer goodsId = grouponRulePo.getGoodsId();
            String str = JacksonUtil.toJson(goodsService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
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
        return grouponRuleVoList;
    }

    public static List<PresaleRuleVo> presaleRulePacking(List<PresaleRule> presaleRuleList) {
        List<PresaleRuleVo> presaleRuleVoList = new ArrayList<>();
        for (PresaleRule presaleRule : presaleRuleList) {
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
            if (goodsPo == null) {
                continue;
            }
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVoList.add(presaleRuleVo);
        }
        return presaleRuleVoList;
    }
}
