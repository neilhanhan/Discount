package com.xmu.discount.controller;

import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.service.PresaleRuleService;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liuwenhan & Zhang Bingyuan
 */
@RestController
@RequestMapping("/discountService")
public class PresaleRuleController {

    @Autowired
    PresaleRuleService presaleRuleService;

    /**
     * 内部接口！！！
     * @param goodsId
     * @return
     */
    @GetMapping("/presaleRule")
    public PresaleRule getPresaleRuleByGoodsId(@RequestParam Integer goodsId){
        return presaleRuleService.getPresaleRuleByGoodsId(goodsId);
    }

    /**
     * 1下架预售规则
     *
     * @param id 预售规则ID
     * @return Object
     */
    @PostMapping("/presaleRules/{id}/invalid")
    public Object downPresaleRuleById(@PathVariable Integer id){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else {
            return presaleRuleService.downPresaleRuleById(id);
        }
    }

    /**
     * 2管理员查看预售商品列表
     *
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    @GetMapping("/admin/presaleGoods")
    public Object adminGetPresaleGoods(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit){
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        return presaleRuleService.adminGetPresaleRule(page, limit);
    }

    /**
     * 3删除预售信息
     *
     * @param id 预售规则ID
     * @return Object
     */
    @DeleteMapping("/presaleRules/{id}")
    public Object deletePresaleRule(@PathVariable Integer id){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else {
            return presaleRuleService.deletePresaleRule(id);
        }
    }

    /**
     * 4用户查看预售信息详情
     *
     * @param id 预售规则ID
     * @return Object
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else {
            return presaleRuleService.getPresaleRuleById(id);
        }
    }

    /**
     * 5用户查看预售商品列表（已上架）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    @GetMapping("/presaleGoods")
    public Object customerGetPresaleGoods(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit){
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }else {
            return presaleRuleService.customerGetPresaleRule(page, limit);
        }
    }

    /**
     * 管理员根据条件查询预售信息
     *
     * @param goodsId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/presaleRules")
    public Object getPresaleRuleVoByInf(@RequestParam("goodsId") Integer goodsId,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit){
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<PresaleRuleVo> presaleRuleVos = presaleRuleService.findPresaleRuleVosByGoodsId(goodsId, page, limit);
        return ResponseUtil.ok(presaleRuleVos);
    }

    /**
     * 发布预售信息
     *
     * @param presaleRule
     * @return
     */
    @PostMapping("/presaleRules")
    public Object addPresaleRule(@RequestBody PresaleRule presaleRule) {
        PresaleRule presaleRule1 = presaleRuleService.addPresaleRule(presaleRule);
        if (presaleRule1.equals(null)) {
            ResponseUtil.presaleInsertFail();
        }
        return ResponseUtil.ok(presaleRule1);
    }

    /**
     * 修改预售信息
     *
     * @param id
     * @param presaleRule
     * @return
     */
    @PutMapping("/presaleRules/{id}")
    public Object updatePresaleRuleById(@PathVariable Integer id, @RequestBody PresaleRule presaleRule) {

        PresaleRule presaleRule1 = presaleRuleService.updatePresaleRuleById(id, presaleRule);
        if (presaleRule1.equals(null)) {
            ResponseUtil.presaleUpdateFail();
        }
        return ResponseUtil.ok(presaleRule1);
    }

    /**
     * 查看预售信息详情
     *
     * @param id
     * @return
     */
    @GetMapping("/admin/presaleRules/{id}")
    public Object getPresaleRuleVoById(@PathVariable Integer id) {
        PresaleRuleVo presaleRuleVoById = presaleRuleService.findPresaleRuleVoById(id);
        if (presaleRuleVoById.equals(null)) {
            ResponseUtil.presaleRuleUnknown();
        }
        return ResponseUtil.ok(presaleRuleVoById);
    }
}
