package com.github.glusk.sveder;

import org.json.JSONObject;

/** Zobozdravnik v sklopu javne mreže. */
public interface Zdravnik extends SvederTip {
    /**
     * Šifra zobozdravnika.
     *
     * @return pozitivno celo število, omejeno na največ 5 mest
     */
    Sifra sifra();
    /**
     * Ime in priimek zobozdravnika.
     *
     * @return ime in priimek zobozdravnika z velikimi tiskanimi črkami
     */
    String imeInPriimek();

    @Override
    default JSONObject json() {
        return new JSONObject()
            .put("sifra", sifra().vrednost())
            .put("ime_priimek", imeInPriimek());
    }
}
