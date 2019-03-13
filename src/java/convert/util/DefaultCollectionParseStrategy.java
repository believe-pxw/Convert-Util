package convert.util;

import java.util.Collection;

/**
 * @program: Convert-Util
 * @description:
 * @author: Mr.Pxw
 * @create: 2019-03-13 22:07
 **/
public class DefaultCollectionParseStrategy implements CollectionParseStrategy {
    @Override
    public String strategy(Collection collection) {
        return collection.toString();
    }
}
