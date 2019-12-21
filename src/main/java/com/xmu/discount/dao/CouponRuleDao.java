package com.xmu.discount.dao;

import com.github.pagehelper.PageHelper;
import com.xmu.discount.discountdo.CouponRuleDo;
import com.xmu.discount.domain.CouponPo;
import com.xmu.discount.domain.CouponRule;
import com.xmu.discount.domain.CouponRulePo;
import com.xmu.discount.mapper.CouponRuleMapper;
import com.xmu.discount.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 15:20
 */
@Repository
public class CouponRuleDao {
    @Autowired
    CouponRuleMapper couponRuleMapper;

    /**
     * 增加一个CouponRule
     *
     * @param couponRulePo
     * @return
     */
    public CouponRulePo addCouponRulePo(CouponRulePo couponRulePo) {
        LocalDateTime localDateTime = LocalDateTime.now();
        couponRulePo.setGmtModified(localDateTime);
        couponRulePo.setGmtCreate(localDateTime);
        couponRuleMapper.addCouponRulePo(couponRulePo);
        if (couponRulePo.getId()>0) {
            couponRulePo.setBeDeleted(false);
            return couponRulePo;
        }
        return null;
    }

    /**
     * 根据id删除CouponRule
     *
     * @param id
     * @return
     */
    public Integer deleteCouponRulePoById(Integer id) {
        Integer integer=0;
        try {
            integer = couponRuleMapper.deleteCouponRulePoById(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return integer;
    }

    /**
     * 通过id更新CouponRule(不更新数量)
     *
     * @param id
     * @param couponRulePo
     * @return
     */
    public CouponRulePo updateCouponRulePo(Integer id, CouponRulePo couponRulePo) {
        couponRulePo.setId(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        couponRulePo.setGmtModified(localDateTime);
        try {
            couponRuleMapper.updateCouponRulePo(couponRulePo);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return couponRulePo;

    }

    /**
     * 通过id更新CouponRule(更新数量)
     *
     * @param id
     * @param couponRulePo
     * @return
     */
    public CouponRulePo updateCouponRulePoOnlyTwo(Integer id, CouponRulePo couponRulePo) {
        couponRulePo.setId(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        couponRulePo.setGmtModified(localDateTime);
        try {
            couponRuleMapper.updateCouponRulePoOnlyTwo(couponRulePo);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return couponRulePo;
    }

    /**
     * 通过id查找CouponRule
     *
     * @param id
     * @return
     */
    public CouponRulePo findCouponRulePoById(Integer id) {
        CouponRulePo couponRulePoById = couponRuleMapper.findCouponRulePoById(id);
        return couponRulePoById;
    }

    /**
     * 管理员查看部分规则列表
     *
     * @return
     */
    public List<CouponRulePo> adminGetAllCouponRulePos(Integer page, Integer limit) {
        List<CouponRulePo> couponRulePos = couponRuleMapper.adminGetAllCouponRulePos();
        if (couponRulePos.size()==0) {
            return couponRulePos;
        }
        List<Object> couponRulePoObjects = new ArrayList<>(couponRulePos.size());
        for (CouponRulePo couponRulePo : couponRulePos) {
            couponRulePoObjects.add(couponRulePo);
        }
        couponRulePoObjects=PageUtil.pageStart(page, limit,couponRulePoObjects);
        couponRulePos.clear();
        if (couponRulePoObjects.size()==0) {
            return couponRulePos;
        }
        for (Object couponRulePoObject : couponRulePoObjects) {
            couponRulePos.add((CouponRulePo)couponRulePoObject);
        }
        return couponRulePos;
    }


    /**
     * 用户查看部分规则列表
     *
     * @return
     */
    public List<CouponRulePo> userGetAllCouponRulePos(Integer page, Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<CouponRulePo> couponRulePos = couponRuleMapper.userGetAllCouponRulePos();
        if(couponRulePos.size()==0) {
            return couponRulePos;
        }
        List<Object> couponRulePoObjects = new ArrayList<>(couponRulePos.size());
        for (CouponRulePo couponRulePo : couponRulePos) {
            couponRulePoObjects.add(couponRulePo);
        }
        couponRulePoObjects=PageUtil.pageStart(page, limit,couponRulePoObjects);
        couponRulePos.clear();
        if (couponRulePoObjects.size()==0) {
            return couponRulePos;
        }
        for (Object couponRulePoObject : couponRulePoObjects) {
            couponRulePos.add((CouponRulePo)couponRulePoObject);
        }

        Iterator<CouponRulePo> iterator = couponRulePos.iterator();
        while(iterator.hasNext()){
            CouponRulePo couponRulePo = iterator.next();
            if (couponRulePo.getEndTime().isBefore(localDateTime) || couponRulePo.getBeginTime().isAfter(localDateTime)) {
                iterator.remove();
            }
        }
        return couponRulePos;
    }

    /**
     * 获取所有的CouponRule的id和goodsListn
     *
     * @return
     */
    public List<CouponRuleDo> getAllCouponRuleDos() {
        List<CouponRuleDo> allCouponRuleDos = couponRuleMapper.getAllCouponRuleDos();
        return allCouponRuleDos;
    }

    public Integer adminUnShelveCouponRules(Integer id) {
        Integer integer = couponRuleMapper.adminUnShelveCouponRules(id);
        return integer;
    }

}
