package fr.it_akademy.football.domain;

import static fr.it_akademy.football.domain.EntraineurTestSamples.*;
import static fr.it_akademy.football.domain.EquipeTestSamples.*;
import static fr.it_akademy.football.domain.JoueurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipe.class);
        Equipe equipe1 = getEquipeSample1();
        Equipe equipe2 = new Equipe();
        assertThat(equipe1).isNotEqualTo(equipe2);

        equipe2.setId(equipe1.getId());
        assertThat(equipe1).isEqualTo(equipe2);

        equipe2 = getEquipeSample2();
        assertThat(equipe1).isNotEqualTo(equipe2);
    }

    @Test
    void entraineurTest() throws Exception {
        Equipe equipe = getEquipeRandomSampleGenerator();
        Entraineur entraineurBack = getEntraineurRandomSampleGenerator();

        equipe.setEntraineur(entraineurBack);
        assertThat(equipe.getEntraineur()).isEqualTo(entraineurBack);

        equipe.entraineur(null);
        assertThat(equipe.getEntraineur()).isNull();
    }

    @Test
    void joueurTest() throws Exception {
        Equipe equipe = getEquipeRandomSampleGenerator();
        Joueur joueurBack = getJoueurRandomSampleGenerator();

        equipe.addJoueur(joueurBack);
        assertThat(equipe.getJoueurs()).containsOnly(joueurBack);
        assertThat(joueurBack.getEquipe()).isEqualTo(equipe);

        equipe.removeJoueur(joueurBack);
        assertThat(equipe.getJoueurs()).doesNotContain(joueurBack);
        assertThat(joueurBack.getEquipe()).isNull();

        equipe.joueurs(new HashSet<>(Set.of(joueurBack)));
        assertThat(equipe.getJoueurs()).containsOnly(joueurBack);
        assertThat(joueurBack.getEquipe()).isEqualTo(equipe);

        equipe.setJoueurs(new HashSet<>());
        assertThat(equipe.getJoueurs()).doesNotContain(joueurBack);
        assertThat(joueurBack.getEquipe()).isNull();
    }
}
