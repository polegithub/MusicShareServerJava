package eric.clapton.musician.core.util;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class QueryUtils {
    protected QueryUtils() {

    }

    /**
     * 将参数设置到查询中。
     * 
     * @param query
     *            要设置参数的查询对象。不能为 <code>null</code>。
     * @param parameters
     *            要设置的参数列表。可以为 <code>null</code>。
     */
    public static final void setParameters(Query query, Map<String, Object> parameters) {
        if (parameters != null) {
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                query.setParameter(parameter.getKey(), parameter.getValue());
            }
        }
    }

    /**
     * 执行 JPQL count 查询。此方法也可以执行那些返回的结果集<b>有且仅有一个</b>非空 {@link Long} 值的 JPQL 查询。
     * 
     * @param entityManager
     *            要执行查询的 JPA 实体管理器。不能为 <code>null</code>。
     * @param countQl
     *            要执行的 count 语句。不能为 <code>null</code>。
     * @param parameters
     *            要附加到查询中的参数。可以为 <code>null</code>。
     * @return
     */
    public static long count(EntityManager entityManager, String countQl,
            Map<String, Object> parameters) {
        TypedQuery<Long> countQuery = entityManager.createQuery(countQl, Long.class);
        QueryUtils.setParameters(countQuery, parameters);

        return countQuery.getSingleResult();
    }

    public static long count(EntityManager entityManager, String countQl) {
        return count(entityManager, countQl, null);
    }
}
