package com.xmu.discount.dao;

import com.xmu.discount.discountdo.CouponRuleDo;
import com.xmu.discount.domain.CouponRulePo;
import com.xmu.discount.mapper.CouponRuleMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/17 19:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CouponRuleDaoTest {
    @Autowired
    CouponRuleDao couponRuleDao;
    @Autowired
    CouponRuleMapper couponRuleMapper;

    static CouponRulePo couponRulePo = new CouponRulePo();

    @BeforeEach
    public void initCouponRulePo() {
        couponRulePo.setCollectedNum(0);
        couponRulePo.setBrief("sijad");
        couponRulePo.setGoodsList1("1,2,3,4,7,8,9");
        couponRulePo.setPicUrl("url");
        System.out.println("CouponRuleDaoTest");
    }

    @Test
    public void getAllCouponRuleVos() {
        List<CouponRuleDo> allCouponRuleDos = couponRuleMapper.getAllCouponRuleDos();
        /*
        测试拿到List<CouponRuleVo>
         */
        for (CouponRuleDo allCouponRuleDo : allCouponRuleDos) {
            System.out.println(allCouponRuleDo.toString());
        }
    }

    @Test
    public void addCouponRulePo() {

        for (int i = 0; i < 100; i++) {
            CouponRulePo couponRulePo1 = couponRuleDao.addCouponRulePo(couponRulePo);
            System.out.println(couponRulePo1);
        }
    }
}
