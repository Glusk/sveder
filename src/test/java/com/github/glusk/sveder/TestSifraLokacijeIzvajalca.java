package com.github.glusk.sveder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class TestSifraLokacijeIzvajalca {
    @Test
    public void pravilnoPoravnaSifroNaDvanastMest() {
        assertEquals(
            "123000000321",
            new SifraLokacijeIzvajalca(
                () -> "123",
                () -> "321"
            ).vrednost()
        );
    }
}
