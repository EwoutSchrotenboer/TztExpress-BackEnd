package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Address;

/**
 * Contains the data to adjust Addresses in the database.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
    /**
     * This query is added to select an address by its contens rather than its Id
     * @param address1 the address1
     * @param address2 the address2
     * @param zipcode the zipcode
     * @param city the city
     * @return the address model
     */
    @Query("select a from Address a where a.address1 = ?1 and a.address2 = ?2 and a.zipcode = ?3 and a.city = ?4")
    Address findAddress(String address1, String address2, String zipcode, String city);
}