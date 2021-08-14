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
    /**
     * Opcijska predpona, ki jo dodamo rezultatu Jsoup poizvedbe, da zgradimo
     * ta URL.
     */
    private final String predpona;
    /**
     * Jsoup poizvedba s katero poiščemo ustrezen element, ki mora vsebovati
     * atribut "href", na {@code stran}i. Glej:
     * https://jsoup.org/cookbook/extracting-data/selector-syntax
     * za več informacij o sintaksi.
     */
    private final String jsoupPoizvedba;

    /**
     * Ekvivalentno klicu:
     * <br>
     * {@code new UrlNaStrani(stran, "", jsoupPoizvedba)}.
     * <br>
     * Glej: {@link #UrlNaStrani(SpletnaStran, String, String)}
     *
     * @param stran stran na kateri se nahaja URL
     * @param jsoupPoizvedba Jsoup poizvedba s katero poiščemo ustrezen
     *                       element, ki mora vsebovati atribut
     *                       "href", na {@code stran}i. Glej:
     *                       https://jsoup.org/cookbook/extracting-data/selector-syntax
     *                       za več informacij o sintaksi.
     */
    public UrlNaStrani(
        final SpletnaStran stran,
        final String jsoupPoizvedba
    ) {
        this(stran, "", jsoupPoizvedba);
    }

    /**
     * Zgradi nov URL, ki je rezultat Jsoup poizvedbe na tej {@code stran}i.
     * <p>
     * Uporabi se vrednost atributa "href" prvega elementa rezultata. Skupaj s
     * predpono {@code predpona} tvorita ta URL.
     *
     * @param stran stran na kateri se nahaja URL
     * @param predpona opcijska predpona, ki jo dodamo rezultatu Jsoup
     *                 poizvedbe, da zgradimo ta URL
     * @param jsoupPoizvedba Jsoup poizvedba s katero poiščemo ustrezen
     *                       element, ki mora vsebovati atribut
     *                       "href", na {@code stran}i. Glej:
     *                       https://jsoup.org/cookbook/extracting-data/selector-syntax
     *                       za več informacij o sintaksi.
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
     *
     * @return novo instanco iskanega URL-ja na strani
     * @throws IOException če pride do napak pri branju strani
     * @throws FileNotFoundException če na strani ni URL naslova, ki se ujema z
     *                               nizom {@code jsoupPoizvedba}, podanim prek
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
