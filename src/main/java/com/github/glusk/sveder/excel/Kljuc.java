package com.github.glusk.sveder.excel;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Row;

/** Ključ vrstic listov v Excel preglednicah. */
public final class Kljuc implements Predicate<Row> {
    /** Indeksi stolpcev tega ključa. */
    private final int[] indeksi;
    /** Vrednosti stolpcev tega ključa. */
    private final Sifra[] vrednosti;
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
                            .equals(vrednosti[i])
                        )
                        .reduce(Boolean::logicalAnd)
                        .get();
    }
}
