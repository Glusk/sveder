package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.glusk.sveder.Dejavnost;
import com.github.glusk.sveder.Lokacije;
import com.github.glusk.sveder.Ordinacija;
import com.github.glusk.sveder.Ordinacije;
import com.github.glusk.sveder.Sifra;
import com.github.glusk.sveder.Zdravnik;
import com.github.glusk.sveder.net.SpletnaStran;
import com.github.glusk.sveder.net.SvederUrl;
import com.github.glusk.sveder.net.UrlNaStrani;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Ordinacije iz {@code .xls} preglednice s seznamom aktivnih zobozdravnikov.
 * <p>
 * Preglednice Zavod za zdravstveno zavarovanje mesečno objalvlja na svojih
 * spletnih straneh.
 *
 * @see <a href="https://partner.zzzs.si/wps/portal/portali/aizv/zdravstvene_storitve/izbira_osebnega_zdravnika/sezn_akt_zob_za_odrasle_in_mladino">
 *      ZZZS - Seznam aktivnih zobozdravnikov za odrasle in mladino</a>
 */
public final class ExcelOrdinacije implements Ordinacije {
    /** Https naslov strežnika ZZZS. */
    private static final String STREZNIK = "https://partner.zzzs.si";
    /** Relativna pot do strani aktivnih zobozdravnikov na {@code STREZNIK}. */
    @SuppressWarnings("checkstyle:linelength")
    private static final String RELATIVNA_POT = "/wps/portal/portali/aizv/zdravstvene_storitve/izbira_osebnega_zdravnika/sezn_akt_zob_za_odrasle_in_mladino";
    /** Spletni naslov strani z seznamom preglednic aktivnih zobozdravnikov. */
    private static final String URL_STRANI = STREZNIK + RELATIVNA_POT;
    /**
     * Modul za testiranje posebnih šifer območnih enot izvajalcev.
     *
     * Primer:
     * <pre>
     * if (sifra % MODUL != MAGIC_OE_SKUPNO) {
     *     // ne gre za posebno šifro
     * }
     * </pre>
     */
    private static final int MODUL = 10000;
    /**
     * Posebni konec šifre območne enote.
     *
     * Podatki v takih vrsticah so agregirani po posamezni območni enoti in
     * dejavnosti.
     */
    private static final int MAGIC_OE_SKUPNO = 9999;
    /** Index stolpca "Šifra OE izvajalca". */
    private static final int STOLPEC_OE_IZVAJALCA = 0;
    /** Index stolpca "Šifra izvajalca". */
    private static final int STOLPEC_IZVAJALEC = 2;
    /** Index stolpca "Šifra zdravnika". */
    private static final int STOLPEC_ZDRAVNIK_SIFRA = 6;
    /** Index stolpca "Priimek in ime zdravnika". */
    private static final int STOLPEC_ZDRAVNIK_IME_PRIIMEK = 7;
    /** Index stolpca "Šifra ZZZS dejavnosti". */
    private static final int STOLPEC_DEJAVNOST = 8;
    /** Index stolpca "Doseganje povprečja". */
    private static final int STOLPEC_DOSEGANJE_POVP = 19;

    /** URL lokacije {@code .xls} datoteke. */
    private final SvederUrl urlPreglednice;

    /**
     * Zgradi nov objekt tipa {@code ExcelOrdinacije} iz URL naslova najbolj
     * ažurne preglednice aktivnih zobozdravnikov.
     */
    public ExcelOrdinacije() {
        this(
            new UrlNaStrani(
                new SpletnaStran(
                    () -> new URL(URL_STRANI)
                ),
                STREZNIK,
                "li:containsOwn(Seznam aktivnih zobozdravnikov za odrasle in mladino) + li a[href]"
            )
        );
    }

