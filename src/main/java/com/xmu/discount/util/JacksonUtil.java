package com.xmu.discount.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xmu.discount.domain.GrouponRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuwenhan
 */
public class JacksonUtil {


    /**
     * 团购等级（满多少人组团多少折扣）
     * JSON格式: {"strategy": [{"lowerbound":xxx, "upperbound":xxx, "rate":xxx}]}, xxx为具体数值
     */
    public static List<GrouponRule.Strategy> getGrouponRuleStrategy(String json) {
        List<GrouponRule.Strategy> dataList;
        dataList = new ArrayList<>();
        try {
            JSONObject rootObject = JSONObject.parseObject(json);
            JSONArray strategyArray = rootObject.getJSONArray("strategy");
            for (int i = 0; i < strategyArray.size(); i++) {
                JSONObject sonObject = strategyArray.getJSONObject(i);
                Integer lowerbound = sonObject.getInteger("lowerbound");
                Integer upperbound = sonObject.getInteger("upperbound");
                BigDecimal rate = sonObject.getBigDecimal("rate");
                GrouponRule.Strategy strategy = new GrouponRule.Strategy();
                strategy.setLowerBound(lowerbound);
                strategy.setUpperBound(upperbound);
                strategy.setDiscountRate(rate);
                dataList.add(strategy);
            }
            return dataList;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
