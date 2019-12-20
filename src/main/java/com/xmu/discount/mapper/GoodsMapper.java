package com.xmu.discount.mapper;

import com.xmu.discount.domain.GoodsPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/18 20:53
 */
@Mapper
@Repository
public interface GoodsMapper {
    /**
     * 根据Id获取GoodsPo
     *
     * @param goodsId
     * @return
     */
    public GoodsPo findGoodsPoById(Integer goodsId);
}
