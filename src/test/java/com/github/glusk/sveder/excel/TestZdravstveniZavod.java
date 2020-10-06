package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public final class TestZdravstveniZavod {
    @Test
    public void vrzeIzjemoCeJeNaURLjuPregledniceWordovDokument() {
        assertThrows(IOException.class, () ->
            new ZdravstveniZavod(
                () ->
                    this.getClass()
                        .getResource("PrazenWordovDokument.docx")
            )
            .preglednica()
        );
    }
    @Test
    public void neMeceIzjemeCeNeNajdeStraniSPreglednico() {
        assertDoesNotThrow(() ->
            new ZdravstveniZavod(() -> {
                throw new FileNotFoundException();
            }).preglednica()
        );
    }
}
