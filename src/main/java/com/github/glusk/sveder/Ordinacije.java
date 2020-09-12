package com.github.glusk.sveder;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
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
        JSONArray ordinacije = new JSONArray();
        for (Ordinacija o : ordinacije()) {
            ordinacije.put(o.json());
        }
        return new JSONObject().put("ordinacije", ordinacije);
    }
}
