package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

public final class TestKljuc {
    @Test
    public void najdeOrdinacijoPoKljucu() throws IOException {
        assertTrue(
            new Kljuc(
                () -> "12345",
                () -> "404101",
                () -> "1234"
            ).test(
                WorkbookFactory.create(
                    this.getClass()
                        .getResource("ZOBEK_UrnCD.xlsx")
                        .openStream()
                ).getSheet("UrnikiIZV")
                 .getRow(1)
            )
        );
    }
    @Test
    public void najdeLokacijoIzvajalcaPoKljucu() throws IOException {
        assertTrue(
            new Kljuc(
                () -> "12345",
                () -> "10101"
            ).test(
                WorkbookFactory.create(
                    this.getClass()
                        .getResource("ZOBEK_UrnCD.xlsx")
                        .openStream()
                ).getSheet("LokacijeIZV")
                 .getRow(1)
            )
        );
    }
    @Test
    public void najdeLokacijoOrdinacijePoKljucu() throws IOException {
        assertTrue(
            new Kljuc(
                () -> "12345",
                () -> "404101",
                () -> "1234",
                () -> "10101"
            ).test(
                WorkbookFactory.create(
                    this.getClass()
                        .getResource("ZOBEK_UrnCD.xlsx")
                        .openStream()
                ).getSheet("UrnikiIZV")
                 .getRow(1)
            )
        );
    }
}
