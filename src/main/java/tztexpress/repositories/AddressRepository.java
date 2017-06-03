package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
    @Query("select a from Address a where a.address1 = ?1 and a.address2 = ?2 and a.zipcode = ?3 and a.city = ?4")
    Address findAddress(String address1, String address2, String zipcode, String city);
}