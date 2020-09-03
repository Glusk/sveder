package com.github.glusk.sveder;

import org.json.JSONObject;

/** Štarševki tip iz katerega dedujejo vsi tipi Svedra. */
public interface SvederTip {
    /**
     * Vrne tip v JSON formatu.
     *
     * @return json izpis tega tipa
     */
    JSONObject json();
}
