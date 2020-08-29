package com.github.glusk.sveder.excel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.github.glusk.sveder.net.SvederUrl;

import org.junit.Test;

public final class TestZdravstveniZavod {
    @Test
    public void najdeZdravnikaVPreglednici() throws IOException {
        assertEquals(
            7809,
            new NumericnaCelica(
                new ZdravstveniZavod(
                    new SvederUrl.UrlOvoj(
                        this.getClass()
                            .getResource("ZOB_ZAVOD_D_IN_G_KOPER_UrnCD.xlsx")
                            .toString()
                    )
                )
                .preglednicaZZZS()
                .getSheet("NosilciTimaIZV")
                .getRow(1),
                0
            ).intValue()
        );
    }
}
