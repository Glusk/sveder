package com.github.glusk.sveder.excel;

import java.util.function.Predicate;

import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Row;

/**
 * Ključ vrstic listov {@code UrnikiIZV} in {@code CDIZV} v Excel preglednici
 * {@code <Ime_zavoda>UrnČD.xlsx}.
 * <p>
 * Prvi štirje stolpci (<em>Izvajalec</em>, <em>Dejavnost</em>,
 * <em>Lokacija</em> in <em>Nosilec</em>) v vrstici enolično določajo
 * vrstico in ta razred vzame ravno te stolpce za ključ.
 * <p>
 * V Svedru je ordinacija enolično določena z zdravnikom, izvajalcem in
 * vrsto dejavnosti, zato lahko rečemo da je ta razred ključ lokacije neke
 * ordinacije.
 *
 * @see com.github.glusk.sveder.Ordinacija
 */
public final class KljucLokacijeOrdinacije implements Predicate<Row> {
    /** Indeks stolpca "Izvajalec" v vrsticah preglednice. */
    private static final int STOLPEC_IZVAJALEC = 0;
    /** Indeks stolpca "Dejavnost" v vrsticah preglednice. */
    private static final int STOLPEC_DEJAVNOST = 1;
    /** Indeks stolpca "Lokacija" v vrsticah preglednice. */
    private static final int STOLPEC_LOKACIJA = 2;
    /** Indeks stolpca "Nosilec" v vrsticah preglednice. */
    private static final int STOLPEC_NOSILEC = 3;

    /** Vrednost stolpca "Izvajalec" tega ključa. */
    private final Sifra sifraIzvajalca;
    /** Vrednost stolpca "Dejavnost" tega ključa. */
    private final Sifra sifraDejavnosti;
    /** Vrednost stolpca "Nosilec" tega ključa. */
    private final Sifra sifraZdravnika;
    /** Vrednost stolpca "Lokacija" tega ključa. */
    private final Sifra sifraLokacije;

    /**
     * Sestavi nov ključ lokacije ordinacije s podanimi vrednostmi
     * stolpcev ključa.
     *
     * @param sifraIzvajalca vrednost stolpca "Izvajalec" tega ključa
     * @param sifraDejavnosti vrednost stolpca "Dejavnost" tega ključa
     * @param sifraZdravnika vrednost stolpca "Nosilec" tega ključa
     * @param sifraLokacije vrednost stolpca "Lokacija" tega ključa
     */
    public KljucLokacijeOrdinacije(
        final Sifra sifraIzvajalca,
        final Sifra sifraDejavnosti,
        final Sifra sifraZdravnika,
        final Sifra sifraLokacije
    ) {
        this.sifraIzvajalca = sifraIzvajalca;
        this.sifraDejavnosti = sifraDejavnosti;
        this.sifraZdravnika = sifraZdravnika;
        this.sifraLokacije = sifraLokacije;
    }

    /**
     * Preveri ali {@code vrstica} ustreza temu ključu.
     *
     * @param vrstica Vrstica enega izmed listov ({@code UrnikiIZV} ali
     *                {@code CDIZV}) v Excel preglednici
     *                {@code <Ime_zavoda>UrnČD.xlsx}.
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
                STOLPEC_IZVAJALEC
            ).vrednost().equals(sifraIzvajalca.vrednost())
            &&
            new ExcelSifra(
                vrstica,
                STOLPEC_DEJAVNOST
            ).vrednost().equals(sifraDejavnosti.vrednost())
            &&
            new ExcelSifra(
                vrstica,
                STOLPEC_NOSILEC
            ).vrednost().equals(sifraZdravnika.vrednost())
            &&
            new ExcelSifra(
                vrstica,
                STOLPEC_LOKACIJA
            ).vrednost().equals(sifraLokacije.vrednost());
    }
}
