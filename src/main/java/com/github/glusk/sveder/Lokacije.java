package com.github.glusk.sveder;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

/**
 * Lokacije ordinacije.
 * <p>
 * Sveder dopušča možnost, da ima {@link Ordinacija} več lokacij. Treba bo še
 * preveriti, ali je v praksi res tako.
 */
public interface Lokacije extends SvederTip {
    /**
     * Vrne vse lokacije ordinacije.
     *
     * @return seznam lokacij neke ordinacije
     * @throws IOException če pride do napake pri branju podatkov
     */
    List<Lokacija> lokacije() throws IOException;
    @Override
    default JSONObject json() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
