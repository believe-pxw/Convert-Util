package convert.util;

import java.util.Collection;

/**
 * 集合的序列化策略
 */
@FunctionalInterface
public interface CollectionParseStrategy {
    String strategy(Collection collection);
}
