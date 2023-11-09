package fr.it_akademy.football.domain;

import static fr.it_akademy.football.domain.EntraineurTestSamples.*;
import static fr.it_akademy.football.domain.EquipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntraineurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entraineur.class);
        Entraineur entraineur1 = getEntraineurSample1();
        Entraineur entraineur2 = new Entraineur();
        assertThat(entraineur1).isNotEqualTo(entraineur2);

        entraineur2.setId(entraineur1.getId());
        assertThat(entraineur1).isEqualTo(entraineur2);

        entraineur2 = getEntraineurSample2();
        assertThat(entraineur1).isNotEqualTo(entraineur2);
    }

    @Test
    void equipeTest() throws Exception {
        Entraineur entraineur = getEntraineurRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        entraineur.setEquipe(equipeBack);
        assertThat(entraineur.getEquipe()).isEqualTo(equipeBack);
        assertThat(equipeBack.getEntraineur()).isEqualTo(entraineur);

        entraineur.equipe(null);
        assertThat(entraineur.getEquipe()).isNull();
        assertThat(equipeBack.getEntraineur()).isNull();
    }
}
