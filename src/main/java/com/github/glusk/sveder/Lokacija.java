package com.github.glusk.sveder;

import java.io.IOException;

import org.json.JSONObject;

/** Lokacija zdravstvene ordinacije. */
public interface Lokacija extends SvederTip {
    /**
     * Vrne pordobnosti te lokacije.
     *
     * @return vrne podrobnosti lokacije
     */
    PodrobnostiLokacije podrobnosti();
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
            .put("sifra", podrobnosti().sifra().vrednost())
            .put("naziv", podrobnosti().naziv())
            .put("naslov", podrobnosti().naslov())
            .put("posta", podrobnosti().posta().vrednost())
            .put("telefon", podrobnosti().telefon())
            .put("urnik", urnik().json());
    }
}
