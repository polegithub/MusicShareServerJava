package eric.clapton.infrastructure.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Enumerable {
    protected Enumerable() {

    }

    public static final <T> boolean isNullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static final <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static final <T> T firstOrDefault(T[] array) {
        return isNullOrEmpty(array) ? null : array[0];
    }

    public static final <T> T firstOrDefault(Iterator<T> iterator) {
        if (iterator == null) {
            return null;
        }
        while (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public static final <T> T firstOrDefault(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }

        return firstOrDefault(iterable.iterator());
    }

    public static final <T> T lastOrDefault(List<T> collection) {
        return isNullOrEmpty(collection) ? null : collection.get(collection.size() - 1);
    }
}
