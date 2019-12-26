package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.discountdo.CouponRuleDo;
import com.xmu.discount.domain.*;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.util.FatherChildUtil;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.JsonObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/8 12:05
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    public CouponDao couponDao;
    @Autowired
    public CouponRuleDao couponRuleDao;

    /**
     * 用id找到Coupon对象
     *
     * @param id
     * @return
     */
    @Override
    public CouponPo findCouponById(Integer id) {
        CouponPo couponPo = couponDao.findCouponById(id);
        return couponPo;
    }

    /**
     * 增加Coupon对象
     *
     * @param couponPo
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public CouponPo addCouponPo(CouponPo couponPo) {
        CouponRulePo couponRulePo = new CouponRulePo();
        couponRulePo = couponRuleDao.findCouponRulePoById(couponPo.getCouponRuleId());
        if (couponRulePo.getCollectedNum() < couponRulePo.getTotal()) {
            couponRulePo.setCollectedNum(couponRulePo.getCollectedNum() + 1);
            couponRuleDao.updateCouponRulePoOnlyTwo(couponRulePo.getId(), couponRulePo);
            CouponPo couponPo1 = couponDao.addCouponPo(couponPo);
            return couponPo1;
        } else {
            return null;
        }
    }

    /**
     * 用id更新Coupon对象
     *
     * @param id
     * @param coupon
     * @return
     */
    @Override
    public boolean updateCouponById(Integer id, Coupon coupon) {
        boolean bool = couponDao.updateCouponById(id, coupon);
        return bool;
    }

    /**
     * 用id删除Coupon对象
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteCouponById(Integer id) {
        boolean bool = couponDao.deleteCouponById(id);
        return bool;
    }


    /**
     * getAllStatusCoupons调用，获得CouponRulePos通过id
     */
    private List<CouponRulePo> getCouponRulePosByCouponPos(List<CouponPo> couponPos) {
        List<Integer> couponRuleIds = new ArrayList<>();
        for (CouponPo couponPo : couponPos) {
            couponRuleIds.add(couponPo.getCouponRuleId());
        }
        HashSet<Integer> couponRuleIdsSet = new HashSet<>(couponRuleIds);
        couponRuleIds.clear();
        couponRuleIds.addAll(couponRuleIdsSet);
        /**
         * 获得这些couponPo的couponRuleId,然后查到couponRulePo
         */
        List<CouponRulePo> couponRulePos = new ArrayList<>();
        for (Integer couponRuleId : couponRuleIds) {
            CouponRulePo couponRulePoById = couponRuleDao.findCouponRulePoById(couponRuleId);
            couponRulePos.add(couponRulePoById);
        }
        return couponRulePos;
    }

    /**
     * 获取所有特定状态优惠券
     *
     * @return
     */
    @Override
    public List<Coupon> getAllStatusCoupons(Integer page, Integer limit, Integer showType) throws Exception {
        /**
         * 获得特定类型的couponPo
         */
        List<CouponPo> couponPos = couponDao.getAllStatusCouponPos(page, limit, showType);
        if (couponPos.size()==0) {
            return new ArrayList<Coupon>();
        }
        List<CouponRulePo> couponRulePos = getCouponRulePosByCouponPos(couponPos);
        List<CouponRule> couponRules = new ArrayList<>();
        for (CouponRulePo couponRulePo : couponRulePos) {
            CouponRule couponRule = new CouponRule();
            FatherChildUtil.fatherToChild(couponRulePo, couponRule);
            couponRule.setCouponStrategy(JsonObjectUtil.getCouponStrategy(couponRule.getStrategy()));
            couponRules.add(couponRule);
        }


        List<Coupon> coupons = new ArrayList<>();
        for (CouponPo couponPo : couponPos) {
            Coupon coupon = new Coupon();
            FatherChildUtil.fatherToChild(couponPo, coupon);

            for (CouponRule couponRule : couponRules) {
                if (coupon.getCouponRuleId().equals(couponRule.getId())) {
                    coupon.setCouponRule(couponRule);
                    break;
                }
            }
            coupons.add(coupon);
        }
        return coupons;
    }

    /**
     * 通过CouponRuleDo获取此CouponRule的所有goodlistId，然后装入map
     *
     * @param allCouponRuleDos
     * @return
     */
    private HashMap<Integer, List<Integer>> getCouponRuleGoodIds(List<CouponRuleDo> allCouponRuleDos) {
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>(allCouponRuleDos.size());

        for (CouponRuleDo allCouponRuleDo : allCouponRuleDos) {
            String jsonString1 = allCouponRuleDo.getGoodsList1();
            String jsonString2=allCouponRuleDo.getGoodsList2();
            jsonString1 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString1);
            jsonString2 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString2);
            System.out.println("jsonString1="+jsonString1);
            System.out.println("jsonString2="+jsonString2);

            List<Integer> goodsId1=new ArrayList<>();
            List<Integer> goodsId2=new ArrayList<>();
            if(jsonString1!=null) {
                goodsId1 = JacksonUtil.parseIntegerList(jsonString1, "gIDs");
                System.out.println("goodsId1参数" + goodsId1);
            }
            if(jsonString2!=null){
                goodsId2=JacksonUtil.parseIntegerList(jsonString2, "gIDs");
                System.out.println("goodsId2参数"+goodsId2);
            }

            if(goodsId2!=null) {
                for (Integer goodsId : goodsId2) {
                    goodsId1.add(goodsId);
                }
            }
            System.out.println(goodsId1);
            map.put(allCouponRuleDo.getId(), goodsId1);
        }
        return map;
    }

    /**
     * 通过couponRule的Id获取coupon（其couponRuleId等于此couponRule的Id）
     *
     * @param couponRuleIds
     * @return
     */
    private List<CouponPo> getCouponPosByCouponRuleIds(List<Integer> couponRuleIds) {
        List<CouponPo> couponPos = new ArrayList<>();
        for (Integer couponRuleId : couponRuleIds) {
            List<CouponPo> couponPoList = couponDao.getCouponPoByCouponRuleId(couponRuleId);
            couponPos.addAll(couponPoList);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Iterator<CouponPo> iterator = couponPos.iterator();
        while(iterator.hasNext()){
            CouponPo couponPo = iterator.next();
            if (couponPo.getEndTime().isBefore(localDateTime) || couponPo.getBeginTime().isAfter(localDateTime)) {
                iterator.remove();   //注意这个地方
            }
        }
        return couponPos;
    }


    public List<Integer> selectCouponRuleId(List<CouponRuleDo> allCouponRuleDos, List<CartItem> cartItemList) {
        /**
         *获得所有的good的id
         */
        List<Integer> goodIdsList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            goodIdsList.add(cartItem.getProduct().getGoodsId());
        }
        /**
         *消除重复的id
         */
        HashSet<Integer> goodIdsSet = new HashSet<>(goodIdsList);
        goodIdsList.clear();
        goodIdsList.addAll(goodIdsSet);
        /**
         *获取所有的couponRule的id和goodIdList
         */

        HashMap<Integer, List<Integer>> map = getCouponRuleGoodIds(allCouponRuleDos);
        /**
         * map中已经装有couponRule的id和对应的goodsIds
         * couponRuleIds用来存被选用的couponRule的id
         */
        List<Integer> couponRuleIds = new ArrayList<>();
        for (Integer key : map.keySet()) {
            List<Integer> goodIds = map.get(key);
            int flag = 0;
            for (Integer goodId1 : goodIds) {
                for (Integer goodId2 : goodIdsList) {
                    if (goodId1.equals(goodId2)) {
                        couponRuleIds.add(key);
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        }
        return couponRuleIds;
    }

    /**
     * 获取可用的优惠券
     *
     * @param cartItemList
     * @return
     */
    @Override
    public List<Coupon> getAvailableCoupons(List<CartItem> cartItemList) throws Exception {
        List<Coupon> coupons = new ArrayList<>();
        List<CouponRuleDo> allCouponRuleDos = couponRuleDao.getAllCouponRuleDos();
        List<Integer> couponRuleIds = selectCouponRuleId(allCouponRuleDos, cartItemList);

        /**
         * 说明没有可获得的coupon
         */
        if (couponRuleIds.size() == 0) {
            return coupons;
        }
        /**
         * 此时couponRuleIds是所要的couponRule的Id
         * 接下来通过数据库查找没有被删除的，而且couponRuleId是我们要的，的coupon
         */
        List<CouponPo> couponPos = new ArrayList<>();
        couponPos = getCouponPosByCouponRuleIds(couponRuleIds);
        List<CouponRulePo> couponRulePosByIds = new ArrayList<>();
        for (Integer couponRuleId : couponRuleIds) {
            couponRulePosByIds.add(couponRuleDao.findCouponRulePoById(couponRuleId));
        }
        /**
         * 存放id，couponRule
         */
        HashMap<Integer, CouponRule> hashMap = new HashMap<>(couponRulePosByIds.size());
        for (CouponPo couponPo : couponPos) {
            Coupon coupon = new Coupon();
            FatherChildUtil.fatherToChild(couponPo, coupon);
            for (CouponRulePo couponRulePosById : couponRulePosByIds) {
                if (couponRulePosById.getId().equals(couponPo.getCouponRuleId())) {
                    if (hashMap.containsKey(couponRulePosById.getId())) {
                        coupon.setCouponRule(hashMap.get(couponRulePosById.getId()));
                    } else {
                        CouponRule couponRule = new CouponRule();
                        FatherChildUtil.fatherToChild(couponRulePosById, couponRule);
                        couponRule.setCouponStrategy(JsonObjectUtil.getCouponStrategy(couponRule.getStrategy()));
                        hashMap.put(couponRule.getId(),couponRule);
                        coupon.setCouponRule(couponRule);
                    }
                    break;
                }
            }
            coupons.add(coupon);
        }
        return coupons;
    }

    /**
     * 计算每个OrderItem的价格
     * @param orderItems
     * @param couponId
     * @return
     */
    @Override
    public List<OrderItem> calcDiscount(List<OrderItem> orderItems, Integer couponId) {
        List<OrderItem> newItems=couponDao.calcDiscount(orderItems,couponId);
        return newItems;
    }

    /**
     * 优惠券被使用后更新
     * @param userId
     * @param couponId
     * @return
     */
    @Override
    public Integer updateUserCouponStatus(Integer userId, Integer couponId) {
        return couponDao.updateUserCouponStatus(userId, couponId);
    }




}
