package nl.cz.testsupport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import nl.cz.testsupport.web.rest.TestUtil;

public class BoekingbestandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boekingbestand.class);
        Boekingbestand boekingbestand1 = new Boekingbestand();
        boekingbestand1.setId(1L);
        Boekingbestand boekingbestand2 = new Boekingbestand();
        boekingbestand2.setId(boekingbestand1.getId());
        assertThat(boekingbestand1).isEqualTo(boekingbestand2);
        boekingbestand2.setId(2L);
        assertThat(boekingbestand1).isNotEqualTo(boekingbestand2);
        boekingbestand1.setId(null);
        assertThat(boekingbestand1).isNotEqualTo(boekingbestand2);
    }
}
