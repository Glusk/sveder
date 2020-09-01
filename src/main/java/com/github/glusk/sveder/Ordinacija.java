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
     * Šifra zobozdravnika, nosilca ordinacije.
     *
     * @return Pozitivno celo število, omejeno na 5 mest.
     */
    Number zdravnik();
    /**
     * Količnik doseganja povprečja ordinacije.
     *
     * Do vrednosti 110.0 je zobozdravnik še dolžan sprejemati nove paciente.
     *
     * @return Pozitivo decimalno število.
     */
    Number doseganjePovprecja();
}
