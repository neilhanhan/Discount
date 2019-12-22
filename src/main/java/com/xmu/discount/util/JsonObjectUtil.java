package com.xmu.discount.util;

import com.alibaba.fastjson.JSONObject;
import com.xmu.discount.util.couponstrategy.OnlyNameAndObj;
import com.xmu.discount.util.couponstrategy.StrategyName;
import com.xmu.discount.util.couponstrategy.includename.CashOffStrategyName;
import com.xmu.discount.util.couponstrategy.includename.NumberStrategyName;
import com.xmu.discount.util.couponstrategy.includename.PercentageStrategyName;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/19 10:17
 * <p>
 * 该字段在数据库中的格式从下面三选一，分别表示三种优惠券类型（策略类名称）。x表示整数，xx.xx表示带2位小数的数字，0.xx表示保留两位的百分比数字。
 * {"name":"NumberStrategy", "obj":{"threshold":x, "offCash":xx.xx}}
 * {"name":"CashOffStrategy", "obj":{"threshold":xx.xx, "offCash":xx.xx}}
 * {"name":"PercentageStrategy", "obj":{"threshold":xx.xx, "percentage":0.xx}}
 */
public class JsonObjectUtil {
    public static Object getCouponStrategy(String json) {
        String name = new String();

        JSONObject jsonObject = JSONObject.parseObject(json);
        OnlyNameAndObj onlyNameAndObj = JSONObject.toJavaObject(jsonObject, OnlyNameAndObj.class);

        name = onlyNameAndObj.getName();
        Object strategy = new Object();
        /**
         * 根据名字创建对象
         */
        if (StrategyName.CashOffStrategy.toString().equals(name)) {
            CashOffStrategyName cashOffStrategyName = JSONObject.toJavaObject(jsonObject, CashOffStrategyName.class);
            strategy = cashOffStrategyName.getObj();

        } else if (StrategyName.NumberStrategy.toString().equals(name)) {
            NumberStrategyName numberStrategyName = JSONObject.toJavaObject(jsonObject, NumberStrategyName.class);
            strategy = numberStrategyName.getObj();
        } else if (StrategyName.PercentageStrategy.toString().equals(name)) {
            PercentageStrategyName percentageStrategyName = JSONObject.toJavaObject(jsonObject, PercentageStrategyName.class);
            strategy = percentageStrategyName.getObj();
        } else {
            System.out.println("json解析错误,name:"+name+"  json:"+json);
        }
        return strategy;
    }
}
