package nl.cz.testsupport.service;

import nl.cz.testsupport.domain.Boekingbestand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Boekingbestand}.
 */
public interface BoekingbestandService {

    /**
     * Save a boekingbestand.
     *
     * @param boekingbestand the entity to save.
     * @return the persisted entity.
     */
    Boekingbestand save(Boekingbestand boekingbestand);

    /**
     * Get all the boekingbestands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Boekingbestand> findAll(Pageable pageable);


    /**
     * Get the "id" boekingbestand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Boekingbestand> findOne(Long id);

    /**
     * Delete the "id" boekingbestand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
