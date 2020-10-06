package com.github.glusk.sveder.excel;

import java.io.IOException;

import com.github.glusk.sveder.PodrobnostiLokacije;
import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Row;

/**
 * Podrobnosti lokacije, pridobljene iz lista {@code LokacijeIZV}
 * preglednice zavoda.
 */
public final class PodrobnostiExcelLokacije implements PodrobnostiLokacije {
    /** Indeks stolpca "Šifra lokacije" v listu {@code LokacijeIZV}. */
    private static final int STOLPEC_SIFRA_LOKACIJE = 0;
    /** Indeks stolpca "Naziv lokacije" v listu {@code LokacijeIZV}. */
    private static final int STOLPEC_NAZIV = 1;
    /** Indeks stolpca "Naslov lokacije" v listu {@code LokacijeIZV}. */
    private static final int STOLPEC_NASLOV = 2;
    /** Indeks stolpca "Tel. številka" v listu {@code LokacijeIZV}. */
    private static final int STOLPEC_TELEFON = 3;
    /** Indeks stolpca "Pošta" v listu {@code LokacijeIZV}. */
    private static final int STOLPEC_POSTA = 4;

    /** Vrstica lista {@code LokacijeIZV}. */
    private final ExcelVrstica vrsticaPodrobnostiLokacije;

    /**
     * Zgradi podrobnosti lokacije iz zavoda za ključ:
     * {@code sifraIzvajalca | sifraLokacije}.
     *
     * @param zavod zavod s preglednico, v kateri je list `LokacijeIZV`
     * @param sifraIzvajalca šifra izvajalca, del ključa
     * @param sifraLokacije šifra lokacije, del ključa
     */
    public PodrobnostiExcelLokacije(
        final ZdravstveniZavod zavod,
        final Sifra sifraIzvajalca,
        final Sifra sifraLokacije
    ) {
        this(
            new VrsticaPodrobnostiLokacije(
                zavod,
                sifraIzvajalca,
                sifraLokacije
            )
        );
    }

    /**
     * Zgradi podrobnosti lokacije iz vrstice lista {@code LokacijeIZV}.
     *
     * @param vrsticaPodrobnostiLokacije vrstica lista {@code LokacijeIZV}
     */
    public PodrobnostiExcelLokacije(
        final ExcelVrstica vrsticaPodrobnostiLokacije
    ) {
        this.vrsticaPodrobnostiLokacije = vrsticaPodrobnostiLokacije;
    }

    @Override
    public Sifra sifra() throws IOException {
        return
            new ExcelSifra(
                vrsticaPodrobnostiLokacije.vrstica(),
                STOLPEC_SIFRA_LOKACIJE
            );
    }

    @Override
    public String naziv() throws IOException {
        String naziv = "";
        Row vrstica = vrsticaPodrobnostiLokacije.vrstica();
        if (new JeNepraznaCelica(vrstica, STOLPEC_NAZIV).test()) {
            naziv += vrstica.getCell(STOLPEC_NAZIV)
                            .getStringCellValue()
                            .strip();
        }
        return naziv;
    }

    @Override
    public String naslov() throws IOException {
        String naslov = "";
        Row vrstica = vrsticaPodrobnostiLokacije.vrstica();
        if (new JeNepraznaCelica(vrstica, STOLPEC_NASLOV).test()) {
            naslov += vrstica.getCell(STOLPEC_NASLOV)
                             .getStringCellValue()
                             .strip();
        }
        return naslov;
    }

    @Override
    public String telefon() throws IOException {
        String telefon = "";
        Row vrstica = vrsticaPodrobnostiLokacije.vrstica();
        if (new JeNepraznaCelica(vrstica, STOLPEC_TELEFON).test()) {
            telefon += vrstica.getCell(STOLPEC_TELEFON)
                              .getStringCellValue()
                              .strip();
        }
        return telefon;
    }

    @Override
    public Sifra posta() throws IOException {
        return
            new ExcelSifra(
                vrsticaPodrobnostiLokacije.vrstica(),
                STOLPEC_POSTA
            );
    }
}
