package fr.it_akademy.football.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entraineur.
 */
@Entity
@Table(name = "entraineur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entraineur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "num_ident")
    private Long numIdent;

    @Column(name = "date_naissance")
    private Instant dateNaissance;

    @Column(name = "ancienne_equipe")
    private String ancienneEquipe;

    @JsonIgnoreProperties(value = { "entraineur", "joueurs" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "entraineur")
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entraineur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Entraineur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNumIdent() {
        return this.numIdent;
    }

    public Entraineur numIdent(Long numIdent) {
        this.setNumIdent(numIdent);
        return this;
    }

    public void setNumIdent(Long numIdent) {
        this.numIdent = numIdent;
    }

    public Instant getDateNaissance() {
        return this.dateNaissance;
    }

    public Entraineur dateNaissance(Instant dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAncienneEquipe() {
        return this.ancienneEquipe;
    }

    public Entraineur ancienneEquipe(String ancienneEquipe) {
        this.setAncienneEquipe(ancienneEquipe);
        return this;
    }

    public void setAncienneEquipe(String ancienneEquipe) {
        this.ancienneEquipe = ancienneEquipe;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        if (this.equipe != null) {
            this.equipe.setEntraineur(null);
        }
        if (equipe != null) {
            equipe.setEntraineur(this);
        }
        this.equipe = equipe;
    }

    public Entraineur equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entraineur)) {
            return false;
        }
        return getId() != null && getId().equals(((Entraineur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entraineur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", numIdent=" + getNumIdent() +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", ancienneEquipe='" + getAncienneEquipe() + "'" +
            "}";
    }
}
