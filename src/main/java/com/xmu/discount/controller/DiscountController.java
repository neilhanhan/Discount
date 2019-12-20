package com.xmu.discount.controller;

import com.xmu.discount.domain.CartItem;
import com.xmu.discount.domain.Coupon;
import com.xmu.discount.domain.CouponPo;
import com.xmu.discount.domain.CouponRulePo;
import com.xmu.discount.service.CouponRuleService;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.service.GrouponRuleService;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liuwenhan
 */

@Component
@EnableScheduling
@EnableAsync
@RestController
@RequestMapping(value = "/discountService", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
public class DiscountController {

    @Autowired
    GrouponRuleService grouponRuleService;
    @Autowired
    public CouponService couponService;
    @Autowired
    public CouponRuleService couponRuleService;

    /**
     *
     * 定时任务：每天晚上12点检查前一天完成的团购活动并退款
     *
     * @throws InterruptedException
     */
    @Async
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void checkFinishedGrouponRule() throws InterruptedException{
        grouponRuleService.checkFinishedGrouponRule();
    }

    /**
     * 管理员查看部分优惠券列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admin/couponRules")
    public Object adminGetAllCouponRulePos(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        List<CouponRulePo> couponRulePos = couponRuleService.adminGetAllCouponRulePos(page, limit);
        return ResponseUtil.ok(couponRulePos);
    }

    /**
     * 普通用户查看优惠券
     */
    @GetMapping("/couponRules")
    public Object userGetAllCouponRulePos(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        List<CouponRulePo> couponRulePos = couponRuleService.userGetAllCouponRulePos(page, limit);
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
        return ResponseUtil.ok(availableCoupons);
    }


    @PostMapping("/couponRules/{id}/invalid")
    public Object adminUnShelveCouponRules(@PathVariable Integer id) {
        Boolean bool = couponRuleService.adminUnShelveCouponRules(id);
        if (!bool) {
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok();
    }
}
