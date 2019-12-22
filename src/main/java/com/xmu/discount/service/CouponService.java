package com.xmu.discount.service;

import com.xmu.discount.domain.CartItem;
import com.xmu.discount.domain.Coupon;
import com.xmu.discount.domain.CouponPo;
import com.xmu.discount.domain.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 12:02
 */
public interface CouponService {

    /**
     * 用id找到Coupon对象
     * @param id
     * @return
     */
    public CouponPo findCouponById(Integer id);

    /**
     * 增加Coupon对象
     * @param couponPo
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public CouponPo addCouponPo(CouponPo couponPo);

    /**
     * 用id更新Coupon对象
     * @param id
     * @param coupon
     * @return
     */
    public boolean updateCouponById(Integer id, Coupon coupon);

    /**
     * 用id删除Coupon对象
     * @param id
     * @return
     */
    public boolean deleteCouponById(Integer id);

    /**
     * 获取所有特定状态优惠券
     * @param page
     * @param limit
     * @param showType
     * @return
     * @throws Exception
     */
    public List<Coupon> getAllStatusCoupons(Integer page, Integer limit, Integer showType) throws Exception;

    /**
     * 获取可用的优惠券
     * @param cartItemList
     * @return
     * @throws Exception
     */
    public List<Coupon> getAvailableCoupons(List<CartItem> cartItemList) throws Exception;


    /**
     * 计算每个OrderItem的价格
     * @param orderItems
     * @param couponId
     * @return
     */
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId);

    /**
     * 优惠券被使用后更新
     * @param userId
     * @param couponId
     * @return
     */
    public Integer updateUserCouponStatus(Integer userId, Integer couponId);
}
