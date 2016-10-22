package eric.clapton.infrastructure.service;

import java.util.List;

import org.springframework.data.domain.Page;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.entity.po.BaseEntity;

public interface BaseService<T extends BaseEntity> {
    /**
     * 表示找不到对象的错误。
     */
    public static final int ERROR_NOT_FOUND = -1;
    public static final int ERROR_CORRUPTED_DATA = -2;

    public abstract T save(T t);

    public abstract T getOne(Long id);

    public abstract T findOne(Long id);

    public abstract T update(T t);

    public abstract T delete(Long id);

    /**
     * 根据指定的查询条件，分页检索对象。
     * 
     * @param searchable
     *            包含分页信息和查询条件的对象。
     * @return 查询出的当前页对象。
     */
    public abstract Page<T> findAll(Searchable searchable);

    /**
     * 按条件排序查询实体(不分页).
     * 
     * @param searchable
     *            条件
     * @return the list
     */

    public abstract List<T> findAllWithSort(Searchable searchable);

    /**
     * 删除指定的对象。
     * 
     * @param m
     *            要删除的对象。
     * @return 删除后的对象。
     */
    public abstract T delete(T m);

    public abstract List<T> findAll();
}
