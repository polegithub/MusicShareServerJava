package eric.clapton.musician.repository.account;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.account.AddressBookEntry;

public interface AddressBookRepository extends BaseRepository<AddressBookEntry, Long> {
}
