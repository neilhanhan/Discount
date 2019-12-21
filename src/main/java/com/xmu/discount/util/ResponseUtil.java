package com.xmu.discount.util;

import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应操作结果
 * <pre>
 *  {
 *      errno： 错误码，
 *      errmsg：错误消息，
 *      data：  响应数据
 *  }
 * </pre>
 *
 * <p>
 * 错误码：
 * <ul>
 * <li> 0，成功；
 * <li> 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
 * <ul>
 * <li> 401，参数错误，即前端没有传递后端需要的参数；
 * <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。
 * </ul>
 * <li> 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码：
 * <ul>
 * <li> 501，验证失败，即后端要求用户登录；
 * <li> 502，系统内部错误，即没有合适命名的后端内部错误；
 * <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
 * <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
 * <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 * </ul>
 * <li> 6xx，小商城后端业务错误码，
 * 具体见litemall-admin-api模块的AdminResponseCode。
 * <li> 7xx，管理后台后端业务错误码，
 * 具体见litemall-wx-api模块的WxResponseCode。
 * </ul>
 * @author Liuwenhan
 */
public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object okList(List list) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list);

        if (list instanceof Page) {
            Page page = (Page) list;
            data.put("total", page.getTotal());
            data.put("page", page.getPageNum());
            data.put("limit", page.getPageSize());
            data.put("pages", page.getPages());
        } else {
            data.put("total", list.size());
            data.put("page", 1);
            data.put("limit", list.size());
            data.put("pages", 1);
        }

        return ok(data);
    }

    public static Object okList(List list, List pagedList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list);

        if (pagedList instanceof Page) {
            Page page = (Page) pagedList;
            data.put("total", page.getTotal());
            data.put("page", page.getPageNum());
            data.put("limit", page.getPageSize());
            data.put("pages", page.getPages());
        } else {
            data.put("total", pagedList.size());
            data.put("page", 1);
            data.put("limit", pagedList.size());
            data.put("pages", 1);
        }
        return ok(data);
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", -1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object invaildParameter(){
        return fail(580,"参数不合法");
    }

    public static Object grouponRuleUnknown() {
        return fail(720, "该团购规则是无效团购规则（不在数据库里的或者逻辑删除）");
    }

    public static Object grouponUpdateFail() {
        return fail(721, "团购规则修改失败");
    }

    public static Object grouponInsertFail() {
        return fail(722, "团购规则添加失败");
    }

    public static Object grouponDeleteFail() {
        return fail(723, "团购规则删除失败");
    }

    public static Object joinGrouponFail() {
        return fail(724, "无法参与团购(未开始或下架）");
    }

    public static Object presaleRuleUnknown() {
        return fail(730, "无效预售规则（不存在或失效）");
    }

    public static Object presaleUpdateFail() {
        return fail(731, "预售规则修改失败");
    }

    public static Object presaleInsertFail() {
        return fail(732, "团购规则添加失败");
    }

    public static Object presaleDeleteFail() {
        return fail(733, "预售规则删除失败");
    }

    public static Object joinPresaleFail() {
        return fail(734, "预售商品无法购买");
    }

    public static Object payoutPresaleFail() {
        return fail(735, "支付预售尾款失败");
    }

    public static Object invaildCouponRuleFail() {
        return fail(710, "该优惠券规则是无效优惠券规则（不在数据库里的或者逻辑删除）");
    }

    public static Object couponRuleUpdateFail() {
        return fail(711, "优惠券规则修改失败");
    }

    public static Object couponRuleAddFail() {
        return fail(712, "优惠券规则添加失败");
    }

    public static Object couponRuleDeleteFail() {
        return fail(713, "优惠券规则删除失败");
    }

    public static Object getCouponFail() {
        return fail(714, "领取优惠券失败");
    }

    public static Object invaildCouponFail() {
        return fail(715, "该优惠券是无效优惠券规则（不在数据库里的或者逻辑删除）");
    }

    public static Object couponUseFail() {
        return fail(716, "该优惠券无法使用（存在数据库中，但是“不在使用时间内”等情形）");
    }

    public static Object checkCouponRuleFail(){
        return  fail(717,"查看优惠券规则失败");
    }
}