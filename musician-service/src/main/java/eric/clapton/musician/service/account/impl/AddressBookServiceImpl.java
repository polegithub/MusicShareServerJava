package eric.clapton.musician.service.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;
import eric.clapton.musician.repository.account.AddressBookRepository;
import eric.clapton.musician.service.account.AddressBookService;

@Service
public class AddressBookServiceImpl extends BaseServiceImpl<AddressBookEntry, AddressBookRepository>
		implements AddressBookService {
	@Override
	@Resource
	protected void baseSetRepository(AddressBookRepository repository) {
		super.setRepository(repository);
	}
}
