package eric.clapton.infrastructure.util.tuple;

public class Tuple1<T> implements Tuple {
    final T item;

    public Tuple1(T item) {
        this.item = item;
    }

    @Override
    public StringBuffer appendTo(StringBuffer buffer) {
        if (buffer != null) {
            buffer.append(item);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return appendTo(new StringBuffer("(")).append(')').toString();
    }

    @Override
    public int size() {
        return 1;
    }

    public T getItem() {
        return item;
    }
}
