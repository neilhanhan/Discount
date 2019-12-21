package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.xmu.discount.dao.GoodsDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.Goods;
import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.service.OrderService;
import com.xmu.discount.service.PresaleRuleService;
import com.xmu.discount.service.RemoteService;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuwenhan
 */
@Service
public class PresaleRuleServiceImpl implements PresaleRuleService {
    @Autowired
    PresaleRuleDao presaleRuleDao;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    RemoteService remoteService;

    @Override
    public List<PresaleRuleVo> adminGetPresaleRule(Integer page, Integer limit) {
        List<PresaleRule> presaleRuleList = presaleRuleDao.adminGetPresaleRule(page, limit);
        Page<PresaleRuleVo> presaleRuleVoList = new Page<PresaleRuleVo>();

        for (PresaleRule presaleRule : presaleRuleList) {
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(remoteService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null) {
                continue;
            }
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVoList.add(presaleRuleVo);
        }
        return presaleRuleVoList;
    }

    @Override
    public List<PresaleRuleVo> customerGetPresaleRule(Integer page, Integer limit) {
        List<PresaleRule> presaleRuleList = presaleRuleDao.customerGetPresaleRule(page, limit);
        Page<PresaleRuleVo> presaleRuleVoList = new Page<PresaleRuleVo>();

        for (PresaleRule presaleRule : presaleRuleList) {
            Integer goodsId = presaleRule.getGoodsId();
            String str = JacksonUtil.toJson(remoteService.getGoodsById(goodsId));
            Map map = (Map) JSON.parse(str);
            String data = map.get("data").toString();
            GoodsPo goodsPo=new GoodsPo();
            try{
                goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (goodsPo == null) {
                continue;
            }
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVoList.add(presaleRuleVo);
        }
        return presaleRuleVoList;
    }

    @Override
    public boolean deletePresaleRule(Integer id) {
        return presaleRuleDao.deletePresaleRule(id);
    }

    @Override
    public PresaleRuleVo getPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleDao.getPresaleRuleById(id);
        PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
        Integer goodsId = presaleRule.getGoodsId();
        String str = JacksonUtil.toJson(remoteService.getGoodsById(goodsId));
        Map map = (Map) JSON.parse(str);
        String data = map.get("data").toString();
        GoodsPo goodsPo=new GoodsPo();
        try{
            goodsPo=(GoodsPo) new ObjectMapper().readValue(data, Goods.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        presaleRuleVo.setGoodsPo(goodsPo);
        presaleRuleVo.setPresaleRule(presaleRule);
        return presaleRuleVo;
    }


    /**
     * 数据库中增加一个PresaleRule
     *
     * @param presaleRule 预售规则
     * @return PresaleRule 预售规则
     */
    @Override
    public PresaleRule addPresaleRule(PresaleRule presaleRule) {
        PresaleRule presaleRule1 = presaleRuleDao.addPresaleRule(presaleRule);
        return presaleRule1;
    }

    /**
     * 通过goodsId获取presaleRules
     *
     * @param goodsId 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return List<PresaleRuleVo>
     */
    @Override
    public List<PresaleRuleVo> findPresaleRuleVosByGoodsId(Integer goodsId, Integer page, Integer limit) {
        /**
         * 获得List<PresaleRule>和GoodsPo对象
         */
        List<PresaleRule> presaleRules = presaleRuleDao.findPresaleRulesByGoodsId(goodsId, page, limit);
        GoodsPo goodsPo = goodsDao.findGoodsPoById(goodsId);

        List<PresaleRuleVo> presaleRuleVos = new ArrayList<>(presaleRules.size());

        for (PresaleRule presaleRule : presaleRules) {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(goodsPo);
            presaleRuleVos.add(presaleRuleVo);
        }
        return presaleRuleVos;
    }

    /**
     * 修改预售信息
     *
     * @param id 预售规则ID
     * @param presaleRule
     * @return
     */
    @Override
    public PresaleRule updatePresaleRuleById(Integer id, PresaleRule presaleRule) {
        PresaleRule presaleRule1 = presaleRuleDao.updatePresaleRuleById(id, presaleRule);
        return presaleRule1;
    }

    /**
     * 通过id获得PresaleRuleVo
     * @param id 预售规则ID
     * @return PresaleVo
     */
    @Override
    public PresaleRuleVo findPresaleRuleVoById(Integer id) {
        PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
        PresaleRule presaleRuleById = presaleRuleDao.findPresaleRuleById(id);
        GoodsPo goodsPoById = goodsDao.findGoodsPoById(presaleRuleById.getGoodsId());
        if (presaleRuleById.equals(null)||goodsPoById.equals(null)) {
            return null;
        }
        presaleRuleVo.setGoodsPo(goodsPoById);
        presaleRuleVo.setPresaleRule(presaleRuleById);
        return presaleRuleVo;
    }

    /**
     * 下架预售规则
     * @param id 预售规则ID
     * @return Boolean
     */
    @Override
    public Boolean downPresaleRuleById(Integer id) {
        PresaleRule presaleRule = presaleRuleDao.getPresaleRuleById(id);
        orderService.refundOfPresaleRule(presaleRule);
        return presaleRuleDao.downPresaleRuleById(id);
    }

}
