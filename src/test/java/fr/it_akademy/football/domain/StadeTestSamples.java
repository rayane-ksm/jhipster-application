package fr.it_akademy.football.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stade getStadeSample1() {
        return new Stade().id(1L).nom("nom1").lieu("lieu1");
    }

    public static Stade getStadeSample2() {
        return new Stade().id(2L).nom("nom2").lieu("lieu2");
    }

    public static Stade getStadeRandomSampleGenerator() {
        return new Stade().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).lieu(UUID.randomUUID().toString());
    }
}
