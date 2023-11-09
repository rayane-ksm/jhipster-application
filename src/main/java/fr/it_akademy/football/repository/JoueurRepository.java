package fr.it_akademy.football.repository;

import fr.it_akademy.football.domain.Joueur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Joueur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {}
