package com.xmu.discount.controller;

import com.xmu.discount.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/20 10:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountControllerTest {
    @Autowired
    DiscountController discountController;

    static CouponRulePo couponRulePo = new CouponRulePo();
    static CouponPo couponPo = new CouponPo();

    @Before
    public void getCouponRulePo() {
        System.out.println("DiscountControllerTest");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime beginTime = LocalDateTime.parse("2019-09-28 17:07:05", df);
        LocalDateTime endTime = LocalDateTime.parse("2020-09-28 17:07:05", df);

        couponRulePo.setName("优惠券规则");
        couponRulePo.setBeginTime(beginTime);
        couponRulePo.setEndTime(endTime);
        couponRulePo.setValidPeriod(100);
        couponRulePo.setTotal(100);
        couponRulePo.setCollectedNum(0);
        couponRulePo.setStrategy("{\"name\":\"NumberStrategy\", \"obj\":{\"threshold\":4, \"offCash\":9.98}}");
        couponRulePo.setBrief("This is a couponrule");
        couponRulePo.setGoodsList1("1,2,3,4,7,8,9");
        couponRulePo.setGoodsList2("");
        couponRulePo.setPicUrl("url");
        couponRulePo.setStatusCode(true);

        couponPo.setName("优惠券");
        couponPo.setUserId(123);
        couponPo.setBeginTime(beginTime);
        couponPo.setEndTime(endTime);
        couponPo.setCouponRuleId(53);
        couponPo.setUsedTime(LocalDateTime.now());
        couponPo.setCouponSn("6545468549dasdada");
        couponPo.setPicUrl("http://sakjdgjaskgdbiada");
        couponPo.setStatusCode(0);
    }


    @Test
    public void adminGetAllCouponRulePos() throws Exception {
        Object allCoupons = discountController.adminGetAllCouponRulePos(3, 6);
        System.out.println(allCoupons);
    }

    @Test
    public void addCouponRulePo() {
        for (int i = 100; i > 0; i--) {
            Object object = discountController.addCouponRulePo(couponRulePo);
            System.out.println(object);
        }
    }

    @Test
    public void userGetAllCouponRulePos() {
        Object allCoupons = discountController.userGetAllCouponRulePos(3, 6);
        System.out.println(allCoupons);
    }

    @Test
    public void findCouponRule() throws Exception {
        Object couponRule = discountController.findCouponRule(276);
        System.out.println(couponRule);
    }

    @Test
    public void updateCouponRule() {
        couponRulePo.setBrief("修改了");
        System.out.println(couponRulePo);
        Object object = discountController.updateCouponRule(276, couponRulePo);
        System.out.println(object);
    }

    @Test
    public void deleteCouponRule() {
        discountController.deleteCouponRulePo(276);
    }

    @Test
    public void addCoupon() {
        for (int i = 0; i < 50; i++) {
            Object object = discountController.addCoupon(couponPo);
            System.out.println(object);
        }
    }

    @Test
    public void getCoupons() throws Exception {
        Object allCoupons = discountController.getAllCoupons(1, 6, 1);
        System.out.println(allCoupons);
    }

    @Test
    public void getAvailableCoupons() {

    }

    @Test
    public void adminUnShelveCouponRules() {
        Object object = discountController.adminUnShelveCouponRules(253);
        System.out.println(object);
    }

    @Test
    public void discountOrder() {
        Order order = new Order();
        ArrayList<OrderItem> orderItems =new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product =new Product();
        Goods goods=new Goods();
        goods.setId(1);
        product.setGoods(goods);
        product.setGoodsId(1);
        orderItem.setProduct(product);
        orderItem.setNumber(10);
        orderItem.setPrice(BigDecimal.TEN);
        orderItems.add(orderItem);
        order.setOrderItemList(orderItems);
        order.setCouponId(42);
        User user =new User();
        user.setId(123);
        order.setUser(user);
        Object o = discountController.discountOrder(order);
        System.out.println(o);
    }
}