    /**
     * Zgradi nov objekt tipa {@code ExcelOrdinacije} z potjo do {@code .xls}
     * datoteke.
     * <p>
     * Za konstruktor je uporaben za testiranje oziroma v primeru, ko bi radi
     * zgradili {@code ExcelOrdinacije} iz že vnaprej prenešene preglednice.
     *
     * @param urlPreglednice Pot do {@code .xls} daoteteke aktivnih
     *                       zobozdravnikov.
     */
    public ExcelOrdinacije(final URL urlPreglednice) {
        this(() -> urlPreglednice);
    }

    /**
     * Zgradi nov objekt tipa {@code ExcelOrdinacije} z potjo do {@code .xls}
     * datoteke.
     * <p>
     * Za konstruktor je uporaben za testiranje oziroma v primeru, ko bi radi
     * zgradili {@code ExcelOrdinacije} iz že vnaprej prenešene preglednice.
     *
     * @param urlPreglednice Pot do {@code .xls} daoteteke aktivnih
     *                       zobozdravnikov.
     */
    public ExcelOrdinacije(final SvederUrl urlPreglednice) {
        this.urlPreglednice = urlPreglednice;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException Če pride do napake pri branju {@code .xls} datoteke.
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public List<Ordinacija> ordinacije() throws IOException {
        try (
            Workbook wb =
                WorkbookFactory.create(urlPreglednice.url().openStream())
        ) {
            return StreamSupport
                .stream(wb.getSheetAt(0).spliterator(), false)
                .filter(vrstica ->
                    vrstica.getCell(STOLPEC_OE_IZVAJALCA) != null
                    &&
                    vrstica.getCell(STOLPEC_OE_IZVAJALCA)
                           .getCellType()
                           .equals(CellType.NUMERIC)
                    &&
                    new NumericnaCelica(
                        vrstica,
                        STOLPEC_OE_IZVAJALCA
                    ).intValue() % MODUL != MAGIC_OE_SKUPNO
                    &&
                    new NumericnaCelica(
                        vrstica,
                        STOLPEC_ZDRAVNIK_SIFRA
                    ).intValue() > 0
                    &&
                    new NumericnaCelica(
                        vrstica,
                        STOLPEC_IZVAJALEC
                    ).intValue() > 0
                )
                .map(
                    (vrstica) -> new Ordinacija() {
                        @Override
                        public Sifra izvajalec() {
                            return
                                new ExcelSifra(
                                    vrstica,
                                    STOLPEC_IZVAJALEC
                                );
                        }
                        @Override
                        public Dejavnost dejavnost() {
                            Sifra sifra = new ExcelSifra(vrstica, STOLPEC_DEJAVNOST);
                            for (Dejavnost dejavnost : Dejavnost.values()) {
                                if (dejavnost.sifra().vrednost().equals(sifra.vrednost())) {
                                    return dejavnost;
                                }
                            }
                            throw new RuntimeException(
                                String.format(
                                    "Neveljavna šifra dejavnosti: \"%s\"",
                                    sifra
                                )
                            );
                        }
                        @Override
                        public Zdravnik zdravnik() {
                            return
                                new Zdravnik() {
                                    @Override
                                    public Sifra sifra() {
                                        return
                                            new ExcelSifra(
                                                vrstica,
                                                STOLPEC_ZDRAVNIK_SIFRA
                                            );
                                    }
                                    @Override
                                    public String imeInPriimek() {
                                        return
                                            vrstica.getCell(STOLPEC_ZDRAVNIK_IME_PRIIMEK)
                                                   .getStringCellValue()
                                                   .strip()
                                                   .replaceAll(" +", " ");
                                    }
                                };
                        }
                        @Override
                        public Number doseganjePovprecja() {
                            return
                                new NumericnaCelica(
                                    vrstica,
                                    STOLPEC_DOSEGANJE_POVP
                                );
                        }
                        @Override
                        public Lokacije lokacije() {
                            return
                                new LokacijeZdravstvenihZavodov(
                                    new Izvajalec(izvajalec()),
                                    izvajalec(),
                                    dejavnost().sifra(),
                                    zdravnik().sifra()
                                );
                        }
                    }
                )
                .collect(Collectors.toList());
        }
    }
}
