package com.github.glusk.sveder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public final class TestUrnik {
    @Test
    public void pravilnoFormatiraPrazenUrnik() throws Exception {
        assertTrue(
            new JSONObject(
                new String(
                    this.getClass()
                        .getResource("urnik.json")
                        .openStream()
                        .readAllBytes(),
                    StandardCharsets.UTF_8
                )
            ).similar(
                new Urnik() {
                    @Override
                    public String[][] urnik() {
                        return new String[][] {
                            {"", ""},
                            {"", ""},
                            {"", ""},
                            {"", ""},
                            {"", ""},
                            {"", ""},
                        };
                    }
                    @Override
                    public String opombe() {
                        return "";
                    }
                }.json()
            )
        );
    }
}
