package com.github.glusk.sveder;

import java.util.List;

/** Zobozdravstvene ordinacije s koncesijo. */
public interface Ordinacije {
    /**
     * Seznam zobozdravstvenih ordinacij s koncesijo.
     *
     * @return Seznam zobozdravstvenih ordinacij s koncesijo.
     * @throws Exception Če pride do napake pri branju podatkov.
     */
    List<Ordinacija> ordinacije() throws Exception;
}
