package com.github.glusk.sveder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public final class TestSifraLokacijeIzvajalca {
    @Test
    public void pravilnoPoravnaSifro() {
        assertEquals(
            "1230000321",
            new SifraLokacijeIzvajalca(
                () -> "123",
                () -> "321"
            ).vrednost()
        );
    }
}
