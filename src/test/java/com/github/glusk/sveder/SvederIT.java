package com.github.glusk.sveder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.github.glusk.sveder.excel.ExcelOrdinacije;
import com.github.glusk.sveder.iskanje.OrdinacijeImeDejavnost;

import org.junit.Test;

public final class SvederIT {
    @Test
    public void najdeOrdinacijoPoKljucuZdravnikDejavnost() throws Exception {
        List<Ordinacija> rezultat =
            new OrdinacijeImeDejavnost(
                new ExcelOrdinacije(
                    this.getClass()
                        .getResource("excel/ZOB_1_8_2020.XLSX")
                ),
                "404101",
                "ŽELJKO",
                "GAJIĆ"
            )
            .ordinacije();

        assertTrue(rezultat.size() == 1);
        Ordinacija o = rezultat.get(0);
        assertEquals(25333, o.izvajalec().intValue());
        assertEquals(7809, o.zdravnik().sifra().intValue());
        assertEquals("GAJIĆ ŽELJKO", o.zdravnik().imeInPriimek());
        assertEquals("404101", o.dejavnost());
        assertEquals(119.13, o.doseganjePovprecja().doubleValue(), .001f);
    }
}
