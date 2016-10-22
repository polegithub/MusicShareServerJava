package eric.clapton.infrastructure.util.tuple;

/**
 * <p>
 * 提供方法创建{@linkplain Tuple 元组}，调用这些方法可以实例化特定元组对象而不需要显式指定每个元组分量的类型。
 * </p>
 * <p>
 * 直接支持创建具有一到九个组件（即一元组到九元组）的元组对象。
 * 尽管对一个元组可以拥有的组件数没有实际限制，但是这个类中的方法不能用于创建包含十个或更多组件的元组。 若要创建此类元组，请调用
 * {@link TupleN} 的
 * {@linkplain TupleN#TupleN(Object, Object, Object, Object, Object, Object, Object, Tuple)
 * 构造函数}。
 * </p>
 * 
 * @author xuw
 *
 */
public class Tuples {
    /**
     * 创建新的 1 元组，即单一实例。
     * 
     * @param item
     *            元组的唯一一个分量的值。
     * @return 值为 <code>(item)</code> 的元组。
     */
    public static <T> Tuple1<T> create(T item) {
        return new Tuple1<T>(item);
    }

    /**
     * 创建新的 2 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @return 值为 <code>(item1, item2)</code> 的 2 元组。
     */
    public static <T1, T2> Tuple2<T1, T2> create(T1 item1, T2 item2) {
        return new Tuple2<T1, T2>(item1, item2);
    }

    /**
     * 创建新的 3 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @return 值为 <code>(item1, item2, item3)</code> 的 3 元组。
     */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 item1, T2 item2, T3 item3) {

        return new Tuple3<T1, T2, T3>(item1, item2, item3);
    }

    /**
     * 创建新的 4 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @return 值为 <code>(item1, item2, item3, item4)</code> 的 4 元组。
     */
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 item1, T2 item2, T3 item3,
            T4 item4) {
        return new Tuple4<T1, T2, T3, T4>(item1, item2, item3, item4);
    }

    /**
     * 创建新的 5 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @param item5
     *            此元组的第五个分量的值。
     * @return 值为 <code>(item1, item2, item3, item4, item5)</code> 的 5 元组。
     */
    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> create(T1 item1, T2 item2,
            T3 item3, T4 item4, T5 item5) {
        return new Tuple5<T1, T2, T3, T4, T5>(item1, item2, item3, item4, item5);
    }

    /**
     * 创建新的 6 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @param item5
     *            此元组的第五个分量的值。
     * @param item6
     *            此元组的第六个分量的值。
     * @return 值为 <code>(item1, item2, item3, item4, item5, item6)</code> 的 6
     *         元组。
     */
    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> create(T1 item1,
            T2 item2, T3 item3, T4 item4, T5 item5, T6 item6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(item1, item2, item3, item4, item5, item6);
    }

    /**
     * 创建新的 7 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @param item5
     *            此元组的第五个分量的值。
     * @param item6
     *            此元组的第六个分量的值。
     * @param item7
     *            此元组的第七个分量的值。
     * @return 值为 <code>(item1, item2, item3, item4, item5, item6, item7)</code>
     *         的 7 元组。
     */
    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> create(T1 item1,
            T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(item1, item2, item3, item4, item5, item6,
                item7);
    }

    /**
     * 创建新的 8 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @param item5
     *            此元组的第五个分量的值。
     * @param item6
     *            此元组的第六个分量的值。
     * @param item7
     *            此元组的第七个分量的值。
     * @param item8
     *            此元组的第八个分量的值。
     * @return 值为
     *         <code>(item1, item2, item3, item4, item5, item6, item7, item8)</code>
     *         的八元组。
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> TupleN<T1, T2, T3, T4, T5, T6, T7, Tuple1<T8>> create(
            T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7, T8 item8) {
        return new TupleN<T1, T2, T3, T4, T5, T6, T7, Tuple1<T8>>(item1, item2, item3, item4,
                item5, item6, item7, create(item8));
    }

    /**
     * 创建新的 9 元组。
     * 
     * @param item1
     *            此元组的第一个分量的值。
     * @param item2
     *            此元组的第二个分量的值。
     * @param item3
     *            此元组的第三个分量的值。
     * @param item4
     *            此元组的第四个分量的值。
     * @param item5
     *            此元组的第五个分量的值。
     * @param item6
     *            此元组的第六个分量的值。
     * @param item7
     *            此元组的第七个分量的值。
     * @param item8
     *            此元组的第八个分量的值。
     * @param item9
     *            此元组的第九个分量的值。
     * @return 值为
     *         <code>(item1, item2, item3, item4, item5, item6, item7, item8, item9)</code>
     *         的九元组。
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> TupleN<T1, T2, T3, T4, T5, T6, T7, Tuple2<T8, T9>> create(
            T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7, T8 item8, T9 item9) {
        return new TupleN<T1, T2, T3, T4, T5, T6, T7, Tuple2<T8, T9>>(item1, item2, item3, item4,
                item5, item6, item7, create(item8, item9));
    }
}
