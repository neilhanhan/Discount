package com.xmu.discount.dao;

import com.xmu.discount.domain.CouponPo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 19:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CouponDaoTest {
    @Autowired
    CouponDao couponDao;

    static CouponPo couponPo = new CouponPo();

    @BeforeAll
    public static void getCouponPo() {

        couponPo.setCouponSn("look");
        couponPo.setName("12315");
    }

    @Test
    void findCouponById() {
    }

    @Test
    void addCoupon() {
        System.out.println("my  "+couponDao.addCouponPo(couponPo));
    }

    @Test
    void updateCouponById() {
    }

    @Test
    void deleteCouponById() {
    }
}
