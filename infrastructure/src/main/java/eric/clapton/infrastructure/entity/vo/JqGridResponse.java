package eric.clapton.infrastructure.entity.vo;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.domain.Page;

/**
 * 用于 jqGrid 的响应。
 * 
 * @author xuw
 *
 */
public final class JqGridResponse implements Serializable {
    private static final JqGridResponse EMPTY = new JqGridResponse(0, 0, 0L, new Object[] {});

    private static final long serialVersionUID = 6309193286940249653L;

    private final int page;
    private final int total;
    private final Long records;
    private final Object rows;

    public JqGridResponse(int page, int total, Long records, Object rows) {
        this.page = page;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public Long getRecords() {
        return records;
    }

    public Object getRows() {
        return rows;
    }

    /**
     * 从分页数据创建 jqGrid 响应。
     * 
     * @param page
     *            要从中创建响应的分页数据。不能为 <code>null</code>。
     * @return 创建的 jqGrid 响应对象。
     */
    public static JqGridResponse from(Page<?> page) {
        int pageNumber = page.getNumber() + 1;
        return new JqGridResponse(pageNumber, page.getTotalPages(), page.getTotalElements(),
                page.getContent());
    }

    /**
     * 从列表创建 jqGrid 响应。
     * 
     * @param collection
     *            要从中创建响应的列表。不能为 <code>null</code>。
     * @retur创建的 jqGrid 响应对象。
     */
    public static JqGridResponse from(Collection<?> collection) {
        return new JqGridResponse(1, 1, Long.valueOf(collection.size()), collection);
    }

    public static final JqGridResponse empty() {
        return EMPTY;
    }
}
