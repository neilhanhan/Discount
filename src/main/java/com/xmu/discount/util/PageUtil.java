package com.xmu.discount.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zhang BingYuan
 * @Date 2019/12/21 17:06
 */
public class PageUtil {
    /**
     * 判断page，limit参数是否正确不在这里判断
     * @param page
     * @param limit
     * @param objects
     * @return
     */
    public static List<Object> pageStart(Integer page, Integer limit, List<Object> objects) {
        /**
         * 从数据库查找的对象list为空
         */
        if (objects.size()==0) {
            return new ArrayList<Object>();
        }
        if (page>getMaxPageNum(objects,limit)) {
            return new ArrayList<Object>();
        }else if (page<getMaxPageNum(objects,limit)) {
            List<Object> objects1 = objects.subList((page - 1) * limit, page * limit);
            return objects1;
        }else if (page==getMaxPageNum(objects,limit)) {
            List<Object> objects1 = objects.subList((page - 1) * limit, objects.size());
            return objects1;
        }
        /**
         * 理论上这个return不会执行
         */
        return null;
    }

    public static int getMaxPageNum(List<Object> objects, Integer limit) {
        if (objects.size()%limit!=0) {
            return objects.size()/limit+1;
        }
        else {
            return objects.size()/limit;
        }
    }
}
