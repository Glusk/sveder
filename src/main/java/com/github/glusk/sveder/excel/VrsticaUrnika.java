package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Prva veljavna vrstica urnika v listu {@code UrnikiIZV} preglednice
 * zdravstvenega zavoda, ki ustreza ključu.
 *
 * @see JeVrsticaVeljavna
 */
public final class VrsticaUrnika implements ExcelVrstica {
    /** Indeks stolpca "Datum začetka veljavnosti" v listu "UrnikiIZV". */
    private static final int STOLPEC_ZACETEK_VELJAVNOSTI = 41;
    /** Indeks stolpca "Datum konca veljavnosti" v listu "UrnikiIZV". */
    private static final int STOLPEC_KONEC_VELJAVNOSTI = 43;

    /**
     * Zdravstveni zavod z Excel preglednico, ki vsebuje list
     * {@code UrnikiIZV}.
     */
    private final ZdravstveniZavod zavod;
    /**
     * Ključ po katerem iščemo vrstico v listu {@code UrnikiIZV}
     * preglednice {@code zavod}a.
     */
    private final Predicate<Row> kljuc;

    /**
     * Vrstica urnika po ključu lokacije ordinacije.
     * <p>
     * Enako kot:
     * <pre>
     * new VrsticaUrnika(
     *     zavod,
     *     new KljucLokacijeOrdinacije(
     *         sifraIzvajalca,
     *         sifraDejavnosti,
     *         sifraZdravnika,
     *         sifraLokacije
     *     )
     * );
     * </pre>
     *
     * @param zavod zdravstveni zavod z Excel preglednico, ki vsebuje list
     *              {@code UrnikiIZV}
     * @param sifraIzvajalca ta vrstica naj ima vrednost stolpca "Izvajalec"
     *                       enako tej vrednosti
     * @param sifraDejavnosti ta vrstica naj ima vrednost stolpca "Dejavnost"
     *                        enako tej vrednosti
     * @param sifraZdravnika ta vrstica naj ima vrednost stolpca "Nosilec"
     *                       enako tej vrednosti
     * @param sifraLokacije ta vrstica naj ima vrednost stolpca "Lokacija"
     *                      enako tej vrednosti
     * @see KljucLokacijeOrdinacije
     */
    public VrsticaUrnika(
        final ZdravstveniZavod zavod,
        final Number sifraIzvajalca,
        final Number sifraDejavnosti,
        final Number sifraZdravnika,
        final Number sifraLokacije
    ) {
        this(
            zavod,
            new KljucLokacijeOrdinacije(
                sifraIzvajalca,
                sifraDejavnosti,
                sifraZdravnika,
                sifraLokacije
            )
        );
    }

    /**
     * Vrstica urnika po ključu.
     *
     * @param zavod zdravstveni zavod z Excel preglednico, ki vsebuje list
     *              {@code UrnikiIZV}
     * @param kljuc ključ po katerem iščemo vrstico v listu {@code UrnikiIZV}
     *              preglednice {@code zavod}a
     */
    public VrsticaUrnika(
        final ZdravstveniZavod zavod,
        final Predicate<Row> kljuc
    ) {
        this.zavod = zavod;
        this.kljuc = kljuc;
    }

    /**
     * Vrne to vrstico.
     * <p>
     * V kolikor ima urnik več veljavnih vrstic (kar se lahko zgodi v primeru
     * nepazljivega vnosa s strani izvajalca), vzamemo prvo veljavno vrstico.
     *
     * @see JeVrsticaVeljavna
     */
    @Override
    public Row vrstica() throws IOException {
        try (Workbook preglednica = zavod.preglednica()) {
            return
                StreamSupport.stream(
                    preglednica.getSheet("UrnikiIZV").spliterator(),
                    false
                )
                .skip(1)
                .filter(vrstica ->
                    // Glej samo urnike ki so veljavni:
                    new JeVrsticaVeljavna(
                        vrstica,
                        STOLPEC_ZACETEK_VELJAVNOSTI,
                        STOLPEC_KONEC_VELJAVNOSTI
                    ).test()
                    &&
                    kljuc.test(vrstica)
                )
                .findFirst()
                .get();
        } catch (Exception e) {
            throw new IOException("Napaka bri branju vrstice.", e);
        }
    }
}
