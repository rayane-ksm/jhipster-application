package fr.it_akademy.football.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.football.domain.Entraineur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntraineurDTO implements Serializable {

    private Long id;

    private String nom;

    private Long numIdent;

    private Instant dateNaissance;

    private String ancienneEquipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNumIdent() {
        return numIdent;
    }

    public void setNumIdent(Long numIdent) {
        this.numIdent = numIdent;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAncienneEquipe() {
        return ancienneEquipe;
    }

    public void setAncienneEquipe(String ancienneEquipe) {
        this.ancienneEquipe = ancienneEquipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntraineurDTO)) {
            return false;
        }

        EntraineurDTO entraineurDTO = (EntraineurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entraineurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntraineurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", numIdent=" + getNumIdent() +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", ancienneEquipe='" + getAncienneEquipe() + "'" +
            "}";
    }
}
