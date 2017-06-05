package tztexpress.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tztexpress.models.Externalcourier;

import java.util.List;

public interface ExternalCourierRepository extends CrudRepository<Externalcourier, Long> {
    @Query("select ec from Externalcourier ec where ec.type = ?1")
    List<Externalcourier> findExternalCourierByType (String type);
}

