package fr.it_akademy.football.service;

import fr.it_akademy.football.service.dto.EquipeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.football.domain.Equipe}.
 */
public interface EquipeService {
    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save.
     * @return the persisted entity.
     */
    EquipeDTO save(EquipeDTO equipeDTO);

    /**
     * Updates a equipe.
     *
     * @param equipeDTO the entity to update.
     * @return the persisted entity.
     */
    EquipeDTO update(EquipeDTO equipeDTO);

    /**
     * Partially updates a equipe.
     *
     * @param equipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EquipeDTO> partialUpdate(EquipeDTO equipeDTO);

    /**
     * Get all the equipes.
     *
     * @return the list of entities.
     */
    List<EquipeDTO> findAll();

    /**
     * Get the "id" equipe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquipeDTO> findOne(Long id);

    /**
     * Delete the "id" equipe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
