package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.github.glusk.sveder.Urnik;

import org.apache.poi.ss.usermodel.Row;

/**
 * Urnik ordinacije iz vrstice v listu {@code UrnikiIZV} Excel
 * datoteke zavoda.
 */
public final class ExcelUrnik implements Urnik {
    /**
     * Indeks stoplca s podatkom <em>pričetku</em> dela v ponedeljek.
     * <p>
     * Podatek o <em>koncu</em> delovnega časa za ponedeljek je na indeksu
     * {@code URNIK_OFFSET + 1}. Podatek o <em>pričetku</em> dela v torek
     * je na indeksu {@code URNIK_OFFSET + 2} itd.
     */
    private static final int URNIK_OFFSET = 5;
    /** Indeks stolpca "Opombe" v listu {@code UrnikiIZV}. */
    private static final int STOLPEC_OPOMBE = 20;
     /** Indeks stolpca "Opombe malica" v listu {@code UrnikiIZV}. */
    private static final int STOLPEC_OPOMBE_MALICA = 21;
    /** Excel vrstica s podatki o urniku ordinacije. */
    private final ExcelVrstica vrsticaUrnika;

    /**
     * Zgradi nov Excel urnik iz vrstice urnika po ključu lokacije
     * ordinacije.
     * <p>
     * Enako kot:
     * <pre>
     * new ExcelUrnik(
     *     new VrsticaUrnika(
     *         zavod,
     *         sifraIzvajalca,
     *         sifraDejavnosti,
     *         sifraZdravnika,
     *         sifraLokacije
     *     )
     * );
     * </pre>
     *
     * @param zavod zdravstveni zavod s preglednico, ki vsebuje podatke
     *              o urniku ordinacije
     * @param sifraIzvajalca vrednost stolpca "Izvajalec" ključa
     * @param sifraDejavnosti stolpec "Dejavnost" ključa
     * @param sifraZdravnika stolpec "Nosilec" ključa
     * @param sifraLokacije stolpec "Lokacija" ključa
     * @see VrsticaUrnika
     */
    public ExcelUrnik(
        final ZdravstveniZavod zavod,
        final Number sifraIzvajalca,
        final Number sifraDejavnosti,
        final Number sifraZdravnika,
        final Number sifraLokacije
    ) {
        this(
            new VrsticaUrnika(
                zavod,
                sifraIzvajalca,
                sifraDejavnosti,
                sifraZdravnika,
                sifraLokacije
            )
        );
    }

    /**
     * Zgradi nov ExcelUrnik iz vrstice.
     *
     * @param vrsticaUrnika Excel vrstica s podatki o urniku ordinacije
     */
    public ExcelUrnik(final ExcelVrstica vrsticaUrnika) {
        this.vrsticaUrnika = vrsticaUrnika;
    }

    @Override
    public String opombe() throws IOException {
        String opombe = "";
        Row vrstica = vrsticaUrnika.vrstica();
        if (new JeNepraznaCelica(vrstica, STOLPEC_OPOMBE).test()) {
            opombe += vrstica.getCell(STOLPEC_OPOMBE)
                             .getStringCellValue()
                             .strip() + " ";
        }
        if (new JeNepraznaCelica(vrstica, STOLPEC_OPOMBE_MALICA).test()) {
            opombe += vrstica.getCell(STOLPEC_OPOMBE_MALICA)
                             .getStringCellValue()
                             .strip();
        }
        return opombe.strip();
    }

    @Override
    public String[][] urnik() throws IOException {
        String[][] urnik = new String[Urnik.DELOVNI_DNEVI.length][2];
        Row vrstica = vrsticaUrnika.vrstica();
        for (int i = 0; i < urnik.length; i++) {
            urnik[i][0] = "";
            urnik[i][1] = "";
            if (new JeNepraznaCelica(vrstica, URNIK_OFFSET + 2 * i).test()) {
                urnik[i][0] = vrstica
                    .getCell(URNIK_OFFSET + 2 * i)
                    .getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.of("GMT+1"))
                    .format(
                        DateTimeFormatter.ofPattern(
                            "HH:mm"
                        )
                    );
            }
            if (
                new JeNepraznaCelica(vrstica, URNIK_OFFSET + 2 * i + 1).test()
            ) {
                urnik[i][1] = vrstica
                    .getCell(URNIK_OFFSET + 2 * i + 1)
                    .getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.of("GMT+1"))
                    .format(
                        DateTimeFormatter.ofPattern(
                            "HH:mm"
                        )
                    );
            }
        }
        return urnik;
    }
}
