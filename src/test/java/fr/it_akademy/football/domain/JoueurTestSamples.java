package fr.it_akademy.football.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JoueurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Joueur getJoueurSample1() {
        return new Joueur().id(1L).nom("nom1").poste("poste1").numeroMaillot(1L).telephone("telephone1");
    }

    public static Joueur getJoueurSample2() {
        return new Joueur().id(2L).nom("nom2").poste("poste2").numeroMaillot(2L).telephone("telephone2");
    }

    public static Joueur getJoueurRandomSampleGenerator() {
        return new Joueur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .poste(UUID.randomUUID().toString())
            .numeroMaillot(longCount.incrementAndGet())
            .telephone(UUID.randomUUID().toString());
    }
}
