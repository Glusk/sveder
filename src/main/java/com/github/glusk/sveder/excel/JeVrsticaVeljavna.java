package com.github.glusk.sveder.excel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
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
    /** Format datumov, kjer je datum v celici shranjen kot niz. */
    private static final DateTimeFormatter FORMAT_DATUMA =
       DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("sl"));
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
     * Vrne datum {@code NUMERIC} in {@code STRING} celic.
     *
     * @param celica celica Excel preglednice z datumom
     * @return datum kot objekt tipa {@code Instant}
     * @see {@link org.apache.poi.ss.usermodel.CellType}
     * @throws IllegalStateException če {@code celica} ni tipa
     *                              {@code NUMERIC} ali {@code STRING}
     * @throws DateTimeParseException če niz v celici ni v formatu:
     *                                {@code dd.MM.yyyy}
     */
    private static Instant vrniDatum(final Cell celica) {
        switch (celica.getCellType()) {
            case NUMERIC:
                return celica.getDateCellValue().toInstant();
            case STRING:
                return
                    LocalDate.parse(
                        celica.getStringCellValue(),
                        FORMAT_DATUMA
                    )
                    .atStartOfDay()
                    .atZone(ZoneId.of("GMT+1"))
                    .toInstant();
            default:
                throw new IllegalStateException(
                    celica.getCellType().toString()
                );
        }
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
        Instant zacetek = vrniDatum(vrstica.getCell(stolpecZacetek));
        if (!new JeNepraznaCelica(vrstica, stolpecKonec).test()) {
            return zacetek.isBefore(Instant.now());
        }
        Instant konec = vrniDatum(vrstica.getCell(stolpecKonec));
        return
            zacetek.isBefore(Instant.now())
            &&
            konec.isAfter(Instant.now());
    }
}
