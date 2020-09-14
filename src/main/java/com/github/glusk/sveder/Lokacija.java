package com.github.glusk.sveder;

import java.io.IOException;

import org.json.JSONObject;

/** Lokacija zdravstvene ordinacije. */
public interface Lokacija extends SvederTip {
    /**
     * Vrne šifro te lokacije.
     *
     * @return 6-mestna šifra lokacije
     */
    Sifra sifra();
    /**
     * Vrne urnik zdravstvene ordinacije na tej lokaciji.
     *
     * @return nov objekt tipa Urnik
     * @throws IOException ce pride do napake pri branju podatkov urnika
     */
    Urnik urnik() throws IOException;
    @Override
    default JSONObject json() throws IOException {
        return new JSONObject()
            .put("sifra", sifra().vrednost())
            .put("urnik", urnik().json());
    }
}
