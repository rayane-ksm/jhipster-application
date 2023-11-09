package fr.it_akademy.football.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Joueur.
 */
@Entity
@Table(name = "joueur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "poste")
    private String poste;

    @Column(name = "numero_maillot")
    private Long numeroMaillot;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "date_naissance")
    private Instant dateNaissance;

    @Column(name = "prix_transfer")
    private Double prixTransfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "entraineur", "stade", "joueurs" }, allowSetters = true)
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Joueur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Joueur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoste() {
        return this.poste;
    }

    public Joueur poste(String poste) {
        this.setPoste(poste);
        return this;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Long getNumeroMaillot() {
        return this.numeroMaillot;
    }

    public Joueur numeroMaillot(Long numeroMaillot) {
        this.setNumeroMaillot(numeroMaillot);
        return this;
    }

    public void setNumeroMaillot(Long numeroMaillot) {
        this.numeroMaillot = numeroMaillot;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Joueur telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Instant getDateNaissance() {
        return this.dateNaissance;
    }

    public Joueur dateNaissance(Instant dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Double getPrixTransfer() {
        return this.prixTransfer;
    }

    public Joueur prixTransfer(Double prixTransfer) {
        this.setPrixTransfer(prixTransfer);
        return this;
    }

    public void setPrixTransfer(Double prixTransfer) {
        this.prixTransfer = prixTransfer;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Joueur equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Joueur)) {
            return false;
        }
        return getId() != null && getId().equals(((Joueur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Joueur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", poste='" + getPoste() + "'" +
            ", numeroMaillot=" + getNumeroMaillot() +
            ", telephone='" + getTelephone() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", prixTransfer=" + getPrixTransfer() +
            "}";
    }
}
