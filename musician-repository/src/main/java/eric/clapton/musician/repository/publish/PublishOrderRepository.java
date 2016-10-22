package eric.clapton.musician.repository.publish;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.publish.PublishOrder;
import eric.clapton.musician.core.entity.po.publish.PublishTime;

public interface PublishOrderRepository extends BaseRepository<PublishOrder, Long> {

	/*
	 * 20分钟未付款订单取消掉
	 */
	@Query(value = "select c.* from ms_merchant_publish c where c.state = 'CREATED' and TIMESTAMPDIFF(MINUTE,c.created,NOW()) > 20", nativeQuery = true)
	List<PublishOrder> getOuttimeOrders();

	/*
	 * 未抢订单退款
	 */
	@Query(value = "SELECT c.* FROM ms_publish_time c WHERE c.state = 'PAYING'  ", nativeQuery = true)
	List<PublishTime> getWaitOrders();

	/*
	 * 打款订单
	 */
	@Query(value = "SELECT c.* FROM ms_publish_time c WHERE c.state = 'COMPLETE'", nativeQuery = true)
	List<PublishTime> getCompleteOrders();

	/*
	 * 商家不同状态订单
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m WHERE m.id = c.address_id and c.id = d.orderid and  d.state in (?1) and  c.account_id = ?2 limit ?3,?4", nativeQuery = true)
	List<Object> getShoperOrders(String state, Long userId, int startPos, int size);
	
	@Query(value = "select count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d WHERE c.id = d.orderid and  d.state in (?1) and  c.account_id = ?2 ", nativeQuery = true)
	int getShoperOrdersNo(String state, Long userId);

	/*
	 * 商家全部状态订单
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m WHERE m.id = c.address_id and c.id = d.orderid  and  c.account_id = ?1 limit ?2,?3", nativeQuery = true)
	List<Object> getShoperAllOrders(Long userId, int startPos, int size);
	
	@Query(value = "select count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d WHERE c.id = d.orderid  and  c.account_id = ?1 ", nativeQuery = true) 
	int getShoperAllOrdersNo(Long userId);

	/*
	 * 音乐人不同状态订单
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_publish_grap e ,ms_address_book m WHERE m.id = c.address_id and c.id = d.orderid and e.orderid = c.id and e.timeid = d.id and  d.state in (?1) and  e.userid = ?2 limit ?3,?4", nativeQuery = true)
	List<Object> getMusicumOrders(String state, Long userId, int startPos, int size);
	
	@Query(value = "SELECT count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d,ms_publish_grap e WHERE c.id = d.orderid and e.orderid = c.id and e.timeid = d.id and  d.state in (?1) and  e.userid = ?2", nativeQuery = true)
	int getMusicumOrdersNo(String state, Long userId);

	/*
	 * 音乐人全部状态订单
	 */
	//@Query(value = "SELECT c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state FROM ms_merchant_publish c,ms_publish_time d,ms_publish_grap e WHERE c.id = d.orderid and e.orderid = c.id and e.timeid = d.id and  e.userid = ?1  limit ?2,?3", nativeQuery = true)
	
	@Query(value="SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_publish_grap e ,ms_address_book m WHERE m.id = c.address_id and  c.id = d.orderid and e.orderid = c.id and e.timeid = d.id and  e.userid = ?1 UNION SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d ,ms_address_book m WHERE m.id = c.address_id and  c.id = d.orderid AND (d.state = 'PAYING' OR d.state = 'ING') limit ?2,?3" , nativeQuery = true)
	List<Object> getMusicumAllOrders(Long userId, int startPos, int size);

	@Query(value = "SELECT sum(g.c1) FROM(SELECT count(1) as c1 FROM ms_merchant_publish c,ms_publish_time d,ms_publish_grap e  WHERE  c.id = d.orderid and e.orderid = c.id and e.timeid = d.id and  e.userid = ?1 UNION ALL SELECT count(1) as c1 FROM ms_merchant_publish c,ms_publish_time d  WHERE c.id = d.orderid AND (d.state = 'PAYING' OR d.state = 'ING') )g ", nativeQuery = true)
	int getMusicumAllOrdersNo(Long userId);
	
	
	/*
	 * 商家查看已抢订单列表
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs,e.userid FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m ,ms_publish_grap e WHERE e.orderid = c.id and e.timeid = d.id and m.id = c.address_id and c.id = d.orderid and  d.state = 'ING' and  c.account_id = ?1 and c.id = ?2 and d.id = ?3 limit ?4,?5", nativeQuery = true)
	List<Object> getShoperAgreeOrders(Long userId,Long orderId,Long timeId, int startPos, int size);
	
	@Query(value = "SELECT count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m ,ms_publish_grap e WHERE e.orderid = c.id and e.timeid = d.id and m.id = c.address_id and c.id = d.orderid and  d.state = 'ING' and  c.account_id = ?1 and c.id = ?2 and d.id = ?3 ", nativeQuery = true)
	int getShoperAgreeOrdersNo(Long userId,Long orderId,Long timeId);

	
	
	/*
	 * 音乐人可抢订单
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m,ms_publish_grap e WHERE e.orderid = c.id and e.timeid = d.id and e.userid <> ?1 and m.id = c.address_id and c.id = d.orderid and  (d.state = 'PAYING' OR d.state = 'ING')  limit ?2,?3", nativeQuery = true)
	List<Object> getMusicumAviabledOrders(Long accountId,int startPos, int size);
	
	@Query(value = "SELECT count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m ,ms_publish_grap e WHERE e.orderid = c.id and e.timeid = d.id and e.userid <> ?1 and m.id = c.address_id and c.id = d.orderid and  (d.state = 'PAYING' OR d.state = 'ING') ", nativeQuery = true)
	int getMusicumAviabledOrdersNo(Long accountId);
	
	
	
	/*
	 * 商家查看已抢订单列表byName
	 */
	@Query(value = "SELECT DISTINCT(d.id),c.title,c.description,c.address_id,c.account_id,c.created,d.orderid,d.price,d.starttime,d.endtime,d.remark,d.state,d.need,d.deadline,m.name,m.longitude,m.latitude,m.address,m.detail_address,m.city,m.contact,m.tel_nrs,e.userid FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m ,ms_publish_grap e ,ms_account f WHERE f.id = e.userid and e.orderid = c.id and e.timeid = d.id and m.id = c.address_id and c.id = d.orderid and  d.state = 'ING' and  c.account_id = ?1 and c.id = ?2 and d.id = ?3 and f.nickname like ?4 limit ?5,?6", nativeQuery = true)
	List<Object> getOrdersNyMusicName(Long accountId,Long orderId,Long timeId,String name, int startPos, int size);
	
	@Query(value = "SELECT count(DISTINCT(d.id)) FROM ms_merchant_publish c,ms_publish_time d,ms_address_book m ,ms_publish_grap e,ms_account f WHERE f.id = e.userid and e.orderid = c.id and e.timeid = d.id and m.id = c.address_id and c.id = d.orderid and  d.state = 'ING' and  c.account_id = ?1 and c.id = ?2 and d.id = ?3 and f.nickname like ?4 ", nativeQuery = true)
	int getOrdersNyMusicNameNo(Long accountId,Long orderId,Long timeId,String name);
}
