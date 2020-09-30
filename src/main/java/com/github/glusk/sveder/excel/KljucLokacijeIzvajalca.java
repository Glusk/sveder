package com.github.glusk.sveder.excel;

import java.util.function.Predicate;

import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Row;

/**
 * Ključ vrstic lista {@code LokacijeIZV} v Excel preglednici
 * {@code <Ime_zavoda>UrnČD.xlsx}.
 * <p>
 * Prvi stolpec je ključ 12-mesten ključ vrstic, ki je definiran takole:
 * <pre>
 * &lt;sifra izvajalca&gt; | &lt;sifra lokacije&gt;
 * </pre>
 * pri čemer je {@code |} oznaka za konkatenacijo nizov. Če se dožini šifer
 * ne seštejeta v 12, vmesni prostor zapolnijo ničle.
 */
public final class KljucLokacijeIzvajalca implements Predicate<Row> {
    /** Dolžina šifre stolpca "Šifra lokacije". */
    private static final int DOLZINA_SIFRE = 12;
    /** Indeks stolpca "Šifra lokacije" v vrsticah preglednice. */
    private static final int STOLPEC_SIFRA_LOKACIJE = 0;

    /** Vrednost stolpca "Šifra lokacije" tega ključa. */
    private final Sifra sifraLokacijeIzvajalca;

    /**
     * Sestavi nov ključ lokacije izvajalca s podanima šiframa lokacije
     * in izvajalca.
     *
     * @param sifraIzvajalca šifra izvajalca tega ključa
     * @param sifraLokacije šifra lokacije tega ključa
     */
    public KljucLokacijeIzvajalca(
        final Sifra sifraIzvajalca,
        final Sifra sifraLokacije
    ) {
        this(() ->
            sifraIzvajalca.vrednost()
          + "0".repeat(
              DOLZINA_SIFRE
            - sifraIzvajalca.vrednost().length()
            - sifraLokacije.vrednost().length()
            )
          + sifraLokacije.vrednost()
        );
    }

    /**
     * Sestavi nov ključ lokacije izvajalca s podano vrednostjo
     * stolpca ključa.
     *
     * @param sifraLokacijeIzvajalca vrednost stolpca "Šifra lokacije" tega
     *                               ključa
     */
    public KljucLokacijeIzvajalca(final Sifra sifraLokacijeIzvajalca) {
        this.sifraLokacijeIzvajalca = sifraLokacijeIzvajalca;
    }

    /**
     * Preveri ali {@code vrstica} ustreza temu ključu.
     *
     * @param vrstica Vrstica enega izmed lista ({@code LokacijeIZV} v Excel
     *                preglednici {@code <Ime_zavoda>UrnČD.xlsx}.
     *                Ne sme biti prva vrstica, ki je oznaka stolpcev. V tem
     *                primeru bo ta metoda vrgla izjemo.
     * @return {@code true}, če vrstica ustreza temu ključu
     * @throws IllegalStateException če je {@code vrstica} prva vrstica v
     *                               listu v kateri so oznake stolpcev
     */
    @Override
    public boolean test(final Row vrstica) {
        return
            new ExcelSifra(
                vrstica,
                STOLPEC_SIFRA_LOKACIJE
            ).vrednost().equals(sifraLokacijeIzvajalca.vrednost()
        );
    }
}
