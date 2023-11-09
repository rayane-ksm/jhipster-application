package fr.it_akademy.football.domain;

import static fr.it_akademy.football.domain.EquipeTestSamples.*;
import static fr.it_akademy.football.domain.StadeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stade.class);
        Stade stade1 = getStadeSample1();
        Stade stade2 = new Stade();
        assertThat(stade1).isNotEqualTo(stade2);

        stade2.setId(stade1.getId());
        assertThat(stade1).isEqualTo(stade2);

        stade2 = getStadeSample2();
        assertThat(stade1).isNotEqualTo(stade2);
    }

    @Test
    void equipeTest() throws Exception {
        Stade stade = getStadeRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        stade.setEquipe(equipeBack);
        assertThat(stade.getEquipe()).isEqualTo(equipeBack);
        assertThat(equipeBack.getStade()).isEqualTo(stade);

        stade.equipe(null);
        assertThat(stade.getEquipe()).isNull();
        assertThat(equipeBack.getStade()).isNull();
    }
}
