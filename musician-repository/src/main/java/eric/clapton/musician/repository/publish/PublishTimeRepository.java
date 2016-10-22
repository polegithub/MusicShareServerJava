package eric.clapton.musician.repository.publish;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.PublishTime;

public interface PublishTimeRepository extends
        BaseRepository<PublishTime, Long> {
    @Query(value = "select c from PublishTime c where c.orderid = ?1")
    List<PublishTime> getOrderTimes(Long orderId);

    @Query(value = "select c from PublishTime c where c.orderid = ?1 and c.starttime = ?2 and c.endtime = ?3")
    PublishTime getOrderTimes(Long orderId, Date startTime, Date endTime);
    
    
    @Query(value = "SELECT d.id, c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m WHERE m.id = c.address_id and c.id = d.orderid and c.id = ?1 and d.id = ?2 ", nativeQuery = true)
	Object getOrderDetail(Long orderId,Long timeId);

    @Query(value = "select c from PublishTime c where c.orderid = ?1 and c.id = ?2")
    PublishTime getTimeOrder(Long orderId,Long timeId);
}
