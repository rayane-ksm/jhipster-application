package fr.it_akademy.football.repository;

import fr.it_akademy.football.domain.Equipe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {}
