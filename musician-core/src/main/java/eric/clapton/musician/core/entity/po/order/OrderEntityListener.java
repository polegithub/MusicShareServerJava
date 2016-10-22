package eric.clapton.musician.core.entity.po.order;

import java.util.Calendar;

import javax.persistence.PrePersist;

public class OrderEntityListener {
	@PrePersist
	public void prePersist(Object o) {
		if (o instanceof Order) {
			Order order = (Order) o;
			order.setCreated(Calendar.getInstance());
		}
	}
}
