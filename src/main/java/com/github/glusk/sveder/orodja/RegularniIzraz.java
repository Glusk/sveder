package com.github.glusk.sveder.orodja;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Ovitek funkcionalnosti paketa {@link java.util.regex}. */
public final class RegularniIzraz implements Ujemanja {
    /** Niz po katerem iščemo. */
    private final String niz;
    /** Vzorec katerega iščemo v {@link #niz}u. */
    private final String vzorec;

    /**
     * Zgradi novo ujemanje kot regularni izraz s podanim nizom in vzorcem.
     *
     * @param niz niz v katerem iščemo ujemanja
     * @param vzorec ključ po katerem uščemo ujemanja v nizu
     */
    public RegularniIzraz(final String niz, final String vzorec) {
        this.niz = niz;
        this.vzorec = vzorec;
    }

    /**
     * Vrne rezultat evaluacije tega regularnega izraza.
     *
     * @return seznam ujemanj vzorca v nizu
     */
    @Override
    public List<String> ujemanja() {
        return Pattern.compile(vzorec).matcher(niz)
            .results()
            .map(MatchResult::group)
            .collect(Collectors.toList());
    }
}
