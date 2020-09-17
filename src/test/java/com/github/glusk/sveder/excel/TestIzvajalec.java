package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.glusk.sveder.net.SpletnaStran;

import org.junit.jupiter.api.Test;

public final class TestIzvajalec {
    @Test
    public void najdeVseZavodeNaStrani() throws Exception {
        assertEquals(
            250,
            new Izvajalec(
                new SpletnaStran(
                    () ->
                        this.getClass()
                            .getResource("seznamZavodov.txt")
                )
            ).zavodi().size()
        );
    }

    @Test
    public void vrneZavodeIzvajalcaZgrajenegaIzSifre() {
        assertDoesNotThrow(() ->
            new Izvajalec(() -> "7809").zavodi()
        );
    }
}
