package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

public final class TestKljucOrdinacije {
    @Test
    public void potrdiUjemanjeVrsticePoKljucu() throws IOException {
        assertTrue(
            new KljucOrdinacije(
                25333,
                404101,
                7809
            ).test(
                WorkbookFactory.create(
                    this.getClass()
                        .getResource("ZOB_ZAVOD_D_IN_G_KOPER_UrnCD.xlsx")
                        .openStream()
                ).getSheet("UrnikiIZV")
                 .getRow(1)
            )
        );
    }

    @Test
    public void vrzeIzjemoPriBranjuPrveVrstice() {
        assertThrows(IllegalStateException.class, () ->
            new KljucOrdinacije(
                25333,
                404101,
                7809
            ).test(
                WorkbookFactory.create(
                    this.getClass()
                        .getResource("ZOB_ZAVOD_D_IN_G_KOPER_UrnCD.xlsx")
                        .openStream()
                ).getSheet("UrnikiIZV")
                 .getRow(0)
            )
        );
    }
}
