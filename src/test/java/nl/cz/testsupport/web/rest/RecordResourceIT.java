package nl.cz.testsupport.web.rest;

import nl.cz.testsupport.TestsupportApp;
import nl.cz.testsupport.domain.Record;
import nl.cz.testsupport.repository.RecordRepository;
import nl.cz.testsupport.service.RecordService;

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

/**
 * Integration tests for the {@link RecordResource} REST controller.
 */
@SpringBootTest(classes = TestsupportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecordResourceIT {

    private static final String DEFAULT_DEBITEUR = "AAAAAAAAAA";
    private static final String UPDATED_DEBITEUR = "BBBBBBBBBB";

    private static final String DEFAULT_VEROORZAKER = "AAAAAAAAAA";
    private static final String UPDATED_VEROORZAKER = "BBBBBBBBBB";

    private static final String DEFAULT_OVEREENKOMST = "AAAAAAAAAA";
    private static final String UPDATED_OVEREENKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_DATUM_INGANG = "AAAAAAAAAA";
    private static final String UPDATED_DATUM_INGANG = "BBBBBBBBBB";

    private static final String DEFAULT_DATUM_EINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUM_EINDE = "BBBBBBBBBB";

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private RecordService recordService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecordMockMvc;

    private Record record;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Record createEntity(EntityManager em) {
        Record record = new Record()
            .debiteur(DEFAULT_DEBITEUR)
            .veroorzaker(DEFAULT_VEROORZAKER)
            .overeenkomst(DEFAULT_OVEREENKOMST)
            .datumIngang(DEFAULT_DATUM_INGANG)
            .datumEinde(DEFAULT_DATUM_EINDE);
        return record;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Record createUpdatedEntity(EntityManager em) {
        Record record = new Record()
            .debiteur(UPDATED_DEBITEUR)
            .veroorzaker(UPDATED_VEROORZAKER)
            .overeenkomst(UPDATED_OVEREENKOMST)
            .datumIngang(UPDATED_DATUM_INGANG)
            .datumEinde(UPDATED_DATUM_EINDE);
        return record;
    }

    @BeforeEach
    public void initTest() {
        record = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecord() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();
        // Create the Record
        restRecordMockMvc.perform(post("/api/records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate + 1);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getDebiteur()).isEqualTo(DEFAULT_DEBITEUR);
        assertThat(testRecord.getVeroorzaker()).isEqualTo(DEFAULT_VEROORZAKER);
        assertThat(testRecord.getOvereenkomst()).isEqualTo(DEFAULT_OVEREENKOMST);
        assertThat(testRecord.getDatumIngang()).isEqualTo(DEFAULT_DATUM_INGANG);
        assertThat(testRecord.getDatumEinde()).isEqualTo(DEFAULT_DATUM_EINDE);
    }

    @Test
    @Transactional
    public void createRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record with an existing ID
        record.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordMockMvc.perform(post("/api/records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecords() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get all the recordList
        restRecordMockMvc.perform(get("/api/records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(record.getId().intValue())))
            .andExpect(jsonPath("$.[*].debiteur").value(hasItem(DEFAULT_DEBITEUR)))
            .andExpect(jsonPath("$.[*].veroorzaker").value(hasItem(DEFAULT_VEROORZAKER)))
            .andExpect(jsonPath("$.[*].overeenkomst").value(hasItem(DEFAULT_OVEREENKOMST)))
            .andExpect(jsonPath("$.[*].datumIngang").value(hasItem(DEFAULT_DATUM_INGANG)))
            .andExpect(jsonPath("$.[*].datumEinde").value(hasItem(DEFAULT_DATUM_EINDE)));
    }
    
    @Test
    @Transactional
    public void getRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", record.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(record.getId().intValue()))
            .andExpect(jsonPath("$.debiteur").value(DEFAULT_DEBITEUR))
            .andExpect(jsonPath("$.veroorzaker").value(DEFAULT_VEROORZAKER))
            .andExpect(jsonPath("$.overeenkomst").value(DEFAULT_OVEREENKOMST))
            .andExpect(jsonPath("$.datumIngang").value(DEFAULT_DATUM_INGANG))
            .andExpect(jsonPath("$.datumEinde").value(DEFAULT_DATUM_EINDE));
    }
    @Test
    @Transactional
    public void getNonExistingRecord() throws Exception {
        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecord() throws Exception {
        // Initialize the database
        recordService.save(record);

        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Update the record
        Record updatedRecord = recordRepository.findById(record.getId()).get();
        // Disconnect from session so that the updates on updatedRecord are not directly saved in db
        em.detach(updatedRecord);
        updatedRecord
            .debiteur(UPDATED_DEBITEUR)
            .veroorzaker(UPDATED_VEROORZAKER)
            .overeenkomst(UPDATED_OVEREENKOMST)
            .datumIngang(UPDATED_DATUM_INGANG)
            .datumEinde(UPDATED_DATUM_EINDE);

        restRecordMockMvc.perform(put("/api/records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecord)))
            .andExpect(status().isOk());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getDebiteur()).isEqualTo(UPDATED_DEBITEUR);
        assertThat(testRecord.getVeroorzaker()).isEqualTo(UPDATED_VEROORZAKER);
        assertThat(testRecord.getOvereenkomst()).isEqualTo(UPDATED_OVEREENKOMST);
        assertThat(testRecord.getDatumIngang()).isEqualTo(UPDATED_DATUM_INGANG);
        assertThat(testRecord.getDatumEinde()).isEqualTo(UPDATED_DATUM_EINDE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecord() throws Exception {
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordMockMvc.perform(put("/api/records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(record)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecord() throws Exception {
        // Initialize the database
        recordService.save(record);

        int databaseSizeBeforeDelete = recordRepository.findAll().size();

        // Delete the record
        restRecordMockMvc.perform(delete("/api/records/{id}", record.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
