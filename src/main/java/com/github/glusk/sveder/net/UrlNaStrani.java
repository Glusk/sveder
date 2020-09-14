package com.github.glusk.sveder.net;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.github.glusk.sveder.orodja.RegularniIzraz;

/** URL na spletni strani. */
public final class UrlNaStrani implements SvederUrl {
    /** Spletna stran na kateri se nahaja URL. */
    private final SpletnaStran stran;
    /** Opcijska predpona, ki jo pripnemo na začetek URL ujemanja. */
    private final String predpona;
    /** Regularni izraz - ključ po katerem iščemo URL {@link #stran}i. */
    private final String regexUrl;
    /** Opcijska pripona, ki jo pripnemo na konec URL ujemanja. */
    private final String pripona;

    /**
     * Ekvivalentno klicu:
     * <br>
     * {@code new UrlNaStrani(stran, "", regexUrl, "")}.
     * <br>
     * Glej: {@link #UrlNaStrani(SpletnaStran, String, String, String)}
     *
     * @param stran stran na kateri se nahaja url
     * @param regexUrl regularni izraz - ključ po katerem iščemo url
     */
    public UrlNaStrani(
        final SpletnaStran stran,
        final String regexUrl
    ) {
        this(stran, "", regexUrl, "");
    }

    /**
     * Ekvivalentno klicu:
     * <br>
     * {@code new UrlNaStrani(stran, predpona, regexUrl, "")}.
     * <br>
     * Glej: {@link #UrlNaStrani(SpletnaStran, String, String, String)}
     *
     * @param stran stran na kateri se nahaja url
     * @param predpona opcijska predpona, ki jo dodamo ujemanju
     * @param regexUrl regularni izraz - ključ po katerem iščemo url
     */
    public UrlNaStrani(
        final SpletnaStran stran,
        final String predpona,
        final String regexUrl
    ) {
        this(stran, predpona, regexUrl, "");
    }

    /**
     * Zgradi nov URL, ki se ujema z {@code regexUrl} na tej {@code stran}i.
     *
     * @param stran stran na kateri se nahaja URL
     * @param predpona opcijska predpona, ki jo pripnemo na začetek URL ujemanja
     * @param regexUrl regularni izraz - ključ po katerem iščemo URL
     * @param pripona opcijska pripona, ki jo pripnemo na konec URL ujemanja
     */
    public UrlNaStrani(
        final SpletnaStran stran,
        final String predpona,
        final String regexUrl,
        final String pripona
    ) {
        this.stran = stran;
        this.predpona = predpona;
        this.regexUrl = regexUrl;
        this.pripona = pripona;
    }

    /**
     * Vrne URL na strani.
     * <p>
     * Z regularnim izrazom se poišče prvo ujemanje na strani. Ujemanju se
     * z leve strani doda predpona in z desne pripona. Metoda vrne dobljen
     * rezultat kot nov URL.
     *
     * @return novo instanco iskanega URL-ja na strani
     * @throws IOException če pride do napak pri branju strani če na strani
     * @throws FileNotFoundException če na strani ni URL naslova, ki se ujema z
     *                               {@code regexUrl}, podanim prek
     *                               konstruktorja
     * @throws java.net.MalformedURLException če URL ni veljaven
     */
    @Override
    public URL url() throws IOException {
        List<String> ujemanja =
            new RegularniIzraz(
                stran.vsebina(),
                regexUrl
            ).ujemanja();
        if (ujemanja.isEmpty()) {
            throw
                new FileNotFoundException(
                    String.format(
                        "Na spletni strani \"%s\" ni URL-ja,"
                      + "ki bi ustrezal vzorcu: %s",
                        stran.urlNaslov(),
                        regexUrl
                    )
                );
        }
        return
            new URL(
                predpona
              + ujemanja.get(0)
              + pripona
            );
    }
}
