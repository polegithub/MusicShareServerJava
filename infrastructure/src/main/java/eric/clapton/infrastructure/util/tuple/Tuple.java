package eric.clapton.infrastructure.util.tuple;

/**
 * <p>
 * 表示元组。
 * </p>
 * <p>
 * 元组是将一个或多个任意类型的值组合成单个值的数据结构。
 * </p>
 * <p>
 * 可以在内部实现中使用元组而不必创建新的类保存多个值，这在功能上类似于匿名对象。但元组的可读性差，不建议将元组对象作为公开的返回值或参数。
 * </p>
 * 
 * @author xuw
 *
 */
public interface Tuple {
    StringBuffer appendTo(StringBuffer buffer);

    /**
     * 获取元组中包含元素的个数。
     * 
     * @return
     */
    int size();

}
