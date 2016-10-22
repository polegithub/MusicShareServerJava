package eric.clapton.musician.service.order.impl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.DateUtils;
import eric.clapton.musician.core.entity.dto.order.OrderInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;
import eric.clapton.musician.core.entity.po.order.Order;
import eric.clapton.musician.core.entity.po.order.OrderGenre;
import eric.clapton.musician.core.entity.po.order.OrderState;
import eric.clapton.musician.repository.order.OrderRepository;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.account.AddressBookService;
import eric.clapton.musician.service.order.OrderErrors;
import eric.clapton.musician.service.order.OrderService;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRepository> implements OrderService {
	@Resource
	private AddressBookService addressBookService;
	@Resource
	private AccountService accountService;

	@Override
	@Resource
	protected void baseSetRepository(OrderRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public Order delete(Long accountId, Long id) {
		Account account = getAccount(accountId);
		Order o = getOrderOfOrThrowIfNotFound(id, account);

		return delete(o);
	}

	@Override
	protected boolean preDelete(Order entity) {
		if (super.preDelete(entity)) {
			if (entity.getState() != OrderState.NORMAL) {
				throw new ServiceException(OrderErrors.ORDER_NOT_DELETABLE,
						String.format(Locale.CHINA, "订单状态为“%s”时才能删除哦~", OrderState.NORMAL.getDescription()));
			}
		}
		return true;
	}

	@Override
	@Transactional
	public Order create(Long accountId, OrderInfo orderInfo) {
		cleanUpAndValidate(orderInfo, true);

		Account account = accountService.findOne(accountId);

		AddressBookEntry e = getAddressFromAddressBookOf(orderInfo.getAddressId(), account);

		final Order o = new Order();
		o.setAccount(account);
		o.setAddress(e.getAddress());
		o.setCity(e.getCity());
		o.setContact(e.getContact());
		o.setDateFrom(DateUtils.toCalendar(orderInfo.getTimeStart()));
		o.setDateTo(DateUtils.toCalendar(orderInfo.getTimeEnd()));
		o.setDeadline(DateUtils.toCalendar(orderInfo.getDeadline()));
		o.setDescription(orderInfo.getDescription());
		o.setDetailAddress(e.getDetailAddress());
		o.setLatitude(e.getLatitude());
		o.setLongitude(e.getLongitude());
		o.setPrice(orderInfo.getPrice());
		o.setRecruits(orderInfo.getPeopleCount());
		o.setTelephoneNumbers(e.getTelephoneNumbers());
		o.setTitle(orderInfo.getTitle());

		List<OrderGenre> genres = orderInfo.getMusicType().stream().map(t -> new OrderGenre(o, t))
				.collect(Collectors.toList());
		o.setGenres(genres);

		save(o);

		return o;
	}

	@Override
	public Order update(Long accountId, OrderInfo orderInfo) {
		cleanUpAndValidate(orderInfo, true);

		Account account = getAccount(accountId);
		final Order o = getOrderOfOrThrowIfNotFound(orderInfo, account);
		if (o.getState() != OrderState.NORMAL) {
			throw new ServiceException(OrderErrors.ORDER_NOT_UPDATABLE,
					String.format(Locale.CHINA, "订单状态为“%s”时才能修改哦~", OrderState.NORMAL.getDescription()));
		}

		setAddressInfo(o, orderInfo, account);

		o.setDateFrom(DateUtils.toCalendar(orderInfo.getTimeStart()));
		o.setDateTo(DateUtils.toCalendar(orderInfo.getTimeEnd()));
		o.setDeadline(DateUtils.toCalendar(orderInfo.getDeadline()));
		o.setDescription(orderInfo.getDescription());

		o.setPrice(orderInfo.getPrice());
		o.setRecruits(orderInfo.getPeopleCount());
		o.setTitle(orderInfo.getTitle());

		List<OrderGenre> genres = orderInfo.getMusicType().stream().map(t -> new OrderGenre(o, t))
				.collect(Collectors.toList());
		o.setGenres(genres);

		save(o);

		return o;
	}

	private void cleanUpAndValidate(OrderInfo orderInfo, boolean forUpdate) {
		if (!forUpdate) {
			if (orderInfo.getAddressId() == null) {
				throw new ServiceException(OrderErrors.ORDER_EDIT_NO_ADDRESS, "没有指定地址。");
			}
		}
		if (orderInfo.getDeadline() == null) {
			throw new ServiceException(OrderErrors.ORDER_EDIT_NO_DEADLINE, "没有指定截止日期。");
		}
	}

	private void setAddressInfo(Order o, OrderInfo orderInfo, Account account) {
		if (orderInfo.getAddressId() != null) {
			AddressBookEntry e = getAddressFromAddressBookOf(orderInfo.getAddressId(), account);

			o.setAddress(e.getAddress());
			o.setCity(e.getCity());
			o.setContact(e.getContact());
			o.setDetailAddress(e.getDetailAddress());
			o.setLatitude(e.getLatitude());
			o.setLongitude(e.getLongitude());
			o.setTelephoneNumbers(e.getTelephoneNumbers());
		}
	}

	@Override
	public Order getOrderOf(Long orderId, Long accountId) {
		Account account = accountService.findOne(accountId);
		return account == null ? null : getOrderOf(orderId, account, false);
	}

	private Order getOrderOf(Long orderId, Account account, boolean throwIfNotFound) {
		Order order = findOne(orderId);
		if (order != null) {
			if (!account.getId().equals(order.getAccountId())) {
				order = null;
			}
		}
		if (throwIfNotFound && order == null) {
			throw new ServiceException(OrderErrors.NO_ORDER_EXISTS, "订单不存在。");
		}
		return order;
	}

	private Order getOrderOfOrThrowIfNotFound(Long orderId, Account account) {
		return getOrderOf(orderId, account, true);
	}

	private Order getOrderOfOrThrowIfNotFound(OrderInfo orderInfo, Account account) {
		return getOrderOfOrThrowIfNotFound(orderInfo.getId(), account);
	}

	private Account getAccount(Long accountId) {
		Account account = accountService.findOne(accountId);
		if (account == null) {
			logger.warn("No account with identifier {} exists.", accountId);

			throw new ServiceException(OrderErrors.NO_ACCOUNT_EXISTS, "无法检索用户信息，指定的用户不存在。");
		}

		return account;
	}

	/**
	 * 从指定账户的地址簿中检索指定 ID 的地址。
	 * 
	 * @param addressId
	 * @param account
	 * @return
	 */
	private AddressBookEntry getAddressFromAddressBookOf(Long addressId, Account account) {
		AddressBookEntry e = addressBookService.findOne(addressId);
		if (e != null) {
			if (!e.getOwner().getId().equals(account.getId())) {
				e = null;
			}
		}

		if (e == null) {
			throw new ServiceException(0x5001000, "指定的地址不存在。");
		}
		return e;
	}
}
