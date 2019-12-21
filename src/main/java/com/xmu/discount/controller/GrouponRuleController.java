package com.xmu.discount.controller;

import com.xmu.discount.domain.GrouponRule;
import com.xmu.discount.domain.GrouponRulePo;
import com.xmu.discount.service.GrouponRuleService;
import com.xmu.discount.util.ResponseUtil;
import com.xmu.discount.vo.GrouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Liuwenhan
 */

@RestController
@RequestMapping("/discountService")
public class GrouponRuleController {

    @Autowired
     private GrouponRuleService grouponRuleService;

    /**
     * 未测试
     *
     * 下架团购活动
     *
     * @param id 团购规则ID
     * @return 无
     */
    @PostMapping("/grouponRules/{id}/invalid")
    public Object downGrouponRuleById(@PathVariable Integer id){
        if(id<0){
            return ResponseUtil.invaildParameter();
        }else if(grouponRuleService.downGrouponRuleById(id)){
            return ResponseUtil.ok();
        }else {
            /**
             * 是否应该有状态码是下架操作失败的？
             */
            return ResponseUtil.fail();

        }
    }

    /**
     * 测试成功
     *
     * 通过商品ID获取团购规则列表
     *
     * @param goodsId 商品ID
     * @param page 页数
     * @param limit 分页大小
     * @return 团购规则列表
     */
    @GetMapping("/grouponRules")
    public Object getGrouponRuleByGoodsId(@RequestParam Integer goodsId,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit)
    {
        if(goodsId<0||page<=0||limit<0){
            return ResponseUtil.invaildParameter();
        }
        else {
            List<GrouponRuleVo> grouponRuleVoList = grouponRuleService.getGrouponRuleByGoodsId(goodsId, page, limit);
            if (grouponRuleVoList == null) {
                return ResponseUtil.grouponRuleUnknown();
            }
            return ResponseUtil.okList(grouponRuleVoList);
        }
    }

    /**
     * 测试成功
     *
     * 添加团购规则
     *
     * @param grouponRulePo 团购规则ID
     * @return grouponRulePo
     */
    @PostMapping("/grouponRules")
    public Object addGrouponRule(@RequestBody GrouponRulePo grouponRulePo){
        grouponRulePo.setGmtCreate(LocalDateTime.now());
        grouponRulePo.setGmtModified(LocalDateTime.now());
        grouponRulePo.setBeDeleted(false);
        grouponRulePo.setStatusCode(true);
        if(grouponRuleService.addGrouponRule(grouponRulePo)){
            return ResponseUtil.ok(grouponRulePo);
        }
        else{
            return ResponseUtil.grouponInsertFail();
        }
    }

    /**
     * 测试成功
     *
     * 用户通过团购规则ID获取团购活动
     *
     * @param id 团购规则ID
     * @return GrouponRuleVo
     */
    @GetMapping("/grouponRules/{id}")
    public Object getGrouponRuleById(@PathVariable Integer id){
        if (id == null) {
            return ResponseUtil.invaildParameter();
        }
        GrouponRuleVo grouponRuleVo = grouponRuleService.getGrouponRuleById(id);
        return ResponseUtil.ok(grouponRuleVo);
    }

    /**
     * 测试成功
     *
     * 管理员通过团购规则ID获取团购规则详情
     *
     * @param id 团购规则ID
     * @return grouponRuleVo
     */
    @GetMapping("/admin/grouponRules/{id}")
    public Object adminGetGrouponRuleById(@PathVariable Integer id){
        if (id == null) {
            return ResponseUtil.invaildParameter();
        }
        GrouponRuleVo grouponRuleVo = grouponRuleService.adminGetGrouponRuleById(id);
        return ResponseUtil.ok(grouponRuleVo);
    }

    /**
     * 测试成功
     *
     * 更新团购规则
     *
     * @param id 团购规则ID
     * @param grouponRulePo GrouponRulePo
     * @return GrouponRulePo
     */
    @PutMapping("/grouponRules/{id}")
    public Object updateGrouponRule(@PathVariable Integer id,
                                    @RequestBody GrouponRulePo grouponRulePo){
        if(id==null){
            return ResponseUtil.invaildParameter();
        }
        grouponRulePo.setGmtModified(LocalDateTime.now());
        grouponRulePo.setId(id);
        if(grouponRuleService.updateGrouponRule(grouponRulePo)){
            return ResponseUtil.ok(grouponRulePo);
        }
        else{
            return ResponseUtil.grouponUpdateFail();
        }
    }

    /**
     * 测试成功
     *
     * 删除团购规则
     *
     * @param id 团购规则ID
     * @return 无
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGrouponRuleById(@PathVariable Integer id){
        if(id == null){
            return ResponseUtil.invaildParameter();
        }
        else if(grouponRuleService.deleteGrouponRule(id)) {
            return ResponseUtil.ok();
        }
        else {
            return ResponseUtil.grouponDeleteFail();
        }
    }

    /**
     * 测试成功
     *
     * 普通用户获取团购规则列表（未删除且上架中）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return List<GrouponRuleVo>
     */
    @GetMapping("/grouponGoods")
    public Object customerGetGrouponRules(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit){
        List<GrouponRuleVo> grouponRuleVoList = grouponRuleService.customerGetGrouponRule(page, limit);
        return ResponseUtil.okList(grouponRuleVoList);
    }

    /**
     * 测试成功
     *
     * 管理员获取团购规则列表（全部）
     *
     * @param page 页数
     * @param limit 分页大小
     * @return List<GrouponRuleVo>
     */
    @GetMapping("/admin/grouponGoods")
    public Object adminGetGrouponRules(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit){
        List<GrouponRuleVo> grouponRuleVoList = grouponRuleService.adminGetGrouponRule(page,limit);
        return ResponseUtil.okList(grouponRuleVoList);
    }

    /**
     * 给Goods模块调用的接口
     * @param goodsId
     * @return GrouponRule
     */
    @GetMapping("/grouponRule")
    public GrouponRule getGrouponRuleOnshelve(@RequestParam("id") Integer goodsId){
        return grouponRuleService.getGrouponRuleOnshelve(goodsId);
    }
}