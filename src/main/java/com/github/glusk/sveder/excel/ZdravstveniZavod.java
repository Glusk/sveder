package com.github.glusk.sveder.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.github.glusk.sveder.net.SpletnaStran;
import com.github.glusk.sveder.net.SvederUrl;
import com.github.glusk.sveder.net.UrlNaStrani;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Zdravstveni zavod vpisan pri ZZZS-ju.
 * <p>
 * Mogoče <em>zdravstveni zavod</em> ni prava beseda; gre za neke vrste
 * organizacijsko enoto, ki jo ZZZS beleži za izvajalce zdravstvenih storitev.
 * <p>
 * Vsaka taka enota ima lahko eno ali več podenot, ki jih ZZZS imenuje
 * <em>izpostava</em> oziroma <em>območna enota</em>. Sveder ne dela razlik
 * med <em>enoto</em> in <em>podenoto</em> - oboje smatra kot
 * {@code ZdravstveniZavod}.
 * <p>
 * Za potrebe Svedra ne rabimo podatkov o teh organizacijskih enotah ampak
 * Excelove preglednice, ki jih hranijo. V njih se namreč skrivajo podatki o
 * urnikih, čakalnih dobah in lokacijah zobozdravstvenih ordinacij.
 */
public final class ZdravstveniZavod {
    /** URL naslov {@code <Ime_zavoda>UrnČD.xlsx} preglednice. */
    private final SvederUrl urlPreglednice;

    /**
     * Enako kot:
     * {@code new ZdravstveniZavod(Integer.parseInt(zzzsSt))}.
     * <br>
     * Glej: {@link #ZdravstveniZavod(int)}
     *
     * @param zzzsSt ZZZS številka tega zavoda
     */
    public ZdravstveniZavod(final String zzzsSt) {
        this(Integer.parseInt(zzzsSt));
    }

    /**
     * Zgradi nov zavod iz ZZZS številke.
     * <p>
     * Na spletni strani:
     * http://www.zzzs.si/zzzs/pao/pogizv.nsf/PoZZZSst/<strong>{@code zzzsSt}</strong>
     * <br>
     * se nahaja link do {@code .xlsx} datoteke tega zavoda.
     *
     * @param zzzsSt ZZZS številka tega zavoda
     */
    public ZdravstveniZavod(final int zzzsSt) {
        this(
            new UrlNaStrani(
                new SpletnaStran(
                    () -> new URL(
                        String.format(
                            "https://www.zzzs.si/zzzs/pao/pogizv.nsf/PoZZZSst/%d",
                            zzzsSt
                        )
                    )
                ),
                "https://www.zzzs.si",
                "(?<=href=\").+(?=\" title)"
            )
        );
    }

    /**
     * Zgradi nov zavod iz poti do preglednice {@code <Ime_zavoda>UrnČD.xlsx}.
     *
     * @param urlPreglednice URL naslov {@code <Ime_zavoda>UrnČD.xlsx}
     *                       preglednice.
     */
    public ZdravstveniZavod(final SvederUrl urlPreglednice) {
        this.urlPreglednice = urlPreglednice;
    }

   /**
    * Vrne {@code .xlsx} preglednico s predpisanimi listi in stolpci.
    * <p>
    * Za potrebe Svedra mora vsaka preglednica vsebovati naslednje liste:
    * <ul>
    *   <li>{@code UrnikiIZV},</li>
    *   <li>{@code LokacijeIZV},</li>
    *   <li>{@code NosilciTimaIZV},</li>
    *   <li>{@code CDIZV}.</li>
    * </ul>
    * <p>
    * Lahko se zgodi, da nek zavod ne objavi preglednice. V tem primeru metoda
    * vrne prazno preglednico z zgoraj naštetimi listi.
    * <p>
    * Preglednico je potrebno po uporabi tudi zapreti.
    *
    * @return {@code .xlsx} preglednico
    * @throws IOException če pride do napake pri branju preglednice
    * @throws java.net.MalformedURLException če URL spletne strani zavoda ali
    *                                        URL {@code .xlsx} preglednice ni
    *                                        veljaven
    */
    public Workbook preglednica() throws IOException {
        try {
            return WorkbookFactory.create(urlPreglednice.url().openStream());
        } catch (FileNotFoundException e) {
            Workbook prazna = WorkbookFactory.create(false);
            prazna.createSheet("UrnikiIZV");
            prazna.createSheet("LokacijeIZV");
            prazna.createSheet("NosilciTimaIZV");
            prazna.createSheet("CDIZV");
            return prazna;
        } catch (POIXMLException e) {
            throw
                new IOException(
                    String.format(
                        "Napaka pri branju preglednice na: %s",
                        urlPreglednice.url().toString()
                    ),
                    e
                );
        }
    }
}
