package com.xmu.discount.controller;

import com.xmu.discount.domain.PresaleRule;
import com.xmu.discount.service.PresaleRuleService;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.PresaleRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liu Wenhan & Zhang Bingyuan
 */
@RestController
@RequestMapping(value = "/discountService", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
public class PresaleRuleController {

    @Autowired
    PresaleRuleService presaleRuleService;


    /**
     * 下架预售规则
     *
     * @param id 预售规则ID
     * @return 无
     */
    @PostMapping("/presaleRules/{id}/invalid")
    public Object downPresaleRuleById(@PathVariable Integer id){
        if(presaleRuleService.downPresaleRuleById(id)){
            return ResponseUtil.ok();
        }else {
            /**
             * 下架失败的错误码缺失
             */
            return ResponseUtil.fail();
        }
    }

    /**
     * 管理员查看预售商品列表
     *
     * @param page 页数
     * @param limit 分页大小
     * @return List<PresaleRuleVo>
     */
    @GetMapping("/admin/presaleGoods")
    public Object adminGetPresaleGoods(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit){
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<PresaleRuleVo> presaleRuleVoList = presaleRuleService.adminGetPresaleRule(page, limit);
        return ResponseUtil.okList(presaleRuleVoList);
    }

    /**
     * 删除预售信息
     *
     * @param id 预售规则ID
     * @return 无
     */
    @DeleteMapping("/presaleRules/{id}")
    public Object deletePresaleRule(@PathVariable Integer id){
        if(id==null){
            return ResponseUtil.invaildParameter();
        }
        else if(presaleRuleService.deletePresaleRule(id)){
            return ResponseUtil.ok();
        }
        else{
            return ResponseUtil.presaleDeleteFail();
        }
    }

    /**
     * 用户查看预售信息详情
     *
     * @param id 预售规则ID
     * @return PresaleRuleVo
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id){
        if(id==null){
            return ResponseUtil.invaildParameter();
        }
        PresaleRuleVo presaleRuleVo = presaleRuleService.getPresaleRuleById(id);
        return ResponseUtil.ok(presaleRuleVo);
    }

    /**
     * 用户查看预售商品列表（已上架）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return List<PresaleRuleVo>
     */
    @GetMapping("/presaleGoods")
    public Object customerGetPresaleGoods(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit){
        if(page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        List<PresaleRuleVo> presaleRuleVoList = presaleRuleService.customerGetPresaleRule(page, limit);
        return ResponseUtil.okList(presaleRuleVoList);
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
