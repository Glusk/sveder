package com.github.glusk.sveder.excel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Test;

public final class TestJeVrsticaVeljavna {
    @Test
    public void ceNiDatumaZacetkaVeljavnostiNiVeljavna() {
        Row vrstica = new HSSFWorkbook()
            .createSheet()
            .createRow(0)
            .createCell(0, CellType.BLANK)
            .getRow();
        assertFalse(new JeVrsticaVeljavna(vrstica, 0, 1).test());
    }
    @Test
    public void ceNiDatumaKoncaVeljavnostiJeVeljavna() {
        Row vrstica = new HSSFWorkbook()
            .createSheet()
            .createRow(0);
        vrstica.createCell(0, CellType.NUMERIC)
               .setCellValue(
                   Date.from(Instant.now().minus(1, ChronoUnit.DAYS))
               );
        vrstica.createCell(1, CellType.BLANK);
        assertTrue(new JeVrsticaVeljavna(vrstica, 0, 1).test());
    }
    @Test
    public void ceJeTrenutniDatumMedZacetkomInKoncemJeVeljavna() {
        Row vrstica = new HSSFWorkbook()
            .createSheet()
            .createRow(0);
        vrstica.createCell(0, CellType.NUMERIC)
               .setCellValue(
                   Date.from(Instant.now().minus(1, ChronoUnit.DAYS))
               );
        vrstica.createCell(1, CellType.NUMERIC)
               .setCellValue(
                   Date.from(Instant.now().plus(1, ChronoUnit.DAYS))
               );
        assertTrue(new JeVrsticaVeljavna(vrstica, 0, 1).test());
    }
    @Test
    public void nizJeVeljavenDatum() {
        Row vrstica = new HSSFWorkbook()
            .createSheet()
            .createRow(0);
        vrstica.createCell(0, CellType.STRING)
               .setCellValue("01.01.2019");
        vrstica.createCell(1, CellType.BLANK);
        assertTrue(new JeVrsticaVeljavna(vrstica, 0, 1).test());
    }
    @Test
    public void vrzeIzjemoCeJeNizJeNeveljavenDatum() {
        Row vrstica = new HSSFWorkbook()
            .createSheet()
            .createRow(0);
        vrstica.createCell(0, CellType.STRING)
               .setCellValue("01012019");
        vrstica.createCell(1, CellType.BLANK);
        assertThrows(DateTimeParseException.class, () ->
            new JeVrsticaVeljavna(vrstica, 0, 1).test()
        );
    }
}
