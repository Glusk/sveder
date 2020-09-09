package com.github.glusk.sveder.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/** Test berljivosti Excel celice. */
public final class JeNepraznaCelica {
    /** Celica, katere berljivost testiramo. */
    private final Cell celica;

    /**
     * Zgradi nov test iz Excel vrstice in indeksa stolpca celice
     * v vrstici.
     *
     * @param vrstica vrstica, v kateri se celica nahaja
     * @param indeksStolpca indeks stolpca celice v vrstici
     */
    public JeNepraznaCelica(final Row vrstica, final int indeksStolpca) {
        this(vrstica.getCell(indeksStolpca));
    }

    /**
     * Zgradi nov test iz Excel celice.
     *
     * @param celica celica, katere berljivost testiramo
     */
    public JeNepraznaCelica(final Cell celica) {
        this.celica = celica;
    }

    /**
     * Preveri, ali celico lahko preberemo. Celico lahko preberemo če:
     * <ul>
     *   <li>je različna od {@code null}</li>
     *   <li>ni tipa {@code CellType.BLANK}</li>
     *   <li>
     *     če je tipa {@code CellType.STRING} in
     *     {@code celica.getStringCellValue()} ne vrača {@code null}
     *   </li>
     * </ul>
     *
     * @return {@code true}, če celico lahko preberemo
     */
    public boolean test() {
        if (celica == null) {
            return false;
        }
        switch (celica.getCellType()) {
            case BLANK:
                return false;
            case STRING:
                return celica.getStringCellValue() != null;
            default: return true;
        }
    }
}
