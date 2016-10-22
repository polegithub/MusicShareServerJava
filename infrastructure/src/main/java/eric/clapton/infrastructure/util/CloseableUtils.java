package eric.clapton.infrastructure.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseableUtils {
    protected CloseableUtils() {

    }

    /**
     * 尝试释放 {@link Closeable} 对象所占用的资源。
     * 
     * @param closeable
     *            要释放资源的 {@link Closeable} 对象，或 <code>null</code>。
     */

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {

            }
        }
    }
}
