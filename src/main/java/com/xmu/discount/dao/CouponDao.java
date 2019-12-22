package com.xmu.discount.dao;

import com.xmu.discount.domain.*;
import com.xmu.discount.mapper.CouponMapper;
import com.xmu.discount.mapper.CouponRuleMapper;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.JsonObjectUtil;
import com.xmu.discount.util.PageUtil;
import com.xmu.discount.util.couponstrategy.abstractcouponstrategy.AbstractCouponStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 15:19
 */
@Repository
public class CouponDao {
    private static final Logger logger = LoggerFactory.getLogger(CouponDao.class);
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponRuleMapper couponRuleMapper;

    /**
     * 用id找到优惠券
     *
     * @param id
     * @return
     */
    public CouponPo findCouponById(Integer id) {
        CouponPo couponPo = couponMapper.findCouponById(id);
        return couponPo;
    }

    /**
     * 增加优惠券
     *
     * @param couponPo
     * @return
     */
    public CouponPo addCouponPo(CouponPo couponPo) {
        LocalDateTime localDateTime = LocalDateTime.now();
        couponPo.setGmtCreate(localDateTime);
        couponPo.setGmtModified(localDateTime);
        Integer integer = couponMapper.addCouponPo(couponPo);
        if (integer == 1) {
            couponPo.setBeDeleted(false);
            return couponPo;
        }
        return null;
    }

    /**
     * 用id更新优惠券
     *
     * @param id
     * @param coupon
     * @return
     */
    public boolean updateCouponById(Integer id, Coupon coupon) {
        coupon.setId(id);
        boolean bool = couponMapper.updateCoupon(coupon);
        return bool;
    }

    /**
     * 用id删除优惠券
     *
     * @param id
     * @return
     */
    public boolean deleteCouponById(Integer id) {
        boolean bool = couponMapper.deleteCouponById(id);
        return bool;
    }

    /**
     * 获取所有优惠券
     *
     * @return
     */
    public List<CouponPo> getAllStatusCouponPos(Integer page, Integer limit, Integer showType) {
        List<CouponPo> couponPos = couponMapper.getAllStatusCouponPos(showType);
        if (couponPos.size() == 0) {
            return couponPos;
        }
        List<Object> couponPoObjects = new ArrayList<>(couponPos.size());
        for (CouponPo couponPo : couponPos) {
            couponPoObjects.add(couponPo);
        }
        couponPoObjects = PageUtil.pageStart(page, limit, couponPoObjects);
        couponPos.clear();
        if (couponPoObjects.size() == 0) {
            return couponPos;
        }
        for (Object couponPoObject : couponPoObjects) {
            couponPos.add((CouponPo) couponPoObject);
        }
        return couponPos;
    }


    /**
     * 通过couponRule的Id获取coupon（其couponRuleId等于此couponRule的Id）
     *
     * @param couponRuleId
     * @return
     */
    public List<CouponPo> getCouponPoByCouponRuleId(Integer couponRuleId) {
        List<CouponPo> couponPoByCouponRuleId = couponMapper.getCouponPoByCouponRuleId(couponRuleId);
        return couponPoByCouponRuleId;
    }

    /**
     * 管理员下架所有优惠券规则下的优惠券
     *
     * @param couponRuleId
     * @return
     */
    public Boolean adminUnShelveCoupons(Integer couponRuleId) {
        Boolean bool = couponMapper.adminUnShelveCoupons(couponRuleId);
        return bool;
    }

    /**
     * 优惠券被使用后更新
     *
     * @param userId
     * @param couponId
     * @return
     */
    public Integer updateUserCouponStatus(Integer userId, Integer couponId) {
        CouponPo couponPo = new CouponPo();
        couponPo.setId(couponId);
        couponPo.setStatusCode(1);
        couponPo.setUsedTime(LocalDateTime.now());
        couponPo.setGmtModified(LocalDateTime.now());
        couponPo.setUserId(userId);
        return couponMapper.updateUserCouponStatus(couponPo);
    }


    /**
     * 计算使用优惠券后各明细的价格，返回新的明细
     *
     * @param orderItems
     * @param couponId
     * @return
     */
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId) {
        List<OrderItem> validItems = getValidItems(orderItems, couponId);

        CouponPo coupon = couponMapper.findCouponById(couponId);

        CouponRulePo couponRule = couponRuleMapper.findCouponRulePoById(coupon.getCouponRuleId());

        if (validItems.size() != 0) {
            AbstractCouponStrategy couponStrategy = (AbstractCouponStrategy) JsonObjectUtil.getCouponStrategy(couponRule.getStrategy());
            System.out.println("使用优惠券"+couponStrategy);
            List<OrderItem> newItems=couponStrategy.cacuDiscount(orderItems,couponId);
            return newItems;
        } else {
            return orderItems;
        }

    }

    /**
     * 获得能用于此优惠卷的明细
     *
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItem> getValidItems(List<OrderItem> items, Integer couponId) {
        System.out.println("getValidItems参数：items = " + items);
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        CouponPo coupon = couponMapper.findCouponById(couponId);
        System.out.println("coupon");
        Integer couponRuleId = coupon.getCouponRuleId();
        CouponRulePo couponRule = couponRuleMapper.findCouponRulePoById(couponRuleId);
        System.out.println("couponRule");

        for (OrderItem item : items) {
            Integer goodsId = item.getProduct().getGoodsId();
            System.out.println("goodsId"+goodsId);
            if (isUsedOnGoods(goodsId, couponRule)) {
                validItems.add(item);
            }
        }
        if(validItems.size()==0) {
            System.out.println("使用优惠券的item数=0");
        }
        return validItems;
    }



    /**
     * 判断商品是否可用于该优惠券
     *
     * @param goodsId
     * @param couponRule
     * @return
     */
    private boolean isUsedOnGoods(Integer goodsId, CouponRulePo couponRule) {
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.clear();
        goodsIds.addAll(getGoodsIdsInCouponRule(couponRule));
        return goodsIds.contains(goodsId);

    }

    /**
     * 获得适用商品id列表
     * * { gIDs：[xxx,xxx,xxx,xxx,xxx]}
     *
     * @param couponRule
     * @return
     */
    public List<Integer> getGoodsIdsInCouponRule(CouponRulePo couponRule) {
        String jsonString1 = couponRule.getGoodsList1();
        String jsonString2 = couponRule.getGoodsList2();
        jsonString1 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString1);
        jsonString2 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString2);
        System.out.println("jsonString1=" + jsonString1);
        System.out.println("jsonString2=" + jsonString2);

        List<Integer> goodsId1 = new ArrayList<>();
        List<Integer> goodsId2 = new ArrayList<>();
        if (jsonString1 != null&&!"".equals(jsonString1)) {
            goodsId1 = stringToInts(jsonString1);
            System.out.println("goodsId1参数" + goodsId1);
        }
        if (jsonString2 != null&&!"".equals(jsonString2)) {
            goodsId2 = stringToInts(jsonString2);
            System.out.println("goodsId2参数" + goodsId2);
        }

        if (goodsId2.size() != 0) {
            goodsId1.addAll(goodsId2);
        }
        System.out.println(goodsId1);
        return goodsId1;
    }


    public List<Integer> stringToInts(String string) {
        String[] split = string.split(",");
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            integers.add(Integer.valueOf(split[i]));
        }
        return integers;
    }
}
