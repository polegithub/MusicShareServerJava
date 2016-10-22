package eric.clapton.musician.core.entity.po.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_order_genre")
public class OrderGenre extends BaseEntity {
	private static final long serialVersionUID = -3927368711996708897L;

	private Order order;
	private String genre;

	public OrderGenre() {
	}

	public OrderGenre(Order order, String genre) {
		this.order = order;
		this.genre = genre;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return super.getId();
	}

	@JoinColumn(name = "order_id", updatable = false)
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Column(name = "genre", updatable = false)
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
