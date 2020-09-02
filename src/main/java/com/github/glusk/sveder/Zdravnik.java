package com.github.glusk.sveder;

/** Zobozdravnik v sklopu javne mreže. */
public interface Zdravnik {
    /**
     * Šifra zobozdravnika.
     *
     * @return pozitivno celo število, omejeno na 5 mest
     */
    Number sifra();
    /**
     * Ime in priimek zobozdravnika.
     *
     * @return ime in priimek zobozdravnika z velikimi tiskanimi črkami
     */
    String imeInPriimek();
}
