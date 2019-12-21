package com.xmu.discount.dao;

import com.github.pagehelper.PageHelper;
import com.xmu.discount.domain.Coupon;
import com.xmu.discount.domain.CouponPo;
import com.xmu.discount.mapper.CouponMapper;
import com.xmu.discount.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 15:19
 */
@Repository
public class CouponDao {
    @Autowired
    CouponMapper couponMapper;

    /**
     * 用id找到优惠券
     *
     * @param id
     * @return
     */
    public Coupon findCouponById(Integer id) {
        Coupon coupon = couponMapper.findCouponById(id);
        return coupon;
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
        if (integer==1) {
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
        if (couponPos.size()==0) {
            return couponPos;
        }
        List<Object> couponPoObjects = new ArrayList<>(couponPos.size());
        for (CouponPo couponPo : couponPos) {
            couponPoObjects.add(couponPo);
        }
        couponPoObjects=PageUtil.pageStart(page, limit,couponPoObjects);
        couponPos.clear();
        if (couponPoObjects.size()==0) {
            return couponPos;
        }
        for (Object couponPoObject : couponPoObjects) {
            couponPos.add((CouponPo)couponPoObject);
        }
        return couponPos;
    }


    /**
     * 通过couponRule的Id获取coupon（其couponRuleId等于此couponRule的Id）
     * @param couponRuleId
     * @return
     */
    public List<CouponPo> getCouponPoByCouponRuleId(Integer couponRuleId) {
        List<CouponPo> couponPoByCouponRuleId = couponMapper.getCouponPoByCouponRuleId(couponRuleId);
        return couponPoByCouponRuleId;
    }

    /**
     * 管理员下架所有优惠券规则下的优惠券
     * @param couponRuleId
     * @return
     */
    public Boolean adminUnShelveCoupons(Integer couponRuleId) {
        Boolean bool = couponMapper.adminUnShelveCoupons(couponRuleId);
        return bool;
    }

}
