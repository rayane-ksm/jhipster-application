package fr.it_akademy.football.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.football.domain.Joueur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JoueurDTO implements Serializable {

    private Long id;

    private String nom;

    private String poste;

    private Long numeroMaillot;

    private String telephone;

    private Instant dateNaissance;

    private Double prixTransfer;

    private EquipeDTO equipe;

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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Long getNumeroMaillot() {
        return numeroMaillot;
    }

    public void setNumeroMaillot(Long numeroMaillot) {
        this.numeroMaillot = numeroMaillot;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Double getPrixTransfer() {
        return prixTransfer;
    }

    public void setPrixTransfer(Double prixTransfer) {
        this.prixTransfer = prixTransfer;
    }

    public EquipeDTO getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeDTO equipe) {
        this.equipe = equipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JoueurDTO)) {
            return false;
        }

        JoueurDTO joueurDTO = (JoueurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, joueurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JoueurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", poste='" + getPoste() + "'" +
            ", numeroMaillot=" + getNumeroMaillot() +
            ", telephone='" + getTelephone() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", prixTransfer=" + getPrixTransfer() +
            ", equipe=" + getEquipe() +
            "}";
    }
}
