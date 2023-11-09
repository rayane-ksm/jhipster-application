package fr.it_akademy.football.web.rest;

import fr.it_akademy.football.repository.EntraineurRepository;
import fr.it_akademy.football.service.EntraineurService;
import fr.it_akademy.football.service.dto.EntraineurDTO;
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
 * REST controller for managing {@link fr.it_akademy.football.domain.Entraineur}.
 */
@RestController
@RequestMapping("/api/entraineurs")
public class EntraineurResource {

    private final Logger log = LoggerFactory.getLogger(EntraineurResource.class);

    private static final String ENTITY_NAME = "entraineur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntraineurService entraineurService;

    private final EntraineurRepository entraineurRepository;

    public EntraineurResource(EntraineurService entraineurService, EntraineurRepository entraineurRepository) {
        this.entraineurService = entraineurService;
        this.entraineurRepository = entraineurRepository;
    }

    /**
     * {@code POST  /entraineurs} : Create a new entraineur.
     *
     * @param entraineurDTO the entraineurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entraineurDTO, or with status {@code 400 (Bad Request)} if the entraineur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntraineurDTO> createEntraineur(@RequestBody EntraineurDTO entraineurDTO) throws URISyntaxException {
        log.debug("REST request to save Entraineur : {}", entraineurDTO);
        if (entraineurDTO.getId() != null) {
            throw new BadRequestAlertException("A new entraineur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntraineurDTO result = entraineurService.save(entraineurDTO);
        return ResponseEntity
            .created(new URI("/api/entraineurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entraineurs/:id} : Updates an existing entraineur.
     *
     * @param id the id of the entraineurDTO to save.
     * @param entraineurDTO the entraineurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entraineurDTO,
     * or with status {@code 400 (Bad Request)} if the entraineurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entraineurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntraineurDTO> updateEntraineur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntraineurDTO entraineurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entraineur : {}, {}", id, entraineurDTO);
        if (entraineurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entraineurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entraineurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntraineurDTO result = entraineurService.update(entraineurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entraineurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entraineurs/:id} : Partial updates given fields of an existing entraineur, field will ignore if it is null
     *
     * @param id the id of the entraineurDTO to save.
     * @param entraineurDTO the entraineurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entraineurDTO,
     * or with status {@code 400 (Bad Request)} if the entraineurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entraineurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entraineurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntraineurDTO> partialUpdateEntraineur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntraineurDTO entraineurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entraineur partially : {}, {}", id, entraineurDTO);
        if (entraineurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entraineurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entraineurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntraineurDTO> result = entraineurService.partialUpdate(entraineurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entraineurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entraineurs} : get all the entraineurs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entraineurs in body.
     */
    @GetMapping("")
    public List<EntraineurDTO> getAllEntraineurs(@RequestParam(required = false) String filter) {
        if ("equipe-is-null".equals(filter)) {
            log.debug("REST request to get all Entraineurs where equipe is null");
            return entraineurService.findAllWhereEquipeIsNull();
        }
        log.debug("REST request to get all Entraineurs");
        return entraineurService.findAll();
    }

    /**
     * {@code GET  /entraineurs/:id} : get the "id" entraineur.
     *
     * @param id the id of the entraineurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entraineurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntraineurDTO> getEntraineur(@PathVariable Long id) {
        log.debug("REST request to get Entraineur : {}", id);
        Optional<EntraineurDTO> entraineurDTO = entraineurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entraineurDTO);
    }

    /**
     * {@code DELETE  /entraineurs/:id} : delete the "id" entraineur.
     *
     * @param id the id of the entraineurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntraineur(@PathVariable Long id) {
        log.debug("REST request to delete Entraineur : {}", id);
        entraineurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
