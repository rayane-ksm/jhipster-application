package fr.it_akademy.football.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.football.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StadeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StadeDTO.class);
        StadeDTO stadeDTO1 = new StadeDTO();
        stadeDTO1.setId(1L);
        StadeDTO stadeDTO2 = new StadeDTO();
        assertThat(stadeDTO1).isNotEqualTo(stadeDTO2);
        stadeDTO2.setId(stadeDTO1.getId());
        assertThat(stadeDTO1).isEqualTo(stadeDTO2);
        stadeDTO2.setId(2L);
        assertThat(stadeDTO1).isNotEqualTo(stadeDTO2);
        stadeDTO1.setId(null);
        assertThat(stadeDTO1).isNotEqualTo(stadeDTO2);
    }
}
