package com.github.glusk.sveder.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public final class TestUrlNaStrani {
    @Test
    public void najdeUrlNaPreprostiStrani() throws IOException {
        assertEquals(
            "http://www.zzzs.si",
            new UrlNaStrani(
                new SpletnaStran(
                    () ->
                        this.getClass()
                            .getResource("stranZLinkom.txt")
                ),
                "a[href]"
            ).url().toString()
        );
    }

    @Test
    public void vrzeIzjemoCeNiUjemanj() throws IOException {
        assertThrows(FileNotFoundException.class, () ->
            new UrlNaStrani(
                new SpletnaStran(
                    () ->
                        this.getClass()
                            .getResource("stranZLinkom.txt")
                ),
                "div[href]"
            ).url()
        );
    }

    @Test
    public void najdeUrlPreglednice() throws IOException {
        assertEquals(
            "http://www.zzzs.si/zzzs/pao/pogizv.nsf/49912aaadaf85317c1256e4a004e2209/5422a3e779b6d0acc125829f00427cd2/$FILE/ZOB%20ZAVOD%20D%20IN%20G%20KOPER_Urn%C4%8CD.xlsx",
            new UrlNaStrani(
                new SpletnaStran(
                    () ->
                        this.getClass()
                            .getResource("stranZavoda.txt")
                ),
                "http://www.zzzs.si",
                "td.izvajal a[href]"
            ).url().toString()
        );
    }
}
