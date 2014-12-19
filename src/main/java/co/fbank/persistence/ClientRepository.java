package co.fbank.persistence;

import org.springframework.data.repository.CrudRepository;

import co.fbank.domain.Client;

/**
 * A Repository for the entity Client is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author Felipe Triana Castañeda
 * @version 1.0
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
	
}
