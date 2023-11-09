package fr.it_akademy.football.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EntraineurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Entraineur getEntraineurSample1() {
        return new Entraineur().id(1L).nom("nom1").numIdent(1L).ancienneEquipe("ancienneEquipe1");
    }

    public static Entraineur getEntraineurSample2() {
        return new Entraineur().id(2L).nom("nom2").numIdent(2L).ancienneEquipe("ancienneEquipe2");
    }

    public static Entraineur getEntraineurRandomSampleGenerator() {
        return new Entraineur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .numIdent(longCount.incrementAndGet())
            .ancienneEquipe(UUID.randomUUID().toString());
    }
}
