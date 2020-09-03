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
    ZDRAVLJENJE_ODRASLI("404101"),
    /** ZOBOZDR. DEJAVNOST-ZDRAVLJENJE MLADINE */
    ZDRAVLJENJE_MLADINA("404103"),
    /** ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ŠTUDENTOV */
    ZDRAVLJENJE_STUDENTI("404105");

    /** 6-mesten niz - sifra dejavnosti. */
    private final String sifra;

    /**
     * Zgradi novo Dejavnost iz sifre.
     *
     * @param sifra 6-mesten niz - sifra dejavnosti
     */
    Dejavnost(final String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return sifra;
    }

    @Override
    public JSONObject json() {
        return new JSONObject().put("id_dejavnost", sifra)
                               .put("naziv", this);
    }
}
