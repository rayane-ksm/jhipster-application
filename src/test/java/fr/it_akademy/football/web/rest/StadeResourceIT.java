package fr.it_akademy.football.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.football.IntegrationTest;
import fr.it_akademy.football.domain.Stade;
import fr.it_akademy.football.repository.StadeRepository;
import fr.it_akademy.football.service.dto.StadeDTO;
import fr.it_akademy.football.service.mapper.StadeMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StadeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StadeRepository stadeRepository;

    @Autowired
    private StadeMapper stadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStadeMockMvc;

    private Stade stade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stade createEntity(EntityManager em) {
        Stade stade = new Stade().nom(DEFAULT_NOM).lieu(DEFAULT_LIEU);
        return stade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stade createUpdatedEntity(EntityManager em) {
        Stade stade = new Stade().nom(UPDATED_NOM).lieu(UPDATED_LIEU);
        return stade;
    }

    @BeforeEach
    public void initTest() {
        stade = createEntity(em);
    }

    @Test
    @Transactional
    void createStade() throws Exception {
        int databaseSizeBeforeCreate = stadeRepository.findAll().size();
        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);
        restStadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeCreate + 1);
        Stade testStade = stadeList.get(stadeList.size() - 1);
        assertThat(testStade.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testStade.getLieu()).isEqualTo(DEFAULT_LIEU);
    }

    @Test
    @Transactional
    void createStadeWithExistingId() throws Exception {
        // Create the Stade with an existing ID
        stade.setId(1L);
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        int databaseSizeBeforeCreate = stadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStades() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        // Get all the stadeList
        restStadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU)));
    }

    @Test
    @Transactional
    void getStade() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        // Get the stade
        restStadeMockMvc
            .perform(get(ENTITY_API_URL_ID, stade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stade.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU));
    }

    @Test
    @Transactional
    void getNonExistingStade() throws Exception {
        // Get the stade
        restStadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStade() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();

        // Update the stade
        Stade updatedStade = stadeRepository.findById(stade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStade are not directly saved in db
        em.detach(updatedStade);
        updatedStade.nom(UPDATED_NOM).lieu(UPDATED_LIEU);
        StadeDTO stadeDTO = stadeMapper.toDto(updatedStade);

        restStadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stadeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
        Stade testStade = stadeList.get(stadeList.size() - 1);
        assertThat(testStade.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStade.getLieu()).isEqualTo(UPDATED_LIEU);
    }

    @Test
    @Transactional
    void putNonExistingStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStadeWithPatch() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();

        // Update the stade using partial update
        Stade partialUpdatedStade = new Stade();
        partialUpdatedStade.setId(stade.getId());

        partialUpdatedStade.nom(UPDATED_NOM).lieu(UPDATED_LIEU);

        restStadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStade))
            )
            .andExpect(status().isOk());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
        Stade testStade = stadeList.get(stadeList.size() - 1);
        assertThat(testStade.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStade.getLieu()).isEqualTo(UPDATED_LIEU);
    }

    @Test
    @Transactional
    void fullUpdateStadeWithPatch() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();

        // Update the stade using partial update
        Stade partialUpdatedStade = new Stade();
        partialUpdatedStade.setId(stade.getId());

        partialUpdatedStade.nom(UPDATED_NOM).lieu(UPDATED_LIEU);

        restStadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStade))
            )
            .andExpect(status().isOk());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
        Stade testStade = stadeList.get(stadeList.size() - 1);
        assertThat(testStade.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStade.getLieu()).isEqualTo(UPDATED_LIEU);
    }

    @Test
    @Transactional
    void patchNonExistingStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stadeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStade() throws Exception {
        int databaseSizeBeforeUpdate = stadeRepository.findAll().size();
        stade.setId(longCount.incrementAndGet());

        // Create the Stade
        StadeDTO stadeDTO = stadeMapper.toDto(stade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stade in the database
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStade() throws Exception {
        // Initialize the database
        stadeRepository.saveAndFlush(stade);

        int databaseSizeBeforeDelete = stadeRepository.findAll().size();

        // Delete the stade
        restStadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, stade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stade> stadeList = stadeRepository.findAll();
        assertThat(stadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
