package com.github.glusk.sveder.excel;

import static org.junit.Assert.assertEquals;

import com.github.glusk.sveder.net.SpletnaStran;
import com.github.glusk.sveder.net.SvederUrl;

import org.junit.Test;

public final class TestIzvajalec {
    @Test
    public void najdeVseZavodeNaStrani() throws Exception {
        assertEquals(
            250,
            new Izvajalec(
                new SpletnaStran(
                    new SvederUrl.UrlOvoj(
                        this.getClass()
                            .getResource("seznamZavodov.txt")
                            .toString()
                    )
                )
            ).zavodi().size()
        );
    }
}
