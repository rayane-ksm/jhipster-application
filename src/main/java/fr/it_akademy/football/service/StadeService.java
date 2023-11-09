package fr.it_akademy.football.service;

import fr.it_akademy.football.service.dto.StadeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.football.domain.Stade}.
 */
public interface StadeService {
    /**
     * Save a stade.
     *
     * @param stadeDTO the entity to save.
     * @return the persisted entity.
     */
    StadeDTO save(StadeDTO stadeDTO);

    /**
     * Updates a stade.
     *
     * @param stadeDTO the entity to update.
     * @return the persisted entity.
     */
    StadeDTO update(StadeDTO stadeDTO);

    /**
     * Partially updates a stade.
     *
     * @param stadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StadeDTO> partialUpdate(StadeDTO stadeDTO);

    /**
     * Get all the stades.
     *
     * @return the list of entities.
     */
    List<StadeDTO> findAll();

    /**
     * Get all the StadeDTO where Equipe is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<StadeDTO> findAllWhereEquipeIsNull();

    /**
     * Get the "id" stade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StadeDTO> findOne(Long id);

    /**
     * Delete the "id" stade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
