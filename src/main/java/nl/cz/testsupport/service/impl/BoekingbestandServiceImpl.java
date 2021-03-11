package nl.cz.testsupport.service.impl;

import nl.cz.testsupport.service.BoekingbestandService;
import nl.cz.testsupport.domain.Boekingbestand;
import nl.cz.testsupport.repository.BoekingbestandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Boekingbestand}.
 */
@Service
@Transactional
public class BoekingbestandServiceImpl implements BoekingbestandService {

    private final Logger log = LoggerFactory.getLogger(BoekingbestandServiceImpl.class);

    private final BoekingbestandRepository boekingbestandRepository;

    public BoekingbestandServiceImpl(BoekingbestandRepository boekingbestandRepository) {
        this.boekingbestandRepository = boekingbestandRepository;
    }

    @Override
    public Boekingbestand save(Boekingbestand boekingbestand) {
        log.debug("Request to save Boekingbestand : {}", boekingbestand);
        return boekingbestandRepository.save(boekingbestand);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Boekingbestand> findAll(Pageable pageable) {
        log.debug("Request to get all Boekingbestands");
        return boekingbestandRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Boekingbestand> findOne(Long id) {
        log.debug("Request to get Boekingbestand : {}", id);
        return boekingbestandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Boekingbestand : {}", id);
        boekingbestandRepository.deleteById(id);
    }
}
