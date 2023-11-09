package fr.it_akademy.football.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EquipeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Equipe getEquipeSample1() {
        return new Equipe().id(1L).nom("nom1").pays("pays1").nbJoueurs(1L).classement(1L);
    }

    public static Equipe getEquipeSample2() {
        return new Equipe().id(2L).nom("nom2").pays("pays2").nbJoueurs(2L).classement(2L);
    }

    public static Equipe getEquipeRandomSampleGenerator() {
        return new Equipe()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .pays(UUID.randomUUID().toString())
            .nbJoueurs(longCount.incrementAndGet())
            .classement(longCount.incrementAndGet());
    }
}
