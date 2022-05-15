package ppl.pl.tower.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppl.pl.tower.domain.Model.Aircraft;

import java.util.Optional;

@Repository
public interface AircraftRepo extends JpaRepository<Aircraft, Long> {
    Optional<Aircraft> findByManufacturer(String manufacturer);
}
