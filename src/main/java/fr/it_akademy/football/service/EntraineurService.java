package fr.it_akademy.football.service;

import fr.it_akademy.football.service.dto.EntraineurDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.football.domain.Entraineur}.
 */
public interface EntraineurService {
    /**
     * Save a entraineur.
     *
     * @param entraineurDTO the entity to save.
     * @return the persisted entity.
     */
    EntraineurDTO save(EntraineurDTO entraineurDTO);

    /**
     * Updates a entraineur.
     *
     * @param entraineurDTO the entity to update.
     * @return the persisted entity.
     */
    EntraineurDTO update(EntraineurDTO entraineurDTO);

    /**
     * Partially updates a entraineur.
     *
     * @param entraineurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntraineurDTO> partialUpdate(EntraineurDTO entraineurDTO);

    /**
     * Get all the entraineurs.
     *
     * @return the list of entities.
     */
    List<EntraineurDTO> findAll();

    /**
     * Get all the EntraineurDTO where Equipe is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EntraineurDTO> findAllWhereEquipeIsNull();

    /**
     * Get the "id" entraineur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntraineurDTO> findOne(Long id);

    /**
     * Delete the "id" entraineur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
