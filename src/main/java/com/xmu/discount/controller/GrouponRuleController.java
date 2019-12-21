package com.xmu.discount.controller;

import com.xmu.discount.domain.GrouponRule;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.service.GrouponRuleService;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liuwenhan
 */
@Component
@EnableScheduling
@EnableAsync
@RestController
@RequestMapping("/discountService")
public class GrouponRuleController {

    @Autowired
     private GrouponRuleService grouponRuleService;

    /**
     *
     * 定时任务：每天晚上12点检查前一天完成的团购活动并退款
     *
     */
    @Async
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void checkFinishedGrouponRule(){
        grouponRuleService.checkFinishedGrouponRule();
    }

    /**
     *
     * 下架团购活动
     *
     * @param id 团购规则ID
     * @return Object
     */
    @PostMapping("/grouponRules/{id}/invalid")
    public Object downGrouponRuleById(@PathVariable Integer id){
        if(id<0) {
            return ResponseUtil.invaildParameter();
        } else{
            return grouponRuleService.downGrouponRuleById(id);
        }
    }

    /**
     *
     *
     * 通过商品ID获取团购规则列表
     *
     * @param goodsId 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    @GetMapping("/grouponRules")
    public Object getGrouponRuleByGoodsId(@RequestParam Integer goodsId,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit)
    {
        if(goodsId<0||page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        else{
            return grouponRuleService.getGrouponRuleByGoodsId(goodsId, page, limit);
        }
    }

    /**
     *
     *
     * 添加团购规则
     *
     * @param grouponRulePo 团购规则ID
     * @return Object
     */
    @PostMapping("/grouponRules")
    public Object addGrouponRule(@RequestBody GrouponRulePo grouponRulePo){
        if(grouponRulePo==null){
            return ResponseUtil.invaildParameter();
        }else {
            return grouponRuleService.addGrouponRule(grouponRulePo);
        }
    }

    /**
     *
     *
     * 用户通过团购规则ID获取团购活动
     *
     * @param id 团购规则ID
     * @return Object
     */
    @GetMapping("/grouponRules/{id}")
    public Object getGrouponRuleById(@PathVariable Integer id){
        if (id<0) {
            return ResponseUtil.invaildParameter();
        }
        return grouponRuleService.getGrouponRuleById(id);
    }

    /**
     *
     *
     * 管理员通过团购规则ID获取团购规则详情
     *
     * @param id 团购规则ID
     * @return Object
     */
    @GetMapping("/admin/grouponRules/{id}")
    public Object adminGetGrouponRuleById(@PathVariable Integer id){
        if (id == null) {
            return ResponseUtil.invaildParameter();
        }
        return grouponRuleService.adminGetGrouponRuleById(id);
    }

    /**
     *
     *
     * 更新团购规则
     *
     * @param id 团购规则ID
     * @param grouponRulePo GrouponRulePo
     * @return Object
     */
    @PutMapping("/grouponRules/{id}")
    public Object updateGrouponRule(@PathVariable Integer id,
                                    @RequestBody GrouponRulePo grouponRulePo){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else{
            return grouponRuleService.updateGrouponRule(id,grouponRulePo);
        }
    }

    /**
     *
     *
     * 删除团购规则
     *
     * @param id 团购规则ID
     * @return Object
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGrouponRuleById(@PathVariable Integer id){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else {
            return grouponRuleService.deleteGrouponRule(id);
        }
    }

    /**
     *
     *
     * 普通用户获取团购规则列表（未删除且上架中）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    @GetMapping("/grouponGoods")
    public Object customerGetGrouponRules(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit){
        return grouponRuleService.customerGetGrouponRule(page, limit);
    }

    /**
     *
     *
     * 管理员获取团购规则列表（全部）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return Object
     */
    @GetMapping("/admin/grouponGoods")
    public Object adminGetGrouponRules(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit){
        return grouponRuleService.adminGetGrouponRule(page,limit);
    }

    /**
     * 测试成功
     *
     * 给Goods模块调用的接口
     *
     * @param id 团购规则ID
     * @return Object
     */
    @GetMapping("/grouponRule/{id}")
    public GrouponRule getGrouponRuleOnshelve(@PathVariable Integer id){
        return grouponRuleService.getGrouponRuleOnshelve(id);
    }
}