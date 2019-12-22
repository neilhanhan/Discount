package com.xmu.discount.controller;

import com.xmu.discount.domain.*;
import com.xmu.discount.service.CouponRuleService;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.service.GrouponRuleService;
import com.xmu.discount.service.PresaleRuleService;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Bingyuan
 */
@RestController
@RequestMapping("/discountService")
public class DiscountController {

    @Autowired
    GrouponRuleService grouponRuleService;
    @Autowired
    PresaleRuleService presaleRuleService;
    @Autowired
    public CouponService couponService;
    @Autowired
    public CouponRuleService couponRuleService;


    @GetMapping("/orders")
    public Object discountOrder(@RequestBody Order order){
        Integer couponId = order.getCouponId();
        List<OrderItem> orderItemList = order.getOrderItemList();
        if(couponId==null){//使用优惠券

        }else {

            if(orderItemList.size()!=1){
                for(OrderItem item:orderItemList){
                    //都是普通商品
                    item.setItemType(0);
                }
                order.setOrderItemList(orderItemList);
            }else {//预售或团购订单或只有一个item的普通订单
                List<OrderItem> newOrderItemList = new ArrayList<>();
                OrderItem item = orderItemList.get(0);
                Integer goodsId = item.getGoodsId();
                if(grouponRuleService.getGrouponRuleOnshelve(goodsId)!=null){
                    item.setItemType(2);
                    newOrderItemList.add(item);
                    order.setOrderItemList(newOrderItemList);
                }else if(presaleRuleService.getPresaleRuleByGoodsId(goodsId)!=null){
                    item.setItemType(1);
                    newOrderItemList.add(item);
                    order.setOrderItemList(newOrderItemList);
                    PresaleRule rule = presaleRuleService.getPresaleRuleByGoodsId(goodsId);
                    List<Payment> payments = presaleRuleService.presaleRulePayment(order,rule);
                    order.setPaymentList(payments);
                }else {
                    item.setItemType(0);
                    newOrderItemList.add(item);
                    order.setOrderItemList(newOrderItemList);
                }
            }
        }
        return ResponseUtil.ok(order);
    }

    /**
     * 管理员查看部分优惠券规则列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admin/couponRules")
    public Object adminGetAllCouponRulePos(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<CouponRulePo> couponRulePos = couponRuleService.adminGetAllCouponRulePos(page, limit);
        if (couponRulePos.size()==0) {
            return ResponseUtil.checkCouponRuleFail();
        }
        return ResponseUtil.ok(couponRulePos);
    }

    /**
     * 普通用户查看优惠券规则
     */
    @GetMapping("/couponRules")
    public Object userGetAllCouponRulePos(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<CouponRulePo> couponRulePos = couponRuleService.userGetAllCouponRulePos(page, limit);
        if (couponRulePos.size()==0) {
            return ResponseUtil.checkCouponRuleFail();
        }
        return ResponseUtil.ok(couponRulePos);
    }

    /**
     * 添加优惠券规则
     */
    @PostMapping("/couponRules")
    public Object addCouponRulePo(@RequestBody CouponRulePo couponRulePo) {
        if (couponRulePo == null) {
            return ResponseUtil.couponRuleAddFail();
        }
        CouponRulePo couponRulePo1 = couponRuleService.addCouponRulePo(couponRulePo);
        if (couponRulePo1.equals(false)) {
            return ResponseUtil.couponRuleAddFail();
        }
        return ResponseUtil.ok(couponRulePo1);
    }

    /**
     * 查看一种优惠券规则
     */
    @GetMapping("/couponRules/{id}")
    public Object findCouponRule(@PathVariable Integer id) throws Exception {
        CouponRulePo couponRulePoById = couponRuleService.findCouponRulePoById(id);
        if (couponRulePoById.equals(null)) {
            return ResponseUtil.invaildCouponRuleFail();
        }
        return ResponseUtil.ok(couponRulePoById);

    }

    /**
     * 修改优惠券规则
     */
    @PutMapping("/couponRules/{id}")
    public Object updateCouponRule(@PathVariable Integer id, @RequestBody CouponRulePo couponRulePo) {
        CouponRulePo couponRulePo1 = couponRuleService.updateCouponRulePo(id, couponRulePo);
        if (couponRulePo1.equals(null)) {
            return ResponseUtil.couponRuleUpdateFail();
        }
        return ResponseUtil.ok(couponRulePo1);

    }

    /**
     * 删除一种优惠券规则
     */
    @DeleteMapping("/couponRules/{id}")
    public Object deleteCouponRulePo(@PathVariable Integer id) {
        Integer integer = couponRuleService.deleteCouponRulePoById(id);
        if (!integer.equals(1)) {
            return ResponseUtil.couponRuleDeleteFail();
        }
        return ResponseUtil.ok();
    }


    /**
     * 获取特定类型的的优惠券
     */
    @GetMapping("/coupons")
    public Object getAllCoupons(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("showType") Integer showType) throws Exception {
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<Coupon> coupons = couponService.getAllStatusCoupons(page, limit, showType);
        return ResponseUtil.ok(coupons);
    }


    /**
     *增加优惠券
     * @param couponPo
     * @return
     */
    @PostMapping("/coupons")
    public Object addCoupon(@RequestBody CouponPo couponPo) {
        CouponPo couponPo1 = couponService.addCouponPo(couponPo);
        if (couponPo1.equals(null)) {
            return ResponseUtil.getCouponFail();
        }
        return ResponseUtil.ok(couponPo1);
    }

    /**
     * 获取可用的优惠券
     *
     * @param cartItemList
     * @return
     */
    @PostMapping("/coupons/availableCoupons")
    public Object getAvailableCoupons(@RequestBody List<CartItem> cartItemList) throws Exception {
        List<Coupon> availableCoupons = couponService.getAvailableCoupons(cartItemList);
        if(availableCoupons.size()==0) {
            return ResponseUtil.checkCouponRuleFail();
        }
        return ResponseUtil.ok(availableCoupons);
    }

    /**
     * 下架优惠券规则
     *
     * @param id
     * @return
     */
    @PostMapping("/couponRules/{id}/invalid")
    public Object adminUnShelveCouponRules(@PathVariable Integer id) {
        Boolean bool = couponRuleService.adminUnShelveCouponRules(id);
        if (!bool) {
            return ResponseUtil.invaildCouponFail();
        }
        return ResponseUtil.ok();
    }
}
