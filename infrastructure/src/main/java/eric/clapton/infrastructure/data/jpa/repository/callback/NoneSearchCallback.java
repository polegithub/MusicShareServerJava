package eric.clapton.infrastructure.data.jpa.repository.callback;

import javax.persistence.Query;

import eric.clapton.infrastructure.data.jpa.search.Searchable;

// TODO: Auto-generated Javadoc
/**
 * The Class NoneSearchCallback.
 */
public final class NoneSearchCallback implements SearchCallback {

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.roomy.repository.callback.SearchCallback#prepareQL(java.lang.StringBuilder
     * , cn.roomy.entity.search.Searchable)
     */
    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.roomy.repository.callback.SearchCallback#prepareOrder(java.lang.
     * StringBuilder, cn.roomy.entity.search.Searchable)
     */
    @Override
    public void prepareOrder(StringBuilder ql, Searchable search) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.roomy.repository.callback.SearchCallback#setValues(javax.persistence
     * .Query, cn.roomy.entity.search.Searchable)
     */
    @Override
    public void setValues(Query query, Searchable search) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.roomy.repository.callback.SearchCallback#setPageable(javax.persistence
     * .Query, cn.roomy.entity.search.Searchable)
     */
    @Override
    public void setPageable(Query query, Searchable search) {
    }
}
