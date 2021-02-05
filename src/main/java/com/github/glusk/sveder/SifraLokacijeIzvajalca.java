package com.github.glusk.sveder;

/**
 * Šifra lokacije izvajalca.
 * <p>
 * Šifra lokacije izvajalca je definirana takole:
 * <pre>
 * &lt;sifra izvajalca&gt; | &lt;sifra lokacije&gt;
 * </pre>
 * pri čemer je {@code |} oznaka za konkatenacijo nizov.
 * <p>
 * Če {@code sifra lokacije} ni sedemmestna, je spredaj poravnana z ničlami.
 */
public final class SifraLokacijeIzvajalca implements Sifra {
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
            String.format(
                "%d%07d",
                Integer.parseInt(sifraIzvajalca.vrednost()),
                Integer.parseInt(sifraLokacije.vrednost())
            );
    }
}
