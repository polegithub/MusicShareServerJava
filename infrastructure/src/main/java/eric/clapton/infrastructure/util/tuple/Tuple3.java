package eric.clapton.infrastructure.util.tuple;

public class Tuple3<T1, T2, T3> implements Tuple {
    final T1 item1;
    final T2 item2;
    final T3 item3;

    public Tuple3(T1 item1, T2 item2, T3 item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    @Override
    public StringBuffer appendTo(StringBuffer buffer) {
        if (buffer != null) {
            buffer.append(item1).append(", ");
            buffer.append(item2).append(", ");
            buffer.append(item3);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return appendTo(new StringBuffer("(")).append(')').toString();
    }

    @Override
    public int size() {
        return 3;
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    public T3 getItem3() {
        return item3;
    }
}
