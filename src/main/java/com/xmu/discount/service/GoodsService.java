package com.xmu.discount.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Liuwenhan
 */
@Component
@FeignClient(value = "goodsInfoService")
public interface GoodsService {

    /**
     * 通过Id获取goods
     * @param id
     * @return
     */
    @GetMapping("/goods/{id}")
    public Object getGoodsById(@PathVariable("id") Integer id);
}
