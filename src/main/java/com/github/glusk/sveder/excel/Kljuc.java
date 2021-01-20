package com.github.glusk.sveder.excel;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import com.github.glusk.sveder.Sifra;
import com.github.glusk.sveder.SifraLokacijeIzvajalca;

import org.apache.poi.ss.usermodel.Row;

/** Ključ vrstic listov v Excel preglednicah. */
public final class Kljuc implements Predicate<Row> {
    /**
     * Indeks stolpca "Izvajalec" v vrsticah listov {@code UrnikiIZV} in
     * {@code CDIZV} preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     */
    private static final int STOLPEC_IZVAJALEC = 0;
    /**
     * Indeks stolpca "Dejavnost" v vrsticah listov {@code UrnikiIZV} in
     * {@code CDIZV} preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     */
    private static final int STOLPEC_DEJAVNOST = 1;
    /**
     * Indeks stolpca "Lokacija" v vrsticah listov {@code UrnikiIZV} in
     * {@code CDIZV} preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     */
    private static final int STOLPEC_LOKACIJA = 2;
    /**
     * Indeks stolpca "Nosilec" v vrsticah listov {@code UrnikiIZV} in
     * {@code CDIZV} preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     */
    private static final int STOLPEC_NOSILEC = 3;

    /**
     * Indeks stolpca "Šifra lokacije" v vrsticah listov {@code LokacijeIZV}
     * preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     */
    private static final int STOLPEC_SIFRA_LOKACIJE = 0;

    /** Indeksi stolpcev tega ključa. */
    private final int[] indeksi;
    /** Vrednosti stolpcev tega ključa. */
    private final Sifra[] vrednosti;


    /**
     * Sestavi nov ključ <em>lokacije ordinacije</em> s podanimi vrednostmi
     * stolpcev ključa.
     * <p>
     * Ta ključ lahko upoabimo v listih {@code UrnikiIZV} in {@code CDIZV}
     * preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     * <p>
     * Prvi štirje stolpci (<em>Izvajalec</em>, <em>Dejavnost</em>,
     * <em>Lokacija</em> in <em>Nosilec</em>) v vrstici enolično določajo
     * vrstico.
     *
     * @param sifraIzvajalca vrednost stolpca "Izvajalec" tega ključa
     * @param sifraDejavnosti vrednost stolpca "Dejavnost" tega ključa
     * @param sifraZdravnika vrednost stolpca "Nosilec" tega ključa
     * @param sifraLokacije vrednost stolpca "Lokacija" tega ključa
     * @see com.github.glusk.sveder.Ordinacija
     */
    public Kljuc(
        final Sifra sifraIzvajalca,
        final Sifra sifraDejavnosti,
        final Sifra sifraZdravnika,
        final Sifra sifraLokacije
    ) {
        this(
            new int[] {
                STOLPEC_IZVAJALEC,
                STOLPEC_DEJAVNOST,
                STOLPEC_NOSILEC,
                STOLPEC_LOKACIJA
            },
            new Sifra[] {
                sifraIzvajalca,
                sifraDejavnosti,
                sifraZdravnika,
                sifraLokacije
            }
        );
    }

    /**
     * Sestavi nov ključ <em>ordinacije</em> s podanimi vrednostmi
     * stolpcev ključa.
     * <p>
     * Ta ključ lahko upoabimo v listih {@code UrnikiIZV} in {@code CDIZV}
     * preglednic {@code <Ime_zavoda>UrnČD.xlsx}.
     * <p>
     * Prvi štirje stolpci (<em>Izvajalec</em>, <em>Dejavnost</em>,
     * <em>Lokacija</em> in <em>Nosilec</em>) v vrstici enolično določajo
     * vrstico. Ta konstruktor vzame za ključ samo stolpce: <em>Izvajalec</em>,
     * <em>Dejavnost</em> in <em>Nosilec</em>.
     * <p>
     * V Svedru je ordinacija enolično določena z zdravnikom, izvajalcem in
     * vrsto dejavnosti, zato lahko rečemo da je ta razred ključ ordinacije.
     *
     * @param sifraIzvajalca vrednost stolpca "Izvajalec" tega ključa
     * @param sifraDejavnosti vrednost stolpca "Dejavnost" tega ključa
     * @param sifraZdravnika vrednost stolpca "Nosilec" tega ključa
     * @see com.github.glusk.sveder.Ordinacija
     */
    public Kljuc(
        final Sifra sifraIzvajalca,
        final Sifra sifraDejavnosti,
        final Sifra sifraZdravnika
    ) {
        this(
            new int[] {
                STOLPEC_IZVAJALEC,
                STOLPEC_DEJAVNOST,
                STOLPEC_NOSILEC
            },
            new Sifra[] {
                sifraIzvajalca,
                sifraDejavnosti,
                sifraZdravnika
            }
        );
    }

    /**
     * Sestavi nov ključ lokacije izvajalca s podanima šiframa lokacije
     * in izvajalca.
     * <p>
     * Ta ključ lahko uporabimo v listih {@code LokacijeIZV} preglednic
     * {@code <Ime_zavoda>UrnČD.xlsx}.
     *
     * @param sifraIzvajalca šifra izvajalca tega ključa
     * @param sifraLokacije šifra lokacije tega ključa
     * @see com.github.glusk.sveder.SifraLokacijeIzvajalca
     */
    public Kljuc(
        final Sifra sifraIzvajalca,
        final Sifra sifraLokacije
    ) {
        this(
            new int[] {STOLPEC_SIFRA_LOKACIJE},
            new Sifra[] {
                new SifraLokacijeIzvajalca(
                    sifraIzvajalca,
                    sifraLokacije
                )
            }
        );
    }

    /**
     * Sestavi nov ključ s podanimi indeksi in vrednostmi šifer ključa.
     * <p>
     * Recimo, da iščemo sledečo vrstico: <pre>
     * |  0   |   1   |   2   | .... | n |
     * |---------------------------------|
     * |               ....              |
     * |---------------------------------|
     * | "12" | "abc" | ..... | ...  | . | --&gt; iskana vrstica
     * |---------------------------------|
     * </pre>,
     * potem izvedemo klic tega konstruktorja na nasleden način: <pre>
     * new Kljuc(
     *     new int[] {0, 1},
     *     new Sifra[] {
     *         () -&gt; "12",
     *         () -&gt; "abc"
     *     }
     * );
     * </pre>
     *
     * @param indeksi indeksi stolpcev tega ključa
     * @param vrednosti vrednosti stolpcev tega ključa
     */
    public Kljuc(
        final int[] indeksi,
        final Sifra[] vrednosti
    ) {
        this.indeksi = indeksi;
        this.vrednosti = vrednosti;
    }

    /**
     * Preveri ali {@code vrstica} ustreza temu ključu.
     *
     * @param vrstica Vrstica enega izmed listov v Excel
     *                preglednici.
     *                Ne sme biti prva vrstica, ki je oznaka stolpcev. V tem
     *                primeru bo ta metoda vrgla izjemo.
     * @return {@code true}, če vrstica ustreza temu ključu
     * @throws IllegalArgumentException če je {@code vrstica} prva vrstica v
     *                                  listu v kateri so oznake stolpcev
     */
    @Override
    public boolean test(final Row vrstica) {
        return IntStream.range(0, indeksi.length)
                        .boxed()
                        .map(i ->
                            new ExcelSifra(
                                vrstica,
                                indeksi[i]
                            )
                            .vrednost()
                            .equals(vrednosti[i].vrednost())
                        )
                        .reduce(Boolean::logicalAnd)
                        .get();
    }
}
