package co.fbank.persistence;

import org.springframework.data.repository.CrudRepository;

import co.fbank.model.Account;

/**
 * A Repository for the entity Account is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author Felipe Triana Castañeda
 * @version 1.0
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

}
