package com.xmu.discount.service;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.discountdo.CouponRuleDo;
import com.xmu.discount.domain.CouponPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Zhang Bingyuan
 * @version 1.0
 * @date 2019/12/20 19:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {
    @Autowired
    CouponService couponService;
    @Autowired
    CouponRuleDao couponRuleDao;
    @Autowired
    CouponDao couponDao;

    public HashMap<Integer, List<Integer>> getCouponRuleGoodIds(List<CouponRuleDo> allCouponRuleDos) {
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>(allCouponRuleDos.size());
        for (CouponRuleDo allCouponRuleDo : allCouponRuleDos) {
            String goodsIdList1 = allCouponRuleDo.getGoodsList1();
            String goodsIdList2 = allCouponRuleDo.getGoodsList2();
            List<Integer> goodIdList = new ArrayList<Integer>();
            String[] array = new String[5000];
            array = goodsIdList1.split(",");
            for (String s : array) {
                goodIdList.add(Integer.valueOf(s));
            }
            array = goodsIdList2.split(",");
            for (String s : array) {
                if (s.equals("")) continue;
                goodIdList.add(Integer.valueOf(s));
            }
            map.put(allCouponRuleDo.getId(), goodIdList);
        }
        return map;
    }

    @Test
    public void testGetCouponRuleGoodIds() {
        List<CouponRuleDo> allCouponRuleDos = couponRuleDao.getAllCouponRuleDos();
        HashMap<Integer, List<Integer>> map = getCouponRuleGoodIds(allCouponRuleDos);
        for (Integer key : map.keySet()) {
            System.out.println(key + "  ");
            for (Integer id : map.get(key)) {
                System.out.println(id);
            }
        }
    }


    public List<CouponPo> getCouponPosByCouponRuleIds(List<Integer> couponRuleIds) {
        List<CouponPo> couponPos = new ArrayList<>();
        for (Integer couponRuleId : couponRuleIds) {
            List<CouponPo> couponPoList = couponDao.getCouponPoByCouponRuleId(couponRuleId);
            couponPos.addAll(couponPoList);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Iterator<CouponPo> iterator = couponPos.iterator();
        while(iterator.hasNext()){
            CouponPo couponPo = iterator.next();
            if (couponPo.getEndTime().isBefore(localDateTime) || couponPo.getBeginTime().isAfter(localDateTime)) {
                iterator.remove();   //注意这个地方
            }
        }
        return couponPos;
    }

    @Test
    public void testGetCouponPosByCouponRuleIds() {
        List<Integer> couponRuleIds = new ArrayList<>();
        couponRuleIds.add(253);
        couponRuleIds.add(254);
        List<CouponPo> couponPos = getCouponPosByCouponRuleIds(couponRuleIds);
        for (CouponPo couponPo : couponPos) {
            System.out.println(couponPo);
        }
    }
}
