package com.xmu.discount.mapper;

import com.xmu.discount.discountdo.CouponRuleDo;
import com.xmu.discount.domain.CouponRulePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**Mapper接口，用于和数据库交互
 * @Author Zhang BingYuan
 * @Date 2019/12/8 15:25
 */
@Mapper
@Repository
public interface CouponRuleMapper {
    /**
     * 增加一个CouponRule
     * @param couponRulePo
     * @return
     */
    public Integer addCouponRulePo(CouponRulePo couponRulePo);
    /**
     * 根据id删除CouponRule
     * @param id
     * @return
     */
    public Integer deleteCouponRulePoById(Integer id);

    /**
     * 更新CouponRule
     * @param couponRulePo
     * @return
     */
    public Integer updateCouponRulePo(CouponRulePo couponRulePo);

    /**
     * 更新CouponRule
     * @param couponRulePo
     * @return
     */
    public Integer updateCouponRulePoOnlyTwo(CouponRulePo couponRulePo);

    /**
     * 通过id查找CouponRule
     * @param id
     * @return
     */
    public CouponRulePo findCouponRulePoById(Integer id);

    /**
     *  查看部分规则列表
     * @return
     */
    public List<CouponRulePo> adminGetAllCouponRulePos();

    /**
     * 获得CouponRule的id和goodsListn
     * @return
     */
    public List<CouponRuleDo> getAllCouponRuleDos();


    /**
     * 用户分页查找优惠券规则
     * @return
     */
    public List<CouponRulePo> userGetAllCouponRulePos();

    /**
     * 管理员下架优惠券规则
     * @param id
     * @return
     */
    public Integer adminUnShelveCouponRules(Integer id);

}
