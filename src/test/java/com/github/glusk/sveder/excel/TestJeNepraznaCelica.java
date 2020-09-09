package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;

public final class TestJeNepraznaCelica {
    @Test
    public void nullCelicaJePraznaCelica() {
        assertFalse(new JeNepraznaCelica(null).test());
    }
    @Test
    public void blankCelicaJePrazna() {
        Cell celica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.BLANK);
        assertFalse(new JeNepraznaCelica(celica).test());
    }
    @Test
    public void nullStringCelicaJePrazna() {
        Cell celica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.STRING);
        celica.setCellValue((String) null);
        assertFalse(new JeNepraznaCelica(celica).test());
    }
}
