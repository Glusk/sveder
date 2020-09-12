package com.github.glusk.sveder.excel;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;

/** Vrstica Excelove preglednice. */
@FunctionalInterface
public interface ExcelVrstica {
    /**
     * Vrne vrstico Excel preglednice.
     *
     * @return to vrstico
     * @throws IOException če pride do napake pri branju vrstice
     */
    Row vrstica() throws IOException;
}
