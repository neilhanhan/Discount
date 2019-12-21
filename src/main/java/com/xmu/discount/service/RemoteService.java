package com.xmu.discount.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "goodsInfoService")
public interface RemoteService {

    @GetMapping("goods/{id}")
    public Object getGoodsById(@PathVariable("id") Integer id);
}
