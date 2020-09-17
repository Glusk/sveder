package com.github.glusk.sveder.net;

import java.io.IOException;
import java.net.URL;

/**
 * Razširitev Javinega razreda {@link java.net.URL}.
 * <p>
 * Omogoča bolj deklarativen model programiranja. Lahko rečemo, da nek
 * objekt <em>je</em> URL, namesto da bi vedno znova rekli <em>vrni mi</em>
 * URL.
 */
public interface SvederUrl {
    /**
     * Vrne URL.
     *
     * @return ta url
     * @throws IOException če pride do napake pri branju URL-ja
     * @throws java.net.MalformedURLException če url ni veljaven
     */
    URL url() throws IOException;
}
