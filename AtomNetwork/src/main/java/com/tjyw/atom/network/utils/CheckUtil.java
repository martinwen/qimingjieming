package com.tjyw.atom.network.utils;

import java.util.Collection;
import java.util.Map;

public class CheckUtil {
    /**
     * 检查任意Object是否为空
     * <hr>
     * shallow check : 不会检查容器内部的元素是否为空
     * @return
     */
    public static boolean isEmpty(Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof Collection<?>) {
            // 检查各种Collection是否为空(List,Queue,Set)
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Map<?, ?>) {
            // 检查各种Map
            return ((Map<?, ?>) obj).isEmpty();
        } else if (obj instanceof CharSequence) {
            // 检查各种CharSequence
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            // 检查各种base array
            // return Array.getLength(obj) == 0;
            if (obj instanceof Object[]) {
                return ((Object[]) obj).length == 0;
            } else if (obj instanceof int[]) {
                return ((int[]) obj).length == 0;
            } else if (obj instanceof long[]) {
                return ((long[]) obj).length == 0;
            } else if (obj instanceof short[]) {
                return ((short[]) obj).length == 0;
            } else if (obj instanceof double[]) {
                return ((double[]) obj).length == 0;
            } else if (obj instanceof float[]) {
                return ((float[]) obj).length == 0;
            } else if (obj instanceof boolean[]) {
                return ((boolean[]) obj).length == 0;
            } else if (obj instanceof char[]) {
                return ((char[]) obj).length == 0;
            } else if (obj instanceof byte[]) {
                return ((byte[]) obj).length == 0;
            }
        } else if (obj instanceof EmptyCheckable) {
            return ((EmptyCheckable) obj).isEmpty();
        }

        return false;
    }

    public interface EmptyCheckable {
        boolean isEmpty();
    }
}
