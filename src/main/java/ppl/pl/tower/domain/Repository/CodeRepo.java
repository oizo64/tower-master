package ppl.pl.tower.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppl.pl.tower.domain.Model.Code;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {
}
