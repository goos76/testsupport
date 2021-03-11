package nl.cz.testsupport.service.impl;

import nl.cz.testsupport.service.RecordService;
import nl.cz.testsupport.domain.Record;
import nl.cz.testsupport.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Record}.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record save(Record record) {
        log.debug("Request to save Record : {}", record);
        return recordRepository.save(record);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Record> findAll(Pageable pageable) {
        log.debug("Request to get all Records");
        return recordRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Record> findOne(Long id) {
        log.debug("Request to get Record : {}", id);
        return recordRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.deleteById(id);
    }
}
