package com.xmu.discount.dao;

import com.xmu.discount.domain.GoodsPo;
import com.xmu.discount.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/18 20:52
 */
@Repository
public class GoodsDao {

    @Autowired
    GoodsMapper goodsMapper;
    public GoodsPo findGoodsPoById(Integer goodsId) {
        GoodsPo goodsPoById = goodsMapper.findGoodsPoById(goodsId);
        return goodsPoById;
    }

}
