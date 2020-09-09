package com.github.glusk.sveder.excel;

import java.time.Instant;

import org.apache.poi.ss.usermodel.Row;

/**
 * Test veljavnosti vrstic v Excel preglednicah zavodov.
 * <p>
 * Vrstice v listih {@code UrnikiIZV} in {@code CDIZV} Excel preglednic
 * {@code <Ime_zavoda>UrnČD.xlsx} imajo stolpca
 * <em>Datum začetka veljavnosti</em> ter <em>Datum konca veljavnosti</em>.
 * Na podlagi teh stolpcev ta razred preveri veljavnost vrstice.
 */
public final class JeVrsticaVeljavna {
    /** Vrstica, katere veljavnost preverjamo. */
    private final Row vrstica;
    /** Stolpec datuma zacetka veljavnosti v vrstici. */
    private final int stolpecZacetek;
    /** Stolpec datuma konca veljavnosti v vrstici. */
    private final int stolpecKonec;

    /**
     * Zgradi nov test veljavnosti vrstice.
     *
     * @param vrstica vrstica, katere veljavnost preverjamo
     * @param stolpecZacetek stolpec datuma zacetka veljavnosti v vrstici
     * @param stolpecKonec stolpec datuma konca veljavnosti v vrstici
     */
    public JeVrsticaVeljavna(
        final Row vrstica,
        final int stolpecZacetek,
        final int stolpecKonec
    ) {
        this.vrstica = vrstica;
        this.stolpecZacetek = stolpecZacetek;
        this.stolpecKonec = stolpecKonec;
    }

    /**
     * Preveri veljavnost vrstice.
     * <p>
     * Vrstica je veljavna, če:
     * <ul>
     *   <li>
     *     je datum začetka veljavnosti <em>pred</em> trenutnim datumom in ni
     *     podatka o datumu konca veljavnosti
     *   </li>
     *   <li>
     *     je trenutni datum <em>med</em> datumom začetka veljavnosti in
     *     datumom konca veljavnosti
     *   </li>
     * </ul>
     * @return {@code true}, če je vrstica veljavna
     */
    public boolean test() {
        if (!new JeNepraznaCelica(vrstica, stolpecZacetek).test()) {
            return false;
        }
        Instant zacetek = vrstica.getCell(stolpecZacetek)
                                 .getDateCellValue()
                                 .toInstant();
        if (!new JeNepraznaCelica(vrstica, stolpecKonec).test()) {
            return zacetek.isBefore(Instant.now());
        }
        Instant konec = vrstica.getCell(stolpecKonec)
                               .getDateCellValue()
                               .toInstant();
        return
            zacetek.isBefore(Instant.now())
            &&
            konec.isAfter(Instant.now());
    }
}
