package com.github.glusk.sveder;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Urnik ordinacije na lokaciji.
 *
 * @see Lokacija
 * @see Ordinacija
 */
public interface Urnik extends SvederTip {
    /**
     * Tabela velikosti 6 s tromestnimi {@code String} konstantami delovnih dni
     * ("pon", "tor", "sre", "cet", "pet", "sob").
     */
    String[] DELOVNI_DNEVI = {
        "pon", "tor", "sre", "cet", "pet", "sob"
    };
    /**
     * Vrne 2-D tabelo z urnikom.
     * <p>
     * Tabela ima 6 vrstic za vsak delovni dan od ponedeljka do sobote.
     * Prvi stolpec v vsaki vrstici je čas začetka dela ordinacije za tisti
     * dan, drugi pa čas konca.
     *
     * @return 2-D tabela urnika
     * @throws IOException če pride do napake pri branju podatkov
     */
    String[][] urnik() throws IOException;
    /**
     * Opombe urnika.
     * <p>
     * Opombe vsebujejo dodatne podatke o urniku, npr. odmor za malico.
     *
     * @return niz z opombami
     * @throws IOException če pride do napake pri branju podatkov
     */
    String opombe() throws IOException;

    @Override
    default JSONObject json() throws IOException {
        JSONArray tabela = new JSONArray();
        String[][] urnik = urnik();
        for (int i = 0; i < DELOVNI_DNEVI.length; i++) {
            tabela.put(new JSONObject()
                .put("dan", DELOVNI_DNEVI[i])
                .put("od", urnik[i][0])
                .put("do", urnik[i][1])
            );
        }
        return new JSONObject().put("razpored", tabela)
                               .put("opombe", opombe());
    }
}
