package com.github.glusk.sveder.excel;

import com.github.glusk.sveder.Sifra;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/** Šifra Excel preglednic. */
public final class ExcelSifra implements Sifra {
    /** Vrstica v kateri je ta šifra. */
    private Row vrstica;
    /** Indeks stolpca v vrstici s to šifro. */
    private int indeksStolpca;

    /**
     * Zgradi novo šifro iz vrstice in indeksa stolpca celice v vrstici s to
     * šifro.
     *
     * @param vrstica vrstica v kateri je ta šifra
     * @param indeksStolpca indeks stolpca v vrstici s to šifro
     */
    public ExcelSifra(final Row vrstica, final int indeksStolpca) {
        this.vrstica = vrstica;
        this.indeksStolpca = indeksStolpca;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Veljavne celice šifre so tipa {@code NUMERIC} in {@code STRING}. Če
     * celica šifre ni veljavnega tipa, ta metoda vrže izjemo.
     *
     * @throws IllegalArgumentException če celica te šifre ni veljavnega tipa
     */
    @Override
    public String vrednost() {
        Cell celica = vrstica.getCell(indeksStolpca);
        switch (celica.getCellType()) {
            case NUMERIC:
                return
                    Integer.toString(
                        new NumericnaCelica(
                            vrstica,
                            indeksStolpca
                        ).intValue()
                    );
            case STRING:
                return celica.getStringCellValue().strip();
            default:
                throw new IllegalArgumentException(
                    String.format(
                        "Šifra ne more biti tipa: %s", celica.getCellType()
                    )
                );
        }
    }
}
