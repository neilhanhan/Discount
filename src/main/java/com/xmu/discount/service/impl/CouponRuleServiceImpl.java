package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.domain.CouponRulePo;
import com.xmu.discount.service.CouponRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 12:05
 */
@Service
public class CouponRuleServiceImpl implements CouponRuleService {

    @Autowired
    public CouponRuleDao couponRuleDao;
    @Autowired
    public CouponDao couponDao;

    /**
     * 增加一个CouponRule
     *
     * @param couponRulePo
     * @return
     */
    @Override
    public CouponRulePo addCouponRulePo(CouponRulePo couponRulePo) {
        CouponRulePo couponRulePo1 = couponRuleDao.addCouponRulePo(couponRulePo);
        return couponRulePo1;
    }

    /**
     * 根据id查找CouponRule
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteCouponRulePoById(Integer id) {
        Integer integer = couponRuleDao.deleteCouponRulePoById(id);
        return integer;
    }

    /**
     * 通过id更新CouponRule
     *
     * @param id
     * @param couponRulePo
     * @return
     */
    @Override
    public CouponRulePo updateCouponRulePo(Integer id, CouponRulePo couponRulePo) {
        CouponRulePo couponRulePo1 = couponRuleDao.updateCouponRulePo(id, couponRulePo);
        return couponRulePo1;
    }

    /**
     * 通过id查找CouponRule
     *
     * @param id
     * @return
     */
    @Override
    public CouponRulePo findCouponRulePoById(Integer id) throws Exception {
        CouponRulePo couponRulePo = couponRuleDao.findCouponRulePoById(id);
        return couponRulePo;
    }


    /**
     * 管理员分页获取部分的优惠券规则
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<CouponRulePo> adminGetAllCouponRulePos(Integer page, Integer limit) {
        List<CouponRulePo> allCouponRulePos = couponRuleDao.adminGetAllCouponRulePos(page, limit);
        if (allCouponRulePos.size()==0) {
            return allCouponRulePos;
        }
        for (CouponRulePo allCouponRulePo : allCouponRulePos) {
            allCouponRulePo.setBeDeleted(false);
        }
        return allCouponRulePos;
    }

    /**
     * 用户分页获取部分优惠券规则
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<CouponRulePo> userGetAllCouponRulePos(Integer page, Integer limit) {
        List<CouponRulePo> allCouponRulePos = couponRuleDao.userGetAllCouponRulePos(page, limit);
        if (allCouponRulePos.size()==0) {
            return allCouponRulePos;
        }
        for (CouponRulePo allCouponRulePo : allCouponRulePos) {
            allCouponRulePo.setBeDeleted(false);
        }
        return allCouponRulePos;
    }

    /**
     * 管理员下架优惠券规则
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Boolean adminUnShelveCouponRules(Integer id) {
        Boolean bool = couponDao.adminUnShelveCoupons(id);
        Integer integer = couponRuleDao.adminUnShelveCouponRules(id);
         return calBool(bool, integer);
    }
    public Boolean calBool(Boolean bool, Integer integer) {
        return bool&&(integer.equals(1));
    }


}
