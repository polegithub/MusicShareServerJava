package eric.clapton.infrastructure.data.jpa.repository.callback;

import javax.persistence.Query;

import eric.clapton.infrastructure.data.jpa.search.Searchable;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchCallback.
 * 
 * @author wuwei 2014-4-29
 */
public interface SearchCallback {

    /** The Constant NONE. */
    public static final SearchCallback NONE = new NoneSearchCallback();

    /** The Constant DEFAULT. */
    public static final SearchCallback DEFAULT = new DefaultSearchCallback();

    /**
     * 动态拼HQL where、group by having.
     * 
     * @param ql
     *            the ql
     * @param search
     *            the search
     */
    public void prepareQL(StringBuilder ql, Searchable search);

    /**
     * Prepare order.
     * 
     * @param ql
     *            the ql
     * @param search
     *            the search
     */
    public void prepareOrder(StringBuilder ql, Searchable search);

    /**
     * 根据search给query赋值及设置分页信息.
     * 
     * @param query
     *            the query
     * @param search
     *            the search
     */
    public void setValues(Query query, Searchable search);

    /**
     * Sets the pageable.
     * 
     * @param query
     *            the query
     * @param search
     *            the search
     */
    public void setPageable(Query query, Searchable search);

}
