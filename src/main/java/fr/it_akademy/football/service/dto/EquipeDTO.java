package fr.it_akademy.football.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.football.domain.Equipe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipeDTO implements Serializable {

    private Long id;

    private String nom;

    private String pays;

    private Long nbJoueurs;

    private Long classement;

    private EntraineurDTO entraineur;

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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Long getNbJoueurs() {
        return nbJoueurs;
    }

    public void setNbJoueurs(Long nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public Long getClassement() {
        return classement;
    }

    public void setClassement(Long classement) {
        this.classement = classement;
    }

    public EntraineurDTO getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(EntraineurDTO entraineur) {
        this.entraineur = entraineur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipeDTO)) {
            return false;
        }

        EquipeDTO equipeDTO = (EquipeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pays='" + getPays() + "'" +
            ", nbJoueurs=" + getNbJoueurs() +
            ", classement=" + getClassement() +
            ", entraineur=" + getEntraineur() +
            "}";
    }
}
