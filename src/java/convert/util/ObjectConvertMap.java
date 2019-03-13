package convert.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Convert-Util
 * @description: 将对象转为Map
 * @author: Mr.Pxw
 * @create: 2019-03-13 20:25
 **/
public class ObjectConvertMap {
    /**提取的数据*/
    private Map<String,Map<String,String>> data;

    /**提取的对象*/
    private Object origin;

    /**集合字段的序列化策略*/
    private CollectionParseStrategy parseStrategy;

    public ObjectConvertMap(Object origin) {
        this(origin,new DefaultCollectionParseStrategy());
    }

    public ObjectConvertMap(Object origin, CollectionParseStrategy parseStrategy) {
        this.origin = origin;
        this.parseStrategy = parseStrategy;
    }

    public Map<String,Map<String,String>> extract(){
        if(origin==null) return null;
        extractExtend(origin.getClass());
        return data;
    }

    /**
     * 通过字段名提取值
     * @param fieldName 字段名
     * @param clazz Class对象
     * @return value
     */
    private String extractValueByField(String fieldName, Class<?> clazz) {
        String firstLetter = fieldName.substring(0, 1);
        String fieldValue = null;
        try {
            Method method = clazz.getMethod("get" + fieldName.replaceFirst(firstLetter, firstLetter.toUpperCase()));
            Object tempVar = method.invoke(origin);
            if(tempVar instanceof Collection){
                Collection collection= (Collection) tempVar;
                fieldValue = parseStrategy.strategy(collection);
            }else {
                fieldValue = tempVar == null ? "" : tempVar.toString();
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }

    /**
     * 递归提取对象的k-v
     * @param clazz Class
     */
    private void extractExtend(Class<?> clazz){
        Class<?> superclass = clazz.getSuperclass();
        if(superclass.equals(Object.class)) return;
        extractExtend(superclass);
        Map<String,String> map=new HashMap<>();
        for (Field field : superclass.getDeclaredFields()) {
            String fieldName = field.getName();
            String fieldValue = extractValueByField(fieldName, superclass);
            map.put(fieldName, fieldValue);
        }
        data.put(superclass.getSimpleName(),map);
    }
}
