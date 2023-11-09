package fr.it_akademy.football.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "pays")
    private String pays;

    @Column(name = "nb_joueurs")
    private Long nbJoueurs;

    @Column(name = "classement")
    private Long classement;

    @JsonIgnoreProperties(value = { "equipe" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Entraineur entraineur;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "equipe" }, allowSetters = true)
    private Set<Joueur> joueurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Equipe nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return this.pays;
    }

    public Equipe pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Long getNbJoueurs() {
        return this.nbJoueurs;
    }

    public Equipe nbJoueurs(Long nbJoueurs) {
        this.setNbJoueurs(nbJoueurs);
        return this;
    }

    public void setNbJoueurs(Long nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public Long getClassement() {
        return this.classement;
    }

    public Equipe classement(Long classement) {
        this.setClassement(classement);
        return this;
    }

    public void setClassement(Long classement) {
        this.classement = classement;
    }

    public Entraineur getEntraineur() {
        return this.entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }

    public Equipe entraineur(Entraineur entraineur) {
        this.setEntraineur(entraineur);
        return this;
    }

    public Set<Joueur> getJoueurs() {
        return this.joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        if (this.joueurs != null) {
            this.joueurs.forEach(i -> i.setEquipe(null));
        }
        if (joueurs != null) {
            joueurs.forEach(i -> i.setEquipe(this));
        }
        this.joueurs = joueurs;
    }

    public Equipe joueurs(Set<Joueur> joueurs) {
        this.setJoueurs(joueurs);
        return this;
    }

    public Equipe addJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
        joueur.setEquipe(this);
        return this;
    }

    public Equipe removeJoueur(Joueur joueur) {
        this.joueurs.remove(joueur);
        joueur.setEquipe(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pays='" + getPays() + "'" +
            ", nbJoueurs=" + getNbJoueurs() +
            ", classement=" + getClassement() +
            "}";
    }
}
