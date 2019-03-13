package convert.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Convert-Util
 * @description:
 * @author: Mr.Pxw
 * @create: 2019-03-13 22:21
 **/
public class CustomUtil {
    public static Map<String ,String > parseFormToMap(String formParams){
        Map<String ,String > paramsMap=new HashMap<>();
        if("".equals(formParams)){
            return paramsMap;
        }
        String[] entries = formParams.split("&");
        for (String entry : entries) {
            String[] team = entry.split("=",2);
            if(team.length==2){
                paramsMap.put(team[0],team[1]);
            }
        }
        return paramsMap;
    }

}
