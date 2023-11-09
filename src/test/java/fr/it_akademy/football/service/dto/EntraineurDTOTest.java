package fr.it_akademy.football.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntraineurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntraineurDTO.class);
        EntraineurDTO entraineurDTO1 = new EntraineurDTO();
        entraineurDTO1.setId(1L);
        EntraineurDTO entraineurDTO2 = new EntraineurDTO();
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
        entraineurDTO2.setId(entraineurDTO1.getId());
        assertThat(entraineurDTO1).isEqualTo(entraineurDTO2);
        entraineurDTO2.setId(2L);
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
        entraineurDTO1.setId(null);
        assertThat(entraineurDTO1).isNotEqualTo(entraineurDTO2);
    }
}
