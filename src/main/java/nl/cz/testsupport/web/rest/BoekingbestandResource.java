package nl.cz.testsupport.web.rest;

import nl.cz.testsupport.domain.Boekingbestand;
import nl.cz.testsupport.service.BoekingbestandService;
import nl.cz.testsupport.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link nl.cz.testsupport.domain.Boekingbestand}.
 */
@RestController
@RequestMapping("/api")
public class BoekingbestandResource {

    private final Logger log = LoggerFactory.getLogger(BoekingbestandResource.class);

    private static final String ENTITY_NAME = "boekingbestand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoekingbestandService boekingbestandService;

    public BoekingbestandResource(BoekingbestandService boekingbestandService) {
        this.boekingbestandService = boekingbestandService;
    }

    /**
     * {@code POST  /boekingbestands} : Create a new boekingbestand.
     *
     * @param boekingbestand the boekingbestand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boekingbestand, or with status {@code 400 (Bad Request)} if the boekingbestand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boekingbestands")
    public ResponseEntity<Boekingbestand> createBoekingbestand(@RequestBody Boekingbestand boekingbestand) throws URISyntaxException {
        log.debug("REST request to save Boekingbestand : {}", boekingbestand);
        if (boekingbestand.getId() != null) {
            throw new BadRequestAlertException("A new boekingbestand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Boekingbestand result = boekingbestandService.save(boekingbestand);
        return ResponseEntity.created(new URI("/api/boekingbestands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boekingbestands} : Updates an existing boekingbestand.
     *
     * @param boekingbestand the boekingbestand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boekingbestand,
     * or with status {@code 400 (Bad Request)} if the boekingbestand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boekingbestand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boekingbestands")
    public ResponseEntity<Boekingbestand> updateBoekingbestand(@RequestBody Boekingbestand boekingbestand) throws URISyntaxException {
        log.debug("REST request to update Boekingbestand : {}", boekingbestand);
        if (boekingbestand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boekingbestand result = boekingbestandService.save(boekingbestand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boekingbestand.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boekingbestands} : get all the boekingbestands.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boekingbestands in body.
     */
    @GetMapping("/boekingbestands")
    public ResponseEntity<List<Boekingbestand>> getAllBoekingbestands(Pageable pageable) {
        log.debug("REST request to get a page of Boekingbestands");
        Page<Boekingbestand> page = boekingbestandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boekingbestands/:id} : get the "id" boekingbestand.
     *
     * @param id the id of the boekingbestand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boekingbestand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boekingbestands/{id}")
    public ResponseEntity<Boekingbestand> getBoekingbestand(@PathVariable Long id) {
        log.debug("REST request to get Boekingbestand : {}", id);
        Optional<Boekingbestand> boekingbestand = boekingbestandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boekingbestand);
    }

    /**
     * {@code DELETE  /boekingbestands/:id} : delete the "id" boekingbestand.
     *
     * @param id the id of the boekingbestand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boekingbestands/{id}")
    public ResponseEntity<Void> deleteBoekingbestand(@PathVariable Long id) {
        log.debug("REST request to delete Boekingbestand : {}", id);
        boekingbestandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
