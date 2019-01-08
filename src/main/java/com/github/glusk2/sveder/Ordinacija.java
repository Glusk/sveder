package com.github.glusk2.sveder;

/** Zobozdravstvena ordinacija s koncesijo. */
public interface Ordinacija {
    /**
     * Šifra izvajalca zdravstvenih storitev pri katerem je ordinacija
     * registrirana.
     *
     * Posebne šifre, ki ne predstavljajo izvajlcev: {@code XX9999}
     * @return Pozitivno celo število, omejeno na 6 mest.
     */
    Number izvajalec();
    /**
     * Šifra zobozdravnika, nosilca ordinacije.
     *
     * @return Pozitivno celo število, omejeno na 5 mest.
     */
    Number zdravnik();
    /**
     * Količnik doseganja povprečja ordinacije.
     *
     * Do vrednosti 110.0 je zobozdravnik še dolžan sprejemati nove paciente s
     * koncesijo.
     * @return Pozitivo decimalno število.
     */
    Number doseganjePovprecja();
}
