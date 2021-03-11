package nl.cz.testsupport.service;

import nl.cz.testsupport.domain.Record;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Record}.
 */
public interface RecordService {

    /**
     * Save a record.
     *
     * @param record the entity to save.
     * @return the persisted entity.
     */
    Record save(Record record);

    /**
     * Get all the records.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Record> findAll(Pageable pageable);


    /**
     * Get the "id" record.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Record> findOne(Long id);

    /**
     * Delete the "id" record.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
