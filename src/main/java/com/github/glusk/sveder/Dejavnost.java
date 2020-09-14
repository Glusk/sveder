package com.github.glusk.sveder;

import org.json.JSONObject;

/**
 * Dejavnosti zobozdravstvenih ordinacij.
 * <p>
 * Sveder zazna naslednje vrste dejavnosti:
 * <ul>
 *   <li>404101 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ODRASLIH</li>
 *   <li>404103 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE MLADINE</li>
 *   <li>404105 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ŠTUDENTOV</li>
 * </ul>
 */
public enum Dejavnost implements SvederTip {
    /** ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ODRASLIH */
    ZDRAVLJENJE_ODRASLI(() -> "404101"),
    /** ZOBOZDR. DEJAVNOST-ZDRAVLJENJE MLADINE */
    ZDRAVLJENJE_MLADINA(() -> "404103"),
    /** ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ŠTUDENTOV */
    ZDRAVLJENJE_STUDENTI(() -> "404105");

    /** 6-mesten niz - sifra dejavnosti. */
    private final Sifra sifra;

    /**
     * Zgradi novo Dejavnost iz sifre.
     *
     * @param sifra 6-mesten niz - sifra dejavnosti
     */
    Dejavnost(final Sifra sifra) {
        this.sifra = sifra;
    }

    /**
     * Vrne sifro te dejavnosti.
     *
     * @return 6-mesten niz - sifra dejavnosti
     */
    public Sifra sifra() {
        return sifra;
    }

    @Override
    public JSONObject json() {
        return new JSONObject().put("sifra", sifra.vrednost())
                               .put("naziv", this);
    }
}
