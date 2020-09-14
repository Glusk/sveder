package com.github.glusk.sveder.net;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Spletna stran. */
public final class SpletnaStran {
    /** URL naslov te strani. */
    private final SvederUrl url;
    /** Kodiranje vsebine te strani. */
    private final Charset kodiranje;

    /**
     * Zgradi novo objekt tipa {@code SpletnaStran} s standardnim kodiranjem
     * UTF-8.
     * <p>
     * Enako kot:
     * <br>
     * {@code new SpletnaStran(url, StandardCharsets.UTF_8)}
     * Glej: {@link #SpletnaStran(SvederUrl, Charset)}
     * <br>
     *
     * @param url spletni naslov te strani
     */
    public SpletnaStran(final SvederUrl url) {
        this(url, StandardCharsets.UTF_8);
    }

    /**
     * Zgradi novo objekt tipa {@code SpletnaStran} s poljubnim kodiranjem.
     *
     * @param url spletni naslov
     * @param kodiranje kodiranje vsebine
     */
    public SpletnaStran(final SvederUrl url, final Charset kodiranje) {
        this.url = url;
        this.kodiranje = kodiranje;
    }

    /**
     * Vrne url te spletne strani kot niz.
     *
     * @return niz z URL-jem te strani
     * @throws IOException če pride do napake pri branju URL-ja
     * @throws java.net.MalformedURLException če url ni veljaven
     */
    public String urlNaslov() throws IOException {
        return url.url().toString();
    }

    /**
     * Vrne vsebino te spletne strani.
     *
     * @return vsebina kot niz
     * @throws IOException če pride do napak pri branju
     * @throws java.io.FileNotFoundException če stran ne obstaja
     */
    public String vsebina() throws IOException {
        try (Scanner sc = new Scanner(url.url().openStream(), kodiranje)) {
            return sc.useDelimiter("\\A").next();
        }
    }
}
