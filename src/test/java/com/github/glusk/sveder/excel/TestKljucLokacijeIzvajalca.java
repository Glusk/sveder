package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

public final class TestKljucLokacijeIzvajalca {
    @Test
    public void potrdiUjemanjeVrsticePoKljucu() throws IOException {
        assertTrue(
            new KljucLokacijeIzvajalca(
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
}
