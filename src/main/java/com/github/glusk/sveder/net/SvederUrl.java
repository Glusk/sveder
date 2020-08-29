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

    /** Ovojnica za Javin {@link java.net.URL} razred.*/
    final class UrlOvoj implements SvederUrl {
        /** Ovit url. */
        private final String url;
        /**
         * Zgradi nov {@code UrlOvoj} in ovije {@code url}.
         *
         * @param url url ki bo ovit
         */
        public UrlOvoj(final String url) {
            this.url = url;
        }

        /** {@inheritDoc} */
        @Override
        public URL url() throws IOException {
            return new URL(url);
        }
    }
}
