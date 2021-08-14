package com.github.glusk.sveder.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/** URL na spletni strani. */
public final class UrlNaStrani implements SvederUrl {
    /** Spletna stran na kateri se nahaja URL. */
    private final SpletnaStran stran;
    /** Opcijska predpona, ki jo pripnemo na začetek URL ujemanja. */
    private final String predpona;
    /** Regularni izraz - ključ po katerem iščemo URL {@link #stran}i. */
    private final String jsoupPoizvedba;

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
        this(stran, "", regexUrl);
    }

    /**
     * Zgradi nov URL, ki se ujema z {@code regexUrl} na tej {@code stran}i.
     *
     * @param stran stran na kateri se nahaja URL
     * @param predpona opcijska predpona, ki jo pripnemo na začetek URL ujemanja
     * @param regexUrl regularni izraz - ključ po katerem iščemo URL
     */
    public UrlNaStrani(
        final SpletnaStran stran,
        final String predpona,
        final String jsoupPoizvedba
    ) {
        this.stran = stran;
        this.predpona = predpona;
        this.jsoupPoizvedba = jsoupPoizvedba;
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
        Elements result =
            Jsoup.parse(stran.vsebina())
                 .select(jsoupPoizvedba);
        if (result.isEmpty()) {
            throw
                new FileNotFoundException(
                    String.format(
                        "Na spletni strani \"%s\" ni URL-ja,"
                        + "ki bi ustrezal Jsoup poizvedbi: %s",
                        stran.urlNaslov(),
                        jsoupPoizvedba
                    )
                );
        }
        return new URL(predpona + result.first().attr("href").strip());
    }
}
