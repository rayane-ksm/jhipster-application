package fr.it_akademy.football.web.rest;

import fr.it_akademy.football.repository.StadeRepository;
import fr.it_akademy.football.service.StadeService;
import fr.it_akademy.football.service.dto.StadeDTO;
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
 * REST controller for managing {@link fr.it_akademy.football.domain.Stade}.
 */
@RestController
@RequestMapping("/api/stades")
public class StadeResource {

    private final Logger log = LoggerFactory.getLogger(StadeResource.class);

    private static final String ENTITY_NAME = "stade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StadeService stadeService;

    private final StadeRepository stadeRepository;

    public StadeResource(StadeService stadeService, StadeRepository stadeRepository) {
        this.stadeService = stadeService;
        this.stadeRepository = stadeRepository;
    }

    /**
     * {@code POST  /stades} : Create a new stade.
     *
     * @param stadeDTO the stadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stadeDTO, or with status {@code 400 (Bad Request)} if the stade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StadeDTO> createStade(@RequestBody StadeDTO stadeDTO) throws URISyntaxException {
        log.debug("REST request to save Stade : {}", stadeDTO);
        if (stadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StadeDTO result = stadeService.save(stadeDTO);
        return ResponseEntity
            .created(new URI("/api/stades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stades/:id} : Updates an existing stade.
     *
     * @param id the id of the stadeDTO to save.
     * @param stadeDTO the stadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadeDTO,
     * or with status {@code 400 (Bad Request)} if the stadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StadeDTO> updateStade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StadeDTO stadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stade : {}, {}", id, stadeDTO);
        if (stadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StadeDTO result = stadeService.update(stadeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stades/:id} : Partial updates given fields of an existing stade, field will ignore if it is null
     *
     * @param id the id of the stadeDTO to save.
     * @param stadeDTO the stadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadeDTO,
     * or with status {@code 400 (Bad Request)} if the stadeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stadeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StadeDTO> partialUpdateStade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StadeDTO stadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stade partially : {}, {}", id, stadeDTO);
        if (stadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StadeDTO> result = stadeService.partialUpdate(stadeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stadeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stades} : get all the stades.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stades in body.
     */
    @GetMapping("")
    public List<StadeDTO> getAllStades(@RequestParam(required = false) String filter) {
        if ("equipe-is-null".equals(filter)) {
            log.debug("REST request to get all Stades where equipe is null");
            return stadeService.findAllWhereEquipeIsNull();
        }
        log.debug("REST request to get all Stades");
        return stadeService.findAll();
    }

    /**
     * {@code GET  /stades/:id} : get the "id" stade.
     *
     * @param id the id of the stadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StadeDTO> getStade(@PathVariable Long id) {
        log.debug("REST request to get Stade : {}", id);
        Optional<StadeDTO> stadeDTO = stadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stadeDTO);
    }

    /**
     * {@code DELETE  /stades/:id} : delete the "id" stade.
     *
     * @param id the id of the stadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStade(@PathVariable Long id) {
        log.debug("REST request to delete Stade : {}", id);
        stadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
