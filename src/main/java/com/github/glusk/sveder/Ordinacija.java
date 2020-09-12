package com.github.glusk.sveder;

import java.io.IOException;

import org.json.JSONObject;

/** Zobozdravstvena ordinacija v sklopu javne mreže. */
public interface Ordinacija extends SvederTip {
    /**
     * Šifra izvajalca zdravstvenih storitev pri katerem je ordinacija
     * registrirana.
     *
     * @return Pozitivno celo število, omejeno na 5 mest.
     */
    Number izvajalec();
    /**
     * Vrne dejavnost, ki se izvaja v tej ordinaciji.
     *
     * @return {@link Dejavnost} te ordinacije
     */
    Dejavnost dejavnost();
    /**
     * Zobozdravnik, ki je nosilec te ordinacije.
     *
     * @return zobozdravnik, ki je nosilec te ordinacije
     */
    Zdravnik zdravnik();
    /**
     * Količnik doseganja povprečja ordinacije.
     *
     * Do vrednosti 110.0 je zobozdravnik še dolžan sprejemati nove paciente.
     *
     * @return Pozitivo decimalno število.
     */
    Number doseganjePovprecja();

    /**
     * Vrne vse {@link Lokacije} te ordinacije.
     *
     * @return vrne lokacije
     */
    Lokacije lokacije();

    @Override
    default JSONObject json() throws IOException {
        return new JSONObject().put("ordinacija",
            new JSONObject()
            .put("id_izvajalec", izvajalec().intValue())
            .put("zdravnik", zdravnik().json())
            .put("dejavnost", dejavnost().json())
            .put("doseganje_povprecja", doseganjePovprecja().doubleValue())
            .put("lokacije", lokacije().json())
        );
    }
}
