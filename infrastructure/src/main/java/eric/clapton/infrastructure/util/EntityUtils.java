package eric.clapton.infrastructure.util;

import eric.clapton.infrastructure.entity.po.BaseEntity;

public class EntityUtils {
    public static <TEntity extends BaseEntity> TEntity findById(Iterable<TEntity> entities, Long id) {
        for (TEntity entity : entities) {
            if (entity != null && NumberUtils.equals(id, entity.getId())) {
                return entity;
            }
        }
        return null;
    }
}
