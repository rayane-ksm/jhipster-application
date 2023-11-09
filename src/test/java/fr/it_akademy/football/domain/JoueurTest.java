package fr.it_akademy.football.domain;

import static fr.it_akademy.football.domain.EquipeTestSamples.*;
import static fr.it_akademy.football.domain.JoueurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JoueurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Joueur.class);
        Joueur joueur1 = getJoueurSample1();
        Joueur joueur2 = new Joueur();
        assertThat(joueur1).isNotEqualTo(joueur2);

        joueur2.setId(joueur1.getId());
        assertThat(joueur1).isEqualTo(joueur2);

        joueur2 = getJoueurSample2();
        assertThat(joueur1).isNotEqualTo(joueur2);
    }

    @Test
    void equipeTest() throws Exception {
        Joueur joueur = getJoueurRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        joueur.setEquipe(equipeBack);
        assertThat(joueur.getEquipe()).isEqualTo(equipeBack);

        joueur.equipe(null);
        assertThat(joueur.getEquipe()).isNull();
    }
}
