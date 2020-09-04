package org.mozi.xzd.api.common.beans;

import org.springframework.cglib.beans.BeanCopier;

import java.util.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 09:59
 */
public class BeanUtils {



    /**
     * 拷贝Bean
     *
     * @param sourceObj 目标Obj
     * @param source    原Class
     * @param target    目标Class
     * @param <T>
     * @return
     */
    public static <T> T copierTargetBean(Object sourceObj, Class source, Class<T> target) {
        if (Objects.nonNull(sourceObj)) {
            BeanCopier beanCopier = BeanCopier.create(source, target, false);
            T targetObj = null;
            try {
                targetObj = target.newInstance();
            } catch (Exception e) {
                throw new ClassCastException(sourceObj.getClass().getName() + " to " +
                        targetObj.getClass().getName() + " Class convert Exception ");
            }


            beanCopier.copy(sourceObj, targetObj, null);
            return targetObj;
        }
        return null;
    }

    /**
     * 拷贝目标List
     *
     * @param sourceObjList 目标List
     * @param source        原Class
     * @param target        目标Class
     * @param <T>
     * @return
     */
    public static <T> List<T> copierTargetBeanList(List<?> sourceObjList, Class source, Class<T> target) {
        if (null != sourceObjList) {
            List<T> targetList = new ArrayList<>(sourceObjList.size());
            for (Object sourceObj : sourceObjList) {
                T targetObj = copierTargetBean(sourceObj, source, target);
                if (Objects.nonNull(targetObj)) {
                    targetList.add(targetObj);
                }
            }
            return targetList;
        }
        return null;
    }

    /**
     * 拷贝目标Set
     *
     * @param sourceObjList 目标Set
     * @param source        原Class
     * @param target        目标Class
     * @param <T>
     * @return
     */
    public static <T> Set<T> copierTargetBeanSet(Set<?> sourceObjList, Class source, Class<T> target) {
        if (null != sourceObjList) {
            Set<T> targetList = new HashSet<>(sourceObjList.size());
            for (Object sourceObj : sourceObjList) {
                T targetObj = copierTargetBean(sourceObj, source, target);
                if (Objects.nonNull(targetObj)) {
                    targetList.add(targetObj);
                }
            }
            return targetList;
        }
        return null;
    }
}
