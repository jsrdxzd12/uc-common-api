package org.mozi.xzd.api.common.map;

import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 09:57
 */
public class MapUtils {

    /**
     * ß
     *
     * @param m
     * @return
     */
    public static Map<String, String> convertMultiToRegularMap(MultiValueMap<String, String> m) {
        Map<String, String> map = new HashMap<>(m.size());
        if (m == null) {
            return map;
        }
        for (Map.Entry<String, List<String>> entry : m.entrySet()) {

        }
        return map;
    }

    public static Map<String,Object > toParamMap( Map<String,Object> map ){


        Map<String,Object> newMap =new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof String){
                newMap.put(entry.getKey(),  entry.getValue());
            }
        }
        return newMap;
    }

    public static Map<String, String> convertMultiToRegularMap(Map<String, String[]> m) {
        Map<String, String> map = new HashMap<>(m.size());
        if (m == null) {
            return map;
        }
        for (Map.Entry<String, String[]> entry : m.entrySet()) {
            paramBuilder(entry,map);
        }
        return map;
    }


    private static void paramBuilder(Map.Entry<String, String[]> entry ,Map<String,String> map){


        StringBuilder sb = new StringBuilder();
        for (String s : entry.getValue()) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(s);
        }
        map.put(entry.getKey(), sb.toString());
    }

}
