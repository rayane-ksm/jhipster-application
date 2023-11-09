package fr.it_akademy.football.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.football.IntegrationTest;
import fr.it_akademy.football.domain.Equipe;
import fr.it_akademy.football.repository.EquipeRepository;
import fr.it_akademy.football.service.dto.EquipeDTO;
import fr.it_akademy.football.service.mapper.EquipeMapper;
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
 * Integration tests for the {@link EquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final Long DEFAULT_NB_JOUEURS = 1L;
    private static final Long UPDATED_NB_JOUEURS = 2L;

    private static final Long DEFAULT_CLASSEMENT = 1L;
    private static final Long UPDATED_CLASSEMENT = 2L;

    private static final String ENTITY_API_URL = "/api/equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeMapper equipeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createEntity(EntityManager em) {
        Equipe equipe = new Equipe().nom(DEFAULT_NOM).pays(DEFAULT_PAYS).nbJoueurs(DEFAULT_NB_JOUEURS).classement(DEFAULT_CLASSEMENT);
        return equipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createUpdatedEntity(EntityManager em) {
        Equipe equipe = new Equipe().nom(UPDATED_NOM).pays(UPDATED_PAYS).nbJoueurs(UPDATED_NB_JOUEURS).classement(UPDATED_CLASSEMENT);
        return equipe;
    }

    @BeforeEach
    public void initTest() {
        equipe = createEntity(em);
    }

    @Test
    @Transactional
    void createEquipe() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();
        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);
        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate + 1);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEquipe.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testEquipe.getNbJoueurs()).isEqualTo(DEFAULT_NB_JOUEURS);
        assertThat(testEquipe.getClassement()).isEqualTo(DEFAULT_CLASSEMENT);
    }

    @Test
    @Transactional
    void createEquipeWithExistingId() throws Exception {
        // Create the Equipe with an existing ID
        equipe.setId(1L);
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEquipes() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].nbJoueurs").value(hasItem(DEFAULT_NB_JOUEURS.intValue())))
            .andExpect(jsonPath("$.[*].classement").value(hasItem(DEFAULT_CLASSEMENT.intValue())));
    }

    @Test
    @Transactional
    void getEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.nbJoueurs").value(DEFAULT_NB_JOUEURS.intValue()))
            .andExpect(jsonPath("$.classement").value(DEFAULT_CLASSEMENT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe.nom(UPDATED_NOM).pays(UPDATED_PAYS).nbJoueurs(UPDATED_NB_JOUEURS).classement(UPDATED_CLASSEMENT);
        EquipeDTO equipeDTO = equipeMapper.toDto(updatedEquipe);

        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEquipe.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testEquipe.getNbJoueurs()).isEqualTo(UPDATED_NB_JOUEURS);
        assertThat(testEquipe.getClassement()).isEqualTo(UPDATED_CLASSEMENT);
    }

    @Test
    @Transactional
    void putNonExistingEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe.nom(UPDATED_NOM).classement(UPDATED_CLASSEMENT);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEquipe.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testEquipe.getNbJoueurs()).isEqualTo(DEFAULT_NB_JOUEURS);
        assertThat(testEquipe.getClassement()).isEqualTo(UPDATED_CLASSEMENT);
    }

    @Test
    @Transactional
    void fullUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe.nom(UPDATED_NOM).pays(UPDATED_PAYS).nbJoueurs(UPDATED_NB_JOUEURS).classement(UPDATED_CLASSEMENT);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEquipe.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testEquipe.getNbJoueurs()).isEqualTo(UPDATED_NB_JOUEURS);
        assertThat(testEquipe.getClassement()).isEqualTo(UPDATED_CLASSEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(equipeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeDelete = equipeRepository.findAll().size();

        // Delete the equipe
        restEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
