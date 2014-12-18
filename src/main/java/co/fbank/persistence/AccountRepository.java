package co.fbank.persistence;

import org.springframework.data.repository.CrudRepository;

import co.fbank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
