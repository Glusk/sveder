package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;

public final class TestExcelSifra {
    @Test
    public void prebereNumericnoSifro() {
        Cell celica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.NUMERIC);
        celica.setCellValue(100);
        assertEquals(
            "100",
            new ExcelSifra(celica.getRow(), 0).vrednost()
        );
    }
    @Test
    public void prebereStringSifro() {
        Cell celica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.STRING);
        celica.setCellValue("abc");
        assertEquals(
            "abc",
            new ExcelSifra(celica.getRow(), 0).vrednost()
        );
    }
    @Test
    public void vrzeIzjemoZaNeveljavenTipCeliceSSifro() {
        Cell celica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.BLANK);
        assertThrows(IllegalArgumentException.class, () ->
            new ExcelSifra(celica.getRow(), 0).vrednost()
        );
    }
}
