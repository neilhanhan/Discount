package com.xmu.discount.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 将父类对象的属性值赋给子类对象
 * @author BingyuanZhang
 */
public class FatherChildUtil {
    public static <T>void fatherToChild(T father,T child) throws Exception {
        if (child.getClass().getSuperclass()!=father.getClass()){
            throw new Exception("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Class<?> childClass = child.getClass();

        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field=declaredFields[i];

            Method getMethod = fatherClass.getDeclaredMethod("get"+upperHeadChar(field.getName()));
            Method setMethod = childClass.getMethod("set"+upperHeadChar(field.getName()),field.getType());
            Object obj = getMethod.invoke(father);
            setMethod.invoke(child,obj);
        }



    }
    public static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }

}
