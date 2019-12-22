package com.xmu.discount.util;

import com.xmu.discount.util.couponstrategy.abstractcouponstrategy.AbstractCouponStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/19 11:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonObjectUtilTest {
    @Test
    public void JsonObjectTest() {

        String json = "{\"name\":\"PercentageStrategy\", \"obj\":{\"threshold\":1.99, \"percentage\":0.31}}";
        AbstractCouponStrategy couponStrategy = (AbstractCouponStrategy) JsonObjectUtil.getCouponStrategy(json);
        System.out.println(couponStrategy);
    }
}
