package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.github.glusk.sveder.Lokacija;
import com.github.glusk.sveder.Lokacije;
import com.github.glusk.sveder.Sifra;
import com.github.glusk.sveder.Urnik;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Lokacije ordinacije, izluščene iz preglednice zdravstvenega zavoda.
 */
public final class LokacijeZdravstvenegaZavoda implements Lokacije {
    /** Indeks stolpca "Lokacija" v listu "UrnikiIZV". */
    private static final int STOLPEC_LOKACIJA = 2;
    /** Indeks stolpca "Datum začetka veljavnosti" v listu "UrnikiIZV". */
    private static final int STOLPEC_ZACETEK_VELJAVNOSTI = 41;
    /** Indeks stolpca "Datum konca veljavnosti" v listu "UrnikiIZV". */
    private static final int STOLPEC_KONEC_VELJAVNOSTI = 43;

    /** Zavod s preglednico. */
    private final ZdravstveniZavod zavod;
    /** Šifra izvajalca ordinacije, katere lokacije iščemo. */
    private final Sifra sifraIzvajalca;
    /** Šifra dejavnosti ordinacije, katere lokacije iščemo. */
    private final Sifra sifraDejavnosti;
    /** Šifra zdravnika (nosilca) ordinacije, katere lokacije iščemo. */
    private final Sifra sifraZdravnika;

    /**
     * Zgradi lokacije zavoda iz zavoda in ključa ordinacije:
     * ({@code izvajalec}, {@code dejavnost}, {@code zdravnik}).
     *
     * @param zavod zavod s preglednico
     * @param sifraIzvajalca šifra izvajalca ordinacije, katere lokacije
     *                       iščemo
     * @param sifraDejavnosti šifra dejavnosti ordinacije, katere lokacije
     *                        iščemo
     * @param sifraZdravnika šifra zdravnika (nosilca) ordinacije, katere
     *                       lokacije iščemo
     */
    public LokacijeZdravstvenegaZavoda(
        final ZdravstveniZavod zavod,
        final Sifra sifraIzvajalca,
        final Sifra sifraDejavnosti,
        final Sifra sifraZdravnika
    ) {
        this.zavod = zavod;
        this.sifraIzvajalca = sifraIzvajalca;
        this.sifraDejavnosti = sifraDejavnosti;
        this.sifraZdravnika = sifraZdravnika;
    }

    @Override
    public List<Lokacija> lokacije() throws IOException {
        try (
            Workbook preglednica = zavod.preglednica()
        ) {
            if (preglednica.getSheet("UrnikiIZV") == null) {
                throw
                    new IOException(
                        "Preglednica zavoda ne vsebuje lista \"UrnikikiIZV\"."
                    );
            }
            return
                StreamSupport.stream(
                    preglednica.getSheet("UrnikiIZV")
                               .spliterator(),
                    false
                )
                .filter(vrstica ->
                    new JeVrsticaVeljavna(
                        vrstica,
                        STOLPEC_ZACETEK_VELJAVNOSTI,
                        STOLPEC_KONEC_VELJAVNOSTI
                    ).test()
                    &&
                    new KljucOrdinacije(
                        sifraIzvajalca,
                        sifraDejavnosti,
                        sifraZdravnika
                    ).test(vrstica)
                )
                .map(vrstica ->
                    new ExcelSifra(
                        vrstica,
                        STOLPEC_LOKACIJA
                    )
                )
                .distinct()
                .map(sifraLokacije ->
                    new Lokacija() {
                        @Override
                        public Sifra sifra() {
                            return sifraLokacije;
                        }
                        @Override
                        public Urnik urnik() throws IOException {
                            return
                                new ExcelUrnik(
                                    zavod,
                                    sifraIzvajalca,
                                    sifraDejavnosti,
                                    sifraZdravnika,
                                    sifraLokacije
                                );
                        }
                    }
                )
                .collect(Collectors.toList());
        }
    }
}
