package com.github.glusk.sveder.excel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.github.glusk.sveder.net.SpletnaStran;
import com.github.glusk.sveder.net.SvederUrl;
import com.github.glusk.sveder.orodja.RegularniIzraz;

/**
 * Izvajalec zdravstvenih storitev.
 * <p>
 * Vsak izvajalec, ki je del javnega zdravstva, ima pri ZZZS-ju vpisanega
 * enega ali več <em>zdravstvenih zavodov</em>.
 *
 * @see {@link ZdravstveniZavod}
 */
public final class Izvajalec {
    /** Spletna stran izvajalca na ZZZS-ju. */
    private final SpletnaStran stranIzvajalca;

    /**
     * Zgradi izvajalca iz šifre (register izvajalcev, baza NIJZ).
     *
     * @param sifraIzvajalca celo število, omejeno na 5 mest.
     */
    public Izvajalec(final Number sifraIzvajalca) {
        this(
            new SpletnaStran(
                new SvederUrl.UrlOvoj(
                    String.format(
                        "http://www.zzzs.si/ZZZS/pao/izvajalci.nsf/WEBJavniZasebni?OpenView&RestrictToCategory=%d&OE=DI&tip=0&Count=700&n=1012",
                        sifraIzvajalca
                    )
                )
            )
        );
    }

    /**
     * Zgradi izvajalca iz spletne strani izvajalca na ZZZS-ju.
     *
     * @param stranIzvajalca spletna stran izvajalca na ZZZS-ju.
     */
    public Izvajalec(final SpletnaStran stranIzvajalca) {
        this.stranIzvajalca = stranIzvajalca;
    }

    /**
     * Vrne vse zavode tega izvajalca.
     *
     * @return seznam zavodov tega izvajalca
     * @throws IOException če pride do napake pri branju spletne strani
     *                     izvajalca
     */
    public List<ZdravstveniZavod> zavodi() throws IOException {
        return
            new RegularniIzraz(
                stranIzvajalca.vsebina(),
                "(?<=ZZZS št. )\\d{6,7}"
            ).ujemanja().stream()
            .map(zzzsSt -> new ZdravstveniZavod((zzzsSt)))
            .collect(Collectors.toList());
    }
}
