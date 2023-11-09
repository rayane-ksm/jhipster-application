package fr.it_akademy.football.repository;

import fr.it_akademy.football.domain.Entraineur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entraineur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntraineurRepository extends JpaRepository<Entraineur, Long> {}
