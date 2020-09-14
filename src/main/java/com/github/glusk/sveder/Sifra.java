package com.github.glusk.sveder;

/** Šifra v Svedru. */
@FunctionalInterface
public interface Sifra {
    /**
     * Vrne vrednost te šifre kot niz.
     *
     * @return vrne vrednost te šifre kot niz
     */
    String vrednost();
}
