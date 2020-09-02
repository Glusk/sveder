package com.github.glusk.sveder;

/** Zobozdravstvena ordinacija v sklopu javne mreže. */
public interface Ordinacija {
    /**
     * Šifra izvajalca zdravstvenih storitev pri katerem je ordinacija
     * registrirana.
     *
     * @return Pozitivno celo število, omejeno na 5 mest.
     */
    Number izvajalec();
    /**
     * Šifra dejavnosti.
     * <p>
     * <ul>
     *   <li>404101 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ODRASLIH</li>
     *   <li>404103 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE MLADINE</li>
     *   <li>404105 - ZOBOZDR. DEJAVNOST-ZDRAVLJENJE ŠTUDENTOV</li>
     * </ul>
     * @return Pozitivno celo število, omejeno na 6 mest.
     */
    String dejavnost();
    /**
     * Zobozdravnik, ki je nosilec te ordinacije.
     *
     * @return zobozdravnik, ki je nosilec te ordinacije
     */
    Zdravnik zdravnik();
    /**
     * Količnik doseganja povprečja ordinacije.
     *
     * Do vrednosti 110.0 je zobozdravnik še dolžan sprejemati nove paciente.
     *
     * @return Pozitivo decimalno število.
     */
    Number doseganjePovprecja();
}
