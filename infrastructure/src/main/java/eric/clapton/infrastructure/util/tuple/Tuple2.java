package eric.clapton.infrastructure.util.tuple;

/**
 * 表示二元组。
 * 
 * @author xuw
 *
 * @param <T1>
 *            元组中第一个元素的类型。
 * @param <T2>
 *            元组中第二个元素的类型。
 */
public class Tuple2<T1, T2> implements Tuple {
    final T1 item1;
    final T2 item2;

    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public StringBuffer appendTo(StringBuffer buffer) {
        if (buffer != null) {
            buffer.append(item1).append(", ").append(item2);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return appendTo(new StringBuffer("(")).append(')').toString();
    }

    @Override
    public int size() {
        return 2;
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }
}
