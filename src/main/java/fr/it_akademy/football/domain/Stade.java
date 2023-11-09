package fr.it_akademy.football.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Stade.
 */
@Entity
@Table(name = "stade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "lieu")
    private String lieu;

    @JsonIgnoreProperties(value = { "entraineur", "stade", "joueurs" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stade")
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Stade nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return this.lieu;
    }

    public Stade lieu(String lieu) {
        this.setLieu(lieu);
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        if (this.equipe != null) {
            this.equipe.setStade(null);
        }
        if (equipe != null) {
            equipe.setStade(this);
        }
        this.equipe = equipe;
    }

    public Stade equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stade)) {
            return false;
        }
        return getId() != null && getId().equals(((Stade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stade{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lieu='" + getLieu() + "'" +
            "}";
    }
}
