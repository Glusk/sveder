package com.github.glusk.sveder;

import java.io.IOException;

import org.json.JSONObject;

/** Starševki tip iz katerega dedujejo vsi tipi Svedra. */
public interface SvederTip {
    /**
     * Vrne tip v JSON formatu.
     *
     * @return json izpis tega tipa
     * @throws IOException če pride do napake pri branju podatkov tega Sveder
     *                     tipa
     */
    JSONObject json() throws IOException;
}
