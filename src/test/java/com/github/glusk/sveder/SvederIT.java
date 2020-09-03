package com.github.glusk.sveder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.github.glusk.sveder.excel.ExcelOrdinacije;

import org.junit.Test;

public final class SvederIT {
    @Test
    public void najdeOrdinacijoPoKljucuZdravnikDejavnost() throws Exception {
        List<Ordinacija> rezultat =
            new ExcelOrdinacije(
                this.getClass().getResource("excel/Zob_12_18.xls")
            )
            .ordinacije()
            .stream()
            .filter(o ->
                o.dejavnost().equals("404101")
                &&
                o.zdravnik().imeInPriimek().matches(".*GAJIĆ.*ŽELJKO.*")
            )
            .collect(Collectors.toList());

        assertTrue(rezultat.size() == 1);
        Ordinacija o = rezultat.get(0);
        assertEquals(25333, o.izvajalec().intValue());
        assertEquals(7809, o.zdravnik().sifra().intValue());
        assertEquals("GAJIĆ  ŽELJKO", o.zdravnik().imeInPriimek());
        assertEquals("404101", o.dejavnost());
        assertEquals(116.11, o.doseganjePovprecja().doubleValue(), .001f);
    }
}
