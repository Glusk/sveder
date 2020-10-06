package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Prva veljavna vrstica urnika v listu {@code LokacijeIZV} preglednice
 * zdravstvenega zavoda, ki ustreza ključu.
 *
 * @see JeVrsticaVeljavna
 */
public final class VrsticaPodrobnostiLokacije implements ExcelVrstica {
    /** Indeks stolpca "Datum začetka veljavnosti" v listu "LokacijeIZV". */
    private static final int STOLPEC_ZACETEK_VELJAVNOSTI = 7;
    /** Indeks stolpca "Datum konca veljavnosti" v listu "LokacijeIZV". */
    private static final int STOLPEC_KONEC_VELJAVNOSTI = 9;

    /**
     * Zdravstveni zavod z Excel preglednico, ki vsebuje list
     * {@code LokacijeIZV}.
     */
    private final ZdravstveniZavod zavod;
    /**
     * Ključ po katerem iščemo vrstico v listu {@code LokacijeIZV}
     * preglednice {@code zavod}a.
     */
    private final Predicate<Row> kljuc;

    /**
     * Vrstica podrobnosti lokacije po ključu lokacije izvajalca.
     * <p>
     * Enako kot:
     * <pre>
     * new VrsticaPodrobnostiLokacije(
     *     zavod,
     *     new KljucLokacijeIzvajalca(
     *         sifraIzvajalca,
     *         sifraLokacije
     *     )
     * );
     * </pre>
     *
     * @param zavod zdravstveni zavod z Excel preglednico, ki vsebuje list
     *              {@code UrnikiIZV}
     * @param sifraIzvajalca parameter ključa - šifra izvajalca
     * @param sifraLokacije parameter ključa - šifra lokacije
     */
    public VrsticaPodrobnostiLokacije(
        final ZdravstveniZavod zavod,
        final Sifra sifraIzvajalca,
        final Sifra sifraLokacije
    ) {
        this(
            zavod,
            new KljucLokacijeIzvajalca(
                sifraIzvajalca,
                sifraLokacije
            )
        );
    }

    /**
     * Vrstica podrobnosti lokacije po ključu.
     *
     * @param zavod zdravstveni zavod z Excel preglednico, ki vsebuje list
     *              {@code LokacijeIZV}
     * @param kljuc ključ po katerem iščemo vrstico v listu {@code LokacijeIZV}
     *              preglednice {@code zavod}a
     */
    public VrsticaPodrobnostiLokacije(
        final ZdravstveniZavod zavod,
        final Predicate<Row> kljuc
    ) {
        this.zavod = zavod;
        this.kljuc = kljuc;
    }

    /**
     * Vrne to vrstico.
     * <p>
     * V kolikor ima lokacija več veljavnih vrstic (kar se lahko zgodi v primeru
     * nepazljivega vnosa s strani izvajalca), vzamemo prvo veljavno vrstico.
     *
     * @see JeVrsticaVeljavna
     */
    @Override
    public Row vrstica() throws IOException {
        try (Workbook preglednica = zavod.preglednica()) {
            return
                StreamSupport.stream(
                    preglednica.getSheet("LokacijeIZV").spliterator(),
                    false
                )
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
