package com.github.glusk.sveder.iskanje;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.github.glusk.sveder.Dejavnost;
import com.github.glusk.sveder.Ordinacija;
import com.github.glusk.sveder.Ordinacije;

/**
 * Ordinacije, ki ustrezajo iskalnemu ključu:
 * <em>ime zdravnika, šifra dejavnosti</em>.
 */
public class OrdinacijeImeDejavnost implements Ordinacije {
    /** Baza podatkov po kateri filtriramo. */
    private Ordinacije baza;
    /** Dejavnost iskane ordinacije. */
    private Dejavnost dejavnost;
    /**
     * Regularni izraz, ki bo uporabljen za iskanje zobozdravnika po imenu
     * in priimku.
     */
    private String regexImePriimek;

    /**
     * Zgradi nov objekt tipa Ordinacije, ki so rezultat iskanja po ključu:
     * <em>ime zdravnika, šifra dejavnosti</em>.
     * <p>
     * Če ima zobozdravnik več imenov ali priimkov, vnesi samo eno ime in en
     * priimek.
     *
     * @param baza baza podatkov po kateri filtriramo
     * @param dejavnost dejavnost iskane ordinacije
     * @param ime ime zobozdravnika, nosilca iskane ordinacije, z velikimi
     *            začetnicami
     * @param priimek priimek zobozdravnika, nosilca iskane ordinacije, z
     *                velikimi začetnicami
     * @see Ordinacija#dejavnost()
     */
    public OrdinacijeImeDejavnost(
        final Ordinacije baza,
        final Dejavnost dejavnost,
        final String ime,
        final String priimek
    ) {
        this(
            baza,
            dejavnost,
            String.format(
                "(.*%s.*%s.*|.*%s.*%s.*)",
                ime, priimek, priimek, ime
            )
        );
    }

    /**
     * Zgradi nov objekt tipa Ordinacije, ki so rezultat iskanja po ključu:
     * <em>ime zdravnika, šifra dejavnosti</em>.
     *
     * @param baza baza podatkov po kateri filtriramo
     * @param dejavnost dejavnost iskane ordinacije
     * @param regexImePriimek regularni izraz, ki bo uporabljen za iskanje
     *                        zobozdravnika po imenu in priimku
     * @see Ordinacija#dejavnost()
     */
    public OrdinacijeImeDejavnost(
        final Ordinacije baza,
        final Dejavnost dejavnost,
        final String regexImePriimek
    ) {
        this.baza = baza;
        this.dejavnost = dejavnost;
        this.regexImePriimek = regexImePriimek;
    }

    @Override
    public final List<Ordinacija> ordinacije() throws IOException {
        return baza
            .ordinacije()
            .stream()
            .filter(o ->
                o.dejavnost().equals(dejavnost)
                &&
                o.zdravnik()
                 .imeInPriimek()
                 .matches(regexImePriimek)
            )
            .collect(Collectors.toList());
    }
}
