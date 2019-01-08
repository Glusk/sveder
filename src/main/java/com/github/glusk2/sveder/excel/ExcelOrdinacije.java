package com.github.glusk2.sveder.excel;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.glusk2.sveder.Ordinacija;
import com.github.glusk2.sveder.Ordinacije;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Ordinacije iz {@code .xls} preglednice.
 *
 * @see <a href="https://partner.zzzs.si/wps/portal/portali/aizv/zdravstvene_storitve/izbira_osebnega_zdravnika/sezn_akt_zob_za_odrasle_in_mladino">
 *      ZZZS - Seznam aktivnih zobozdravnikov za odrasle in mladino</a>
 */
public final class ExcelOrdinacije implements Ordinacije {

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
    /**
     * Število vrstic na začetku lista, ki niso podatki (oznake stolpcev,
     * podatki o datumu objave, ...).
     */
    private static final int ST_META_VRSTIC = 6;
    /** Index stolpca "Šifra OE izvajalca". */
    private static final int STOLPEC_OE_IZVAJALCA = 0;
    /** Index stolpca "Šifra izvajalca". */
    private static final int STOLPEC_IZVAJALEC = 2;
    /** Index stolpca "Šifra zdravnika". */
    private static final int STOLPEC_ZDRAVNIK = 6;
    /** Index stolpca "Doseganje povprečja". */
    private static final int STOLPEC_DOSEGANJE_POVP = 18;

    /** URL lokacije {@code .xls} datoteke. */
    private final URL potDoPreglednice;

    /**
     * Zgradi nov objekt tipa {@code ExcelOrdinacije} z potjo do {@code .xls}
     * datoteke.
     *
     * @param urlPreglednice Pot do {@code .xls} daoteteke aktivnih
     *                       zobozdravnikov.
     */
    public ExcelOrdinacije(final URL urlPreglednice) {
        this.potDoPreglednice = urlPreglednice;
    }

    /**
     * {@inheritDoc}
     *
     * @throws Exception Če pride do napake pri branju {@code .xls} datoteke.
     */
    @Override
    public List<Ordinacija> ordinacije() throws Exception {
        try (
            Workbook wb = WorkbookFactory.create(potDoPreglednice.openStream())
        ) {
            return StreamSupport
                .stream(wb.getSheetAt(0).spliterator(), false)
                .skip(ST_META_VRSTIC)
                .filter(vrstica ->
                    new NumericnaCelica(
                        vrstica,
                        STOLPEC_OE_IZVAJALCA
                    ).intValue() % MODUL != MAGIC_OE_SKUPNO
                    &&
                    new NumericnaCelica(
                        vrstica,
                        STOLPEC_ZDRAVNIK
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
                        public Number izvajalec() {
                            return
                                new NumericnaCelica(
                                    vrstica,
                                    STOLPEC_IZVAJALEC
                                );
                        }
                        @Override
                        public Number zdravnik() {
                            return
                                new NumericnaCelica(
                                    vrstica,
                                    STOLPEC_ZDRAVNIK
                                );
                        }
                        @Override
                        public Number doseganjePovprecja() {
                            return
                                new NumericnaCelica(
                                    vrstica,
                                    STOLPEC_DOSEGANJE_POVP
                                );
                        }
                    }
                )
                .collect(Collectors.toList());
        }
    }
}
