package fr.it_akademy.football.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.football.domain.Stade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StadeDTO implements Serializable {

    private Long id;

    private String nom;

    private String lieu;

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

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StadeDTO)) {
            return false;
        }

        StadeDTO stadeDTO = (StadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StadeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lieu='" + getLieu() + "'" +
            "}";
    }
}
