package com.github.glusk2.sveder.excel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.glusk2.sveder.net.SpletnaStran;
import com.github.glusk2.sveder.net.SvederUrl;

public final class TestIzvajalec {
    @Test
    public void najdeVseZavodeNaStrani() throws Exception {
        assertEquals(
            250,
            new Izvajalec(
                new SpletnaStran(
                    new SvederUrl.UrlOvoj(
                        this.getClass()
                            .getResource("seznamZasebnikov.html")
                            .toString()
                    )
                )
            ).zavodi().size()
        );
    }
}
