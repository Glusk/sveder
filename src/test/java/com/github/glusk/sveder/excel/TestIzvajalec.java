package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.glusk.sveder.net.SpletnaStran;
import com.github.glusk.sveder.net.SvederUrl;

import org.junit.jupiter.api.Test;

public final class TestIzvajalec {
    @Test
    public void najdeVseZavodeNaStrani() throws Exception {
        assertEquals(
            250,
            new Izvajalec(
                new SpletnaStran(
                    new SvederUrl.UrlOvoj(
                        this.getClass()
                            .getResource("seznamZavodov.txt")
                            .toString()
                    )
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
