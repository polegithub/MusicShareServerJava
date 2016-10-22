package eric.clapton.musician.web.controller.my;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.account.AddressBookEntryInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;
import eric.clapton.musician.service.account.AddressBookService;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/my/address")
@ResponseBody
public class AddressBookController extends ApiControllerSupport {
	@Resource
	private AddressBookService addressBookService;

	@RequestMapping("/list")
	public ApiResponse list() {
		Account account = CurrentUser.getAccount();

		AddressBookEntryInfo[] entries = account.getAddressBookEntries().stream().map(e -> {
			AddressBookEntryInfo i = new AddressBookEntryInfo();

			i.setAddress(e.getAddress());
			i.setCity(e.getCity());
			i.setContact(e.getContact());
			i.setLongitude(e.getLongitude());
			i.setLatitude(e.getLatitude());
			i.setDetailAddress(e.getDetailAddress());
			i.setId(e.getId());
			i.setName(e.getName());
			i.setRemarks(e.getRemarks());
			i.setTelephoneNumbers(e.getTelephoneNumbersAsArray());

			return i;
		}).toArray(AddressBookEntryInfo[]::new);

		return ApiResponse.succeed(entries);
	}

	@RequestMapping("/new")
	public ApiResponse create(@RequestBody() AddressBookEntryInfo i) {
		AddressBookEntry e = new AddressBookEntry();
		e.setAddress(i.getAddress());
		e.setCity(i.getCity());
		e.setContact(i.getContact());
		e.setCreated(Calendar.getInstance());
		e.setDetailAddress(i.getDetailAddress());
		e.setDisplayOrder(1);
		e.setLatitude(i.getLatitude());
		e.setLongitude(i.getLongitude());
		e.setName(i.getName());
		e.setOwner(CurrentUser.getAccount());
		e.setRemarks(i.getRemarks());
		e.setTelephoneNumbersAsArray(i.getTelephoneNumbers());

		addressBookService.save(e);

		return ApiResponse.succeed();
	}

	@RequestMapping("/update")
	public ApiResponse update(@RequestBody() AddressBookEntryInfo i) {
		AddressBookEntry e = getAddressBookEntryForCurrentUser(i.getId());
		if (e == null) {
			return ApiResponse.fail("-10007", "找不到要修改的地址，它可能已被删除。");
		}

		e.setAddress(i.getAddress());
		e.setCity(i.getCity());
		e.setContact(i.getContact());
		e.setCreated(Calendar.getInstance());
		e.setDetailAddress(i.getDetailAddress());
		e.setDisplayOrder(1);
		e.setLatitude(i.getLatitude());
		e.setLongitude(i.getLongitude());
		e.setName(i.getName());
		e.setRemarks(i.getRemarks());
		e.setTelephoneNumbersAsArray(i.getTelephoneNumbers());

		e = addressBookService.save(e);

		return ApiResponse.succeed();
	}

	@RequestMapping("/destroy")
	public ApiResponse destroy(@RequestBody() Long id) {
		AddressBookEntry e = getAddressBookEntryForCurrentUser(id);
		if (e == null) {
			return ApiResponse.fail("-10007", "找不到要修改的地址，它可能已被删除。");
		}

		addressBookService.delete(e);

		return ApiResponse.succeed();
	}

	private AddressBookEntry getAddressBookEntryForCurrentUser(Long id) {
		AddressBookEntry e = addressBookService.findOne(id);
		if (e != null) {
			if (!e.getOwner().getId().equals(CurrentUser.getAccountId())) {
				e = null;
			}
		}
		return e;
	}
}
