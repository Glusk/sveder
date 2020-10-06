package com.github.glusk.sveder;

import java.io.IOException;

/** Podrobnosti o posamezni loaciji ordinacije. */
public interface PodrobnostiLokacije {
    /**
     * Interna šifra lokacije.
     *
     * @return 12-mestna šifra
     * @throws IOException če pride do napake pri branju podatkov
     */
    Sifra sifra() throws IOException;
    /**
     * Naziv lokacije.
     *
     * @return niz z nazivom lokacije
     * @throws IOException če pride do napake pri branju podatkov
     */
    String naziv() throws IOException;
    /**
     * Naslov lokacije, ki naj bi bil sestavljen iz ulice in hišne številke.
     *
     * @return niz z naslovom lokacije
     * @throws IOException če pride do napake pri branju podatkov
     */
    String naslov() throws IOException;
    /**
     * Telefonska številka lokacije.
     *
     * @return niz s telefonsko številko lokacije
     * @throws IOException če pride do napake pri branju podatkov
     */
    String telefon() throws IOException;
    /**
     * Poštna številka lokacije.
     *
     * @return 4-mestna šifra pošte
     * @throws IOException če pride do napake pri branju podatkov
     */
    Sifra posta() throws IOException;
}
