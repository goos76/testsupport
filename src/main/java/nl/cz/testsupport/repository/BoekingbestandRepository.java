package nl.cz.testsupport.repository;

import nl.cz.testsupport.domain.Boekingbestand;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Boekingbestand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoekingbestandRepository extends JpaRepository<Boekingbestand, Long> {
}
