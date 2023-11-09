package fr.it_akademy.football.web.rest;

import fr.it_akademy.football.repository.JoueurRepository;
import fr.it_akademy.football.service.JoueurService;
import fr.it_akademy.football.service.dto.JoueurDTO;
import fr.it_akademy.football.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.football.domain.Joueur}.
 */
@RestController
@RequestMapping("/api/joueurs")
public class JoueurResource {

    private final Logger log = LoggerFactory.getLogger(JoueurResource.class);

    private static final String ENTITY_NAME = "joueur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JoueurService joueurService;

    private final JoueurRepository joueurRepository;

    public JoueurResource(JoueurService joueurService, JoueurRepository joueurRepository) {
        this.joueurService = joueurService;
        this.joueurRepository = joueurRepository;
    }

    /**
     * {@code POST  /joueurs} : Create a new joueur.
     *
     * @param joueurDTO the joueurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new joueurDTO, or with status {@code 400 (Bad Request)} if the joueur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JoueurDTO> createJoueur(@RequestBody JoueurDTO joueurDTO) throws URISyntaxException {
        log.debug("REST request to save Joueur : {}", joueurDTO);
        if (joueurDTO.getId() != null) {
            throw new BadRequestAlertException("A new joueur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JoueurDTO result = joueurService.save(joueurDTO);
        return ResponseEntity
            .created(new URI("/api/joueurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /joueurs/:id} : Updates an existing joueur.
     *
     * @param id the id of the joueurDTO to save.
     * @param joueurDTO the joueurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated joueurDTO,
     * or with status {@code 400 (Bad Request)} if the joueurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the joueurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoueurDTO> updateJoueur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JoueurDTO joueurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Joueur : {}, {}", id, joueurDTO);
        if (joueurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, joueurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!joueurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JoueurDTO result = joueurService.update(joueurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, joueurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /joueurs/:id} : Partial updates given fields of an existing joueur, field will ignore if it is null
     *
     * @param id the id of the joueurDTO to save.
     * @param joueurDTO the joueurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated joueurDTO,
     * or with status {@code 400 (Bad Request)} if the joueurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the joueurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the joueurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JoueurDTO> partialUpdateJoueur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JoueurDTO joueurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Joueur partially : {}, {}", id, joueurDTO);
        if (joueurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, joueurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!joueurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JoueurDTO> result = joueurService.partialUpdate(joueurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, joueurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /joueurs} : get all the joueurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of joueurs in body.
     */
    @GetMapping("")
    public List<JoueurDTO> getAllJoueurs() {
        log.debug("REST request to get all Joueurs");
        return joueurService.findAll();
    }

    /**
     * {@code GET  /joueurs/:id} : get the "id" joueur.
     *
     * @param id the id of the joueurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the joueurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoueurDTO> getJoueur(@PathVariable Long id) {
        log.debug("REST request to get Joueur : {}", id);
        Optional<JoueurDTO> joueurDTO = joueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(joueurDTO);
    }

    /**
     * {@code DELETE  /joueurs/:id} : delete the "id" joueur.
     *
     * @param id the id of the joueurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoueur(@PathVariable Long id) {
        log.debug("REST request to delete Joueur : {}", id);
        joueurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
