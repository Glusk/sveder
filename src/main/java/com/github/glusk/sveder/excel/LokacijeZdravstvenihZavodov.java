package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.glusk.sveder.Lokacija;
import com.github.glusk.sveder.Lokacije;
import com.github.glusk.sveder.Sifra;

/**
 * Lokacije ordinacije, izluščene iz vseh zavodov izvajalca.
 * <p>
 * Tipično samo en zavod v hierarhiji izvajalca objavi preglednico
 * ({@link ZdravstveniZavod#preglednica()}). Vnaprej ne vemo v kateri,
 * zato ta razred pregleda vse.
 *
 * @see ZdravstveniZavod
 * @see Izvajalec
 */
public final class LokacijeZdravstvenihZavodov implements Lokacije {
    /** Izvajalec s seznamom zavodov. */
    private final Izvajalec izvajalec;
    /** Šifra izvajalca ordinacije, katere lokacije iščemo. */
    private final Sifra sifraIzvajalca;
    /** Šifra dejavnosti ordinacije, katere lokacije iščemo. */
    private final Sifra sifraDejavnosti;
    /** Šifra zdravnika (nosilca) ordinacije, katere lokacije iščemo. */
    private final Sifra sifraZdravnika;

    /**
     * Zgradi nove Lokacije iz izvajalca in ključa ordinacije:
     * ({@code izvajalec}, {@code dejavnost}, {@code zdravnik}).
     *
     * @param izvajalec izvajalec s seznamom zavodov
     * @param sifraIzvajalca šifra izvajalca ordinacije, katere lokacije
     *                       iščemo
     * @param sifraDejavnosti šifra dejavnosti ordinacije, katere lokacije
     *                        iščemo
     * @param sifraZdravnika šifra zdravnika (nosilca) ordinacije, katere
     *                       lokacije iščemo
     */
    public LokacijeZdravstvenihZavodov(
        final Izvajalec izvajalec,
        final Sifra sifraIzvajalca,
        final Sifra sifraDejavnosti,
        final Sifra sifraZdravnika
    ) {
        this.izvajalec = izvajalec;
        this.sifraIzvajalca = sifraIzvajalca;
        this.sifraDejavnosti = sifraDejavnosti;
        this.sifraZdravnika = sifraZdravnika;
    }

    @Override
    public List<Lokacija> lokacije() throws IOException {
        return izvajalec
            .zavodi()
            .stream()
            .flatMap(zavod -> {
                try {
                    return
                        new LokacijeZdravstvenegaZavoda(
                            zavod,
                            sifraIzvajalca,
                            sifraDejavnosti,
                            sifraZdravnika
                        )
                        .lokacije()
                        .stream();
                } catch (IOException e) {
                    return Collections.<Lokacija>emptyList().stream();
                }
            })
            .collect(Collectors.toList());
    }
}
