package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Externalcourier;

import java.util.List;

/**
 * Contains the data to adjust External couriers in the database.
 */
public interface ExternalCourierRepository extends CrudRepository<Externalcourier, Long> {

    /**
     * Gets a list of external couriers with a specific type
     * @param type the type for the query
     * @return the list of external couriers that have the requested type
     */
    @Query("select ec from Externalcourier ec where ec.type = ?1")
    List<Externalcourier> findExternalCourierByType (String type);
}

