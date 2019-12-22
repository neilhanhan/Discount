package com.xmu.discount.mapper;

import com.xmu.discount.domain.Coupon;
import com.xmu.discount.domain.CouponPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**Mapper接口，用于和数据库交互
 * @Author Zhang BingYuan
 * @Date 2019/12/8 15:25
 */
@Mapper
@Repository
public interface CouponMapper {
    /**
     *增加优惠券
     * @param couponPo
     * @return
     */
    public Integer addCouponPo(CouponPo couponPo);
    /**
     * 用id删除优惠券
     * @param id
     * @return
     */
    public boolean deleteCouponById(Integer id);
    /**
     * 用id更新优惠券
     * @param coupon
     * @return
     */
    public boolean updateCoupon(Coupon coupon);
    /**
     * 用id找到优惠券
     * @param id
     * @return
     */
    public CouponPo findCouponById(Integer id);

    /**
     * 获取所有特定类型的优惠券
     * @param showType
     * @return
     */
    public List<CouponPo> getAllStatusCouponPos(Integer showType);

    /**
     * 通过couponRule的Id获取coupon（其couponRuleId等于此couponRule的Id）
     * @param couponRuleId
     * @return
     */
    public List<CouponPo> getCouponPoByCouponRuleId(Integer couponRuleId);

    /**
     * 管理员下架优惠券规则下的所有优惠券
     * @param couponRuleId
     * @return
     */
    public Boolean adminUnShelveCoupons(Integer couponRuleId);

    /**
     * 更新优惠券被使用
     * @param couponPo
     * @return
     */
    public Integer updateUserCouponStatus(CouponPo couponPo);

}
