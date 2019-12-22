package com.xmu.discount.util;

import com.xmu.discount.domain.Coupon;
import com.xmu.discount.domain.CouponPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/17 21:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FatherChildUtilTest {

    @Test
    public void fatherChild() throws Exception {
        Coupon coupon = new Coupon();
        CouponPo couponPo = new CouponPo();
        couponPo.setUserId(111);
        couponPo.setCouponSn("look");
        couponPo.setName("12315");
        FatherChildUtil.fatherToChild(couponPo, coupon);
        System.out.println(coupon.getName());
    }

    @Test
    public void array() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Integer integer = new Integer(i);
            list.add(integer);
        }

        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
