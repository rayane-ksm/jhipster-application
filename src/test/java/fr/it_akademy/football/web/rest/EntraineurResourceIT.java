package fr.it_akademy.football.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.football.IntegrationTest;
import fr.it_akademy.football.domain.Entraineur;
import fr.it_akademy.football.repository.EntraineurRepository;
import fr.it_akademy.football.service.dto.EntraineurDTO;
import fr.it_akademy.football.service.mapper.EntraineurMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link EntraineurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntraineurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Long DEFAULT_NUM_IDENT = 1L;
    private static final Long UPDATED_NUM_IDENT = 2L;

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ANCIENNE_EQUIPE = "AAAAAAAAAA";
    private static final String UPDATED_ANCIENNE_EQUIPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entraineurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntraineurRepository entraineurRepository;

    @Autowired
    private EntraineurMapper entraineurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntraineurMockMvc;

    private Entraineur entraineur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entraineur createEntity(EntityManager em) {
        Entraineur entraineur = new Entraineur()
            .nom(DEFAULT_NOM)
            .numIdent(DEFAULT_NUM_IDENT)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .ancienneEquipe(DEFAULT_ANCIENNE_EQUIPE);
        return entraineur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entraineur createUpdatedEntity(EntityManager em) {
        Entraineur entraineur = new Entraineur()
            .nom(UPDATED_NOM)
            .numIdent(UPDATED_NUM_IDENT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .ancienneEquipe(UPDATED_ANCIENNE_EQUIPE);
        return entraineur;
    }

    @BeforeEach
    public void initTest() {
        entraineur = createEntity(em);
    }

    @Test
    @Transactional
    void createEntraineur() throws Exception {
        int databaseSizeBeforeCreate = entraineurRepository.findAll().size();
        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);
        restEntraineurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isCreated());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeCreate + 1);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntraineur.getNumIdent()).isEqualTo(DEFAULT_NUM_IDENT);
        assertThat(testEntraineur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEntraineur.getAncienneEquipe()).isEqualTo(DEFAULT_ANCIENNE_EQUIPE);
    }

    @Test
    @Transactional
    void createEntraineurWithExistingId() throws Exception {
        // Create the Entraineur with an existing ID
        entraineur.setId(1L);
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        int databaseSizeBeforeCreate = entraineurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntraineurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntraineurs() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        // Get all the entraineurList
        restEntraineurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entraineur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].numIdent").value(hasItem(DEFAULT_NUM_IDENT.intValue())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].ancienneEquipe").value(hasItem(DEFAULT_ANCIENNE_EQUIPE)));
    }

    @Test
    @Transactional
    void getEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        // Get the entraineur
        restEntraineurMockMvc
            .perform(get(ENTITY_API_URL_ID, entraineur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entraineur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.numIdent").value(DEFAULT_NUM_IDENT.intValue()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.ancienneEquipe").value(DEFAULT_ANCIENNE_EQUIPE));
    }

    @Test
    @Transactional
    void getNonExistingEntraineur() throws Exception {
        // Get the entraineur
        restEntraineurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();

        // Update the entraineur
        Entraineur updatedEntraineur = entraineurRepository.findById(entraineur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntraineur are not directly saved in db
        em.detach(updatedEntraineur);
        updatedEntraineur
            .nom(UPDATED_NOM)
            .numIdent(UPDATED_NUM_IDENT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .ancienneEquipe(UPDATED_ANCIENNE_EQUIPE);
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(updatedEntraineur);

        restEntraineurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entraineurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntraineur.getNumIdent()).isEqualTo(UPDATED_NUM_IDENT);
        assertThat(testEntraineur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEntraineur.getAncienneEquipe()).isEqualTo(UPDATED_ANCIENNE_EQUIPE);
    }

    @Test
    @Transactional
    void putNonExistingEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entraineurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entraineurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntraineurWithPatch() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();

        // Update the entraineur using partial update
        Entraineur partialUpdatedEntraineur = new Entraineur();
        partialUpdatedEntraineur.setId(entraineur.getId());

        partialUpdatedEntraineur.nom(UPDATED_NOM).ancienneEquipe(UPDATED_ANCIENNE_EQUIPE);

        restEntraineurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntraineur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntraineur))
            )
            .andExpect(status().isOk());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntraineur.getNumIdent()).isEqualTo(DEFAULT_NUM_IDENT);
        assertThat(testEntraineur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEntraineur.getAncienneEquipe()).isEqualTo(UPDATED_ANCIENNE_EQUIPE);
    }

    @Test
    @Transactional
    void fullUpdateEntraineurWithPatch() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();

        // Update the entraineur using partial update
        Entraineur partialUpdatedEntraineur = new Entraineur();
        partialUpdatedEntraineur.setId(entraineur.getId());

        partialUpdatedEntraineur
            .nom(UPDATED_NOM)
            .numIdent(UPDATED_NUM_IDENT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .ancienneEquipe(UPDATED_ANCIENNE_EQUIPE);

        restEntraineurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntraineur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntraineur))
            )
            .andExpect(status().isOk());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
        Entraineur testEntraineur = entraineurList.get(entraineurList.size() - 1);
        assertThat(testEntraineur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntraineur.getNumIdent()).isEqualTo(UPDATED_NUM_IDENT);
        assertThat(testEntraineur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEntraineur.getAncienneEquipe()).isEqualTo(UPDATED_ANCIENNE_EQUIPE);
    }

    @Test
    @Transactional
    void patchNonExistingEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entraineurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntraineur() throws Exception {
        int databaseSizeBeforeUpdate = entraineurRepository.findAll().size();
        entraineur.setId(longCount.incrementAndGet());

        // Create the Entraineur
        EntraineurDTO entraineurDTO = entraineurMapper.toDto(entraineur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntraineurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entraineurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entraineur in the database
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntraineur() throws Exception {
        // Initialize the database
        entraineurRepository.saveAndFlush(entraineur);

        int databaseSizeBeforeDelete = entraineurRepository.findAll().size();

        // Delete the entraineur
        restEntraineurMockMvc
            .perform(delete(ENTITY_API_URL_ID, entraineur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entraineur> entraineurList = entraineurRepository.findAll();
        assertThat(entraineurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
