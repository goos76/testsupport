package nl.cz.testsupport.web.rest;

import nl.cz.testsupport.TestsupportApp;
import nl.cz.testsupport.domain.Boekingbestand;
import nl.cz.testsupport.repository.BoekingbestandRepository;
import nl.cz.testsupport.service.BoekingbestandService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nl.cz.testsupport.domain.enumeration.Label;
/**
 * Integration tests for the {@link BoekingbestandResource} REST controller.
 */
@SpringBootTest(classes = TestsupportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoekingbestandResourceIT {

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final Label DEFAULT_LABEL = Label.CZ;
    private static final Label UPDATED_LABEL = Label.OHRA;

    @Autowired
    private BoekingbestandRepository boekingbestandRepository;

    @Autowired
    private BoekingbestandService boekingbestandService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoekingbestandMockMvc;

    private Boekingbestand boekingbestand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boekingbestand createEntity(EntityManager em) {
        Boekingbestand boekingbestand = new Boekingbestand()
            .kenmerk(DEFAULT_KENMERK)
            .label(DEFAULT_LABEL);
        return boekingbestand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boekingbestand createUpdatedEntity(EntityManager em) {
        Boekingbestand boekingbestand = new Boekingbestand()
            .kenmerk(UPDATED_KENMERK)
            .label(UPDATED_LABEL);
        return boekingbestand;
    }

    @BeforeEach
    public void initTest() {
        boekingbestand = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoekingbestand() throws Exception {
        int databaseSizeBeforeCreate = boekingbestandRepository.findAll().size();
        // Create the Boekingbestand
        restBoekingbestandMockMvc.perform(post("/api/boekingbestands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boekingbestand)))
            .andExpect(status().isCreated());

        // Validate the Boekingbestand in the database
        List<Boekingbestand> boekingbestandList = boekingbestandRepository.findAll();
        assertThat(boekingbestandList).hasSize(databaseSizeBeforeCreate + 1);
        Boekingbestand testBoekingbestand = boekingbestandList.get(boekingbestandList.size() - 1);
        assertThat(testBoekingbestand.getKenmerk()).isEqualTo(DEFAULT_KENMERK);
        assertThat(testBoekingbestand.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createBoekingbestandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boekingbestandRepository.findAll().size();

        // Create the Boekingbestand with an existing ID
        boekingbestand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoekingbestandMockMvc.perform(post("/api/boekingbestands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boekingbestand)))
            .andExpect(status().isBadRequest());

        // Validate the Boekingbestand in the database
        List<Boekingbestand> boekingbestandList = boekingbestandRepository.findAll();
        assertThat(boekingbestandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBoekingbestands() throws Exception {
        // Initialize the database
        boekingbestandRepository.saveAndFlush(boekingbestand);

        // Get all the boekingbestandList
        restBoekingbestandMockMvc.perform(get("/api/boekingbestands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boekingbestand.getId().intValue())))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    @Transactional
    public void getBoekingbestand() throws Exception {
        // Initialize the database
        boekingbestandRepository.saveAndFlush(boekingbestand);

        // Get the boekingbestand
        restBoekingbestandMockMvc.perform(get("/api/boekingbestands/{id}", boekingbestand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boekingbestand.getId().intValue()))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBoekingbestand() throws Exception {
        // Get the boekingbestand
        restBoekingbestandMockMvc.perform(get("/api/boekingbestands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoekingbestand() throws Exception {
        // Initialize the database
        boekingbestandService.save(boekingbestand);

        int databaseSizeBeforeUpdate = boekingbestandRepository.findAll().size();

        // Update the boekingbestand
        Boekingbestand updatedBoekingbestand = boekingbestandRepository.findById(boekingbestand.getId()).get();
        // Disconnect from session so that the updates on updatedBoekingbestand are not directly saved in db
        em.detach(updatedBoekingbestand);
        updatedBoekingbestand
            .kenmerk(UPDATED_KENMERK)
            .label(UPDATED_LABEL);

        restBoekingbestandMockMvc.perform(put("/api/boekingbestands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoekingbestand)))
            .andExpect(status().isOk());

        // Validate the Boekingbestand in the database
        List<Boekingbestand> boekingbestandList = boekingbestandRepository.findAll();
        assertThat(boekingbestandList).hasSize(databaseSizeBeforeUpdate);
        Boekingbestand testBoekingbestand = boekingbestandList.get(boekingbestandList.size() - 1);
        assertThat(testBoekingbestand.getKenmerk()).isEqualTo(UPDATED_KENMERK);
        assertThat(testBoekingbestand.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingBoekingbestand() throws Exception {
        int databaseSizeBeforeUpdate = boekingbestandRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoekingbestandMockMvc.perform(put("/api/boekingbestands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boekingbestand)))
            .andExpect(status().isBadRequest());

        // Validate the Boekingbestand in the database
        List<Boekingbestand> boekingbestandList = boekingbestandRepository.findAll();
        assertThat(boekingbestandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoekingbestand() throws Exception {
        // Initialize the database
        boekingbestandService.save(boekingbestand);

        int databaseSizeBeforeDelete = boekingbestandRepository.findAll().size();

        // Delete the boekingbestand
        restBoekingbestandMockMvc.perform(delete("/api/boekingbestands/{id}", boekingbestand.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Boekingbestand> boekingbestandList = boekingbestandRepository.findAll();
        assertThat(boekingbestandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
