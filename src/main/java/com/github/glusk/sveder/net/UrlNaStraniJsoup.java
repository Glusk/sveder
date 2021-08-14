package com.github.glusk.sveder.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public final class UrlNaStraniJsoup implements SvederUrl {
    private final SpletnaStran stran;
    private final String predpona;
    private final String jsoupPoizvedba;

    /**
     *
     * @param stran stran na kateri se nahaja url
     * @param predpona opcijska predpona, ki jo dodamo rezultatu Jsoup
     *                 poizvedbe, da zgradimo ta url
     * @param jsoupPoizvedba Jsoup poizvedba s katero poiščemo ustrezen
     *                       element, ki mora vsebovati razred
     *                       "href", na strani. Glej:
     *                       https://jsoup.org/cookbook/extracting-data/selector-syntax
     *                       za več informacij o sintaksi.
     */
    public UrlNaStraniJsoup(
        final SpletnaStran stran,
        final String predpona,
        final String jsoupPoizvedba
    ) {
        this.stran = stran;
        this.predpona = predpona;
        this.jsoupPoizvedba = jsoupPoizvedba;
    }

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
