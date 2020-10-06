package com.github.glusk.sveder;

/**
 * Šifra lokacije izvajalca.
 * <p>
 * Šifra lokacije izvajalca je 12 mestna šifra, zgrajena iz cifer, in
 * definirana takole:
 * <pre>
 * &lt;sifra izvajalca&gt; | &lt;sifra lokacije&gt;
 * </pre>
 * pri čemer je {@code |} oznaka za konkatenacijo nizov. Če se dožini šifer
 * ne seštejeta v 12, vmesni prostor zapolnijo ničle.
 */
public final class SifraLokacijeIzvajalca implements Sifra {
    /** Dolžina šifre stolpca "Šifra lokacije". */
    private static final int DOLZINA_SIFRE = 12;
    /** Šifra izvajalca. */
    private final Sifra sifraIzvajalca;
    /** Šifra lokacije. */
    private final Sifra sifraLokacije;

    /**
     * Zgradi novo šifro lokacije izvajalca.
     *
     * @param sifraIzvajalca šifra izvajalca
     * @param sifraLokacije šifra lokacije
     */
    public SifraLokacijeIzvajalca(
        final Sifra sifraIzvajalca,
        final Sifra sifraLokacije
    ) {
        this.sifraIzvajalca = sifraIzvajalca;
        this.sifraLokacije = sifraLokacije;
    }

    @Override
    public String vrednost() {
        return
            sifraIzvajalca.vrednost()
          + "0".repeat(
                DOLZINA_SIFRE
              - sifraIzvajalca.vrednost().length()
              - sifraLokacije.vrednost().length()
            )
          + sifraLokacije.vrednost();
    }
}
