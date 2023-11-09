package fr.it_akademy.football.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JoueurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JoueurDTO.class);
        JoueurDTO joueurDTO1 = new JoueurDTO();
        joueurDTO1.setId(1L);
        JoueurDTO joueurDTO2 = new JoueurDTO();
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
        joueurDTO2.setId(joueurDTO1.getId());
        assertThat(joueurDTO1).isEqualTo(joueurDTO2);
        joueurDTO2.setId(2L);
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
        joueurDTO1.setId(null);
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
    }
}
