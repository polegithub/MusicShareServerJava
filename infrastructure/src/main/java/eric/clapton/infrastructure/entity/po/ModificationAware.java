package eric.clapton.infrastructure.entity.po;

import java.util.Calendar;

public interface ModificationAware {

    /**
     * 获取上次修改该对象的时间。
     * 
     * @return
     */
    Calendar getLastModifiedAt();

    /**
     * 獲取上次修改該對象的用戶。
     * 
     * @return
     */
    String getLastModifiedBy();

    /**
     * 設置上次修改該對象的時間。
     * 
     * @param modifiedAt
     */
    void setLastModifiedAt(Calendar modifiedAt);

    /**
     * 设置上次修改该对象的用户。
     * 
     * @param modifiedBy
     */
    void setLastModifiedBy(String modifiedBy);
}
