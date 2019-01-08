package com.github.glusk2.sveder.excel;

import org.apache.poi.ss.usermodel.Row;

/** Ovitek za numerične vrednosti celic v Excelovih preglednicah. */
public final class NumericnaCelica extends Number {

    /** Vrstica celice. */
    private final Row vrstica;
    /** Indeks celice v vrstici. */
    private final int indeksStolpca;

    /**
     * Zgradi novo celico iz vrstice in indeksa stolpca.
     *
     * @param vrstica Vrstica v kateri se nahaja celica.
     * @param indeksStolpca Prvi element se nahaja na indeksu {@code 0}.
     */
    public NumericnaCelica(final Row vrstica, final int indeksStolpca) {
        this.vrstica = vrstica;
        this.indeksStolpca = indeksStolpca;
    }

    /** {@inheritDoc} */
    @Override
    public double doubleValue() {
        return vrstica.getCell(indeksStolpca).getNumericCellValue();
    }

    /** {@inheritDoc} */
    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    /** {@inheritDoc} */
    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    /** {@inheritDoc} */
    @Override
    public long longValue() {
        return (long) doubleValue();
    }
}
