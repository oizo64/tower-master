package ppl.pl.tower.domain.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Model.Aircraft;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepo extends JpaRepository<Aircraft, Long>, JpaSpecificationExecutor<Aircraft> {
    @Query(value = "select * from aircraft a where a.model_name like %:modelName% order by :orderBy desc",
            nativeQuery = true)
    List<Aircraft> searchAircraftByModelName(@Param("modelName") String modelName, @Param("orderBy") String orderBy);

    @Query(value = "SELECT * FROM AIRCRAFT a WHERE a.manufacturer like %:manufacturer%",
            nativeQuery = true)
    List<Aircraft> searchAircraftByManufacturer(@Param("manufacturer") String manufacturer);

    @Query(value = "SELECT * FROM AIRCRAFT a WHERE a.engine_type like %:engineType%",
            nativeQuery = true)
    List<Aircraft> searchAircraftByEngineType(@Param("engineType") String engineType);


}
