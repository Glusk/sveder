package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Prva veljavna vrstica v {@code list}u preglednice {@code zavod}a, ki ustreza
 * {@code kljuc}u.
 * <p>
 * Na ta način se da iskati vrstice v listih {@code LokacijeIZV},
 * {@code UrnikiIZV}, {@code CDIZV} in še nekaterih drugih, ki pa za delovanje
 * Svedra niso pomembni.
 *
 * @see JeVrsticaVeljavna
 */
public final class PrvaVeljavnaVrsticaPoKljucu implements ExcelVrstica {
    /** Zdravstveni zavod z Excel preglednico, ki vsebuje {@code list}. */
    private final ZdravstveniZavod zavod;
    /** Ime lista preglednice {@code zavod}a kjer se nahaja vrstica. */
    private String list;
     /** Indeks stolpca "Datum začetka veljavnosti" v {@code list}u. */
    private int stolpecZacetekVeljavnosti;
    /** Indeks stolpca "Datum konca veljavnosti" v {@code list}u. */
    private int stolpecKonecVeljavnosti;
    /** Ključ po katerem iščemo vrstico v {@code list}u. */
    private final Predicate<Row> kljuc;

    /**
     * Zgradi prvo veljavno vrstico v {@code list}u preglednice {@code zavod}a,
     * ki ustreza {@code kljuc}u.
     *
     * @param zavod zdravstveni zavod z Excel preglednico, ki vsebuje
     *             {@code list}
     * @param list ime lista preglednice {@code zavod}a kjer se nahaja vrstica
     * @param stolpecZacetekVeljavnosti indeks stolpca "Datum začetka
     *                                  veljavnosti" v {@code list}u
     * @param stolpecKonecVeljavnosti indeks stolpca "Datum konca veljavnosti"
     *                                v {@code list}u
     * @param kljuc ključ po katerem iščemo vrstico v {@code list}u
     */
    public PrvaVeljavnaVrsticaPoKljucu(
        final ZdravstveniZavod zavod,
        final String list,
        final int stolpecZacetekVeljavnosti,
        final int stolpecKonecVeljavnosti,
        final Predicate<Row> kljuc
    ) {
        this.zavod = zavod;
        this.list = list;
        this.stolpecZacetekVeljavnosti = stolpecZacetekVeljavnosti;
        this.stolpecKonecVeljavnosti = stolpecKonecVeljavnosti;
        this.kljuc = kljuc;
    }

    @Override
    public Row vrstica() throws IOException {
        try (Workbook preglednica = zavod.preglednica()) {
            return
                StreamSupport.stream(
                    preglednica.getSheet(this.list).spliterator(),
                    false
                )
                .filter(vrstica ->
                    new JeVrsticaVeljavna(
                        vrstica,
                        this.stolpecZacetekVeljavnosti,
                        this.stolpecKonecVeljavnosti
                    ).test()
                    &&
                    this.kljuc.test(vrstica)
                )
                .findFirst()
                .get();
        } catch (Exception e) {
            throw new IOException("Napaka bri branju vrstice.", e);
        }
    }
}
