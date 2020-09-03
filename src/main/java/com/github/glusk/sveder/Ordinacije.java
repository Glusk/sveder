package com.github.glusk.sveder;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

/** Zobozdravstvene ordinacije v sklopu javne mreže. */
public interface Ordinacije extends SvederTip {
    /**
     * Seznam zobozdravstvenih ordinacij v sklopu javne mreže.
     *
     * @return Seznam zobozdravstvenih ordinacij v sklopu javne mreže.
     * @throws IOException Če pride do napake pri branju podatkov.
     */
    List<Ordinacija> ordinacije() throws IOException;

    @Override
    default JSONObject json() throws IOException {
        JSONObject rezultat = new JSONObject();
        for (Ordinacija o : ordinacije()) {
            rezultat.put("ordinacija", o.json());
        }
        return rezultat;
    }
}
