package com.github.glusk.sveder;

import java.util.List;

/** Zobozdravstvene ordinacije v sklopu javne mreže. */
public interface Ordinacije {
    /**
     * Seznam zobozdravstvenih ordinacij v sklopu javne mreže.
     *
     * @return Seznam zobozdravstvenih ordinacij v sklopu javne mreže.
     * @throws Exception Če pride do napake pri branju podatkov.
     */
    List<Ordinacija> ordinacije() throws Exception;
}
