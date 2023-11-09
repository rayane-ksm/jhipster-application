package fr.it_akademy.football.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.football.IntegrationTest;
import fr.it_akademy.football.domain.Joueur;
import fr.it_akademy.football.repository.JoueurRepository;
import fr.it_akademy.football.service.dto.JoueurDTO;
import fr.it_akademy.football.service.mapper.JoueurMapper;
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
 * Integration tests for the {@link JoueurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JoueurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_POSTE = "AAAAAAAAAA";
    private static final String UPDATED_POSTE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_MAILLOT = 1L;
    private static final Long UPDATED_NUMERO_MAILLOT = 2L;

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRIX_TRANSFER = 1D;
    private static final Double UPDATED_PRIX_TRANSFER = 2D;

    private static final String ENTITY_API_URL = "/api/joueurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JoueurRepository joueurRepository;

    @Autowired
    private JoueurMapper joueurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJoueurMockMvc;

    private Joueur joueur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joueur createEntity(EntityManager em) {
        Joueur joueur = new Joueur()
            .nom(DEFAULT_NOM)
            .poste(DEFAULT_POSTE)
            .numeroMaillot(DEFAULT_NUMERO_MAILLOT)
            .telephone(DEFAULT_TELEPHONE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .prixTransfer(DEFAULT_PRIX_TRANSFER);
        return joueur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joueur createUpdatedEntity(EntityManager em) {
        Joueur joueur = new Joueur()
            .nom(UPDATED_NOM)
            .poste(UPDATED_POSTE)
            .numeroMaillot(UPDATED_NUMERO_MAILLOT)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .prixTransfer(UPDATED_PRIX_TRANSFER);
        return joueur;
    }

    @BeforeEach
    public void initTest() {
        joueur = createEntity(em);
    }

    @Test
    @Transactional
    void createJoueur() throws Exception {
        int databaseSizeBeforeCreate = joueurRepository.findAll().size();
        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);
        restJoueurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isCreated());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeCreate + 1);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
        assertThat(testJoueur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testJoueur.getPoste()).isEqualTo(DEFAULT_POSTE);
        assertThat(testJoueur.getNumeroMaillot()).isEqualTo(DEFAULT_NUMERO_MAILLOT);
        assertThat(testJoueur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testJoueur.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testJoueur.getPrixTransfer()).isEqualTo(DEFAULT_PRIX_TRANSFER);
    }

    @Test
    @Transactional
    void createJoueurWithExistingId() throws Exception {
        // Create the Joueur with an existing ID
        joueur.setId(1L);
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        int databaseSizeBeforeCreate = joueurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJoueurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJoueurs() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        // Get all the joueurList
        restJoueurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(joueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].poste").value(hasItem(DEFAULT_POSTE)))
            .andExpect(jsonPath("$.[*].numeroMaillot").value(hasItem(DEFAULT_NUMERO_MAILLOT.intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].prixTransfer").value(hasItem(DEFAULT_PRIX_TRANSFER.doubleValue())));
    }

    @Test
    @Transactional
    void getJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        // Get the joueur
        restJoueurMockMvc
            .perform(get(ENTITY_API_URL_ID, joueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(joueur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.poste").value(DEFAULT_POSTE))
            .andExpect(jsonPath("$.numeroMaillot").value(DEFAULT_NUMERO_MAILLOT.intValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.prixTransfer").value(DEFAULT_PRIX_TRANSFER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingJoueur() throws Exception {
        // Get the joueur
        restJoueurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();

        // Update the joueur
        Joueur updatedJoueur = joueurRepository.findById(joueur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJoueur are not directly saved in db
        em.detach(updatedJoueur);
        updatedJoueur
            .nom(UPDATED_NOM)
            .poste(UPDATED_POSTE)
            .numeroMaillot(UPDATED_NUMERO_MAILLOT)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .prixTransfer(UPDATED_PRIX_TRANSFER);
        JoueurDTO joueurDTO = joueurMapper.toDto(updatedJoueur);

        restJoueurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, joueurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
        assertThat(testJoueur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testJoueur.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testJoueur.getNumeroMaillot()).isEqualTo(UPDATED_NUMERO_MAILLOT);
        assertThat(testJoueur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testJoueur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testJoueur.getPrixTransfer()).isEqualTo(UPDATED_PRIX_TRANSFER);
    }

    @Test
    @Transactional
    void putNonExistingJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, joueurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJoueurWithPatch() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();

        // Update the joueur using partial update
        Joueur partialUpdatedJoueur = new Joueur();
        partialUpdatedJoueur.setId(joueur.getId());

        partialUpdatedJoueur
            .poste(UPDATED_POSTE)
            .numeroMaillot(UPDATED_NUMERO_MAILLOT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .prixTransfer(UPDATED_PRIX_TRANSFER);

        restJoueurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJoueur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJoueur))
            )
            .andExpect(status().isOk());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
        assertThat(testJoueur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testJoueur.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testJoueur.getNumeroMaillot()).isEqualTo(UPDATED_NUMERO_MAILLOT);
        assertThat(testJoueur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testJoueur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testJoueur.getPrixTransfer()).isEqualTo(UPDATED_PRIX_TRANSFER);
    }

    @Test
    @Transactional
    void fullUpdateJoueurWithPatch() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();

        // Update the joueur using partial update
        Joueur partialUpdatedJoueur = new Joueur();
        partialUpdatedJoueur.setId(joueur.getId());

        partialUpdatedJoueur
            .nom(UPDATED_NOM)
            .poste(UPDATED_POSTE)
            .numeroMaillot(UPDATED_NUMERO_MAILLOT)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .prixTransfer(UPDATED_PRIX_TRANSFER);

        restJoueurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJoueur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJoueur))
            )
            .andExpect(status().isOk());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
        assertThat(testJoueur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testJoueur.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testJoueur.getNumeroMaillot()).isEqualTo(UPDATED_NUMERO_MAILLOT);
        assertThat(testJoueur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testJoueur.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testJoueur.getPrixTransfer()).isEqualTo(UPDATED_PRIX_TRANSFER);
    }

    @Test
    @Transactional
    void patchNonExistingJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, joueurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();
        joueur.setId(longCount.incrementAndGet());

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJoueurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(joueurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeDelete = joueurRepository.findAll().size();

        // Delete the joueur
        restJoueurMockMvc
            .perform(delete(ENTITY_API_URL_ID, joueur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
