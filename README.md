# Sveder

![Logo](logo.png)

[![Build Status](https://travis-ci.com/Glusk/sveder.svg?branch=master)](https://travis-ci.com/Glusk/sveder)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.glusk/sveder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.glusk/sveder)
[![javadoc](https://javadoc.io/badge2/com.github.glusk/sveder/javadoc.svg)](https://javadoc.io/doc/com.github.glusk/sveder)

Java knjižnica za *vrtanje* po javno-dostopnih podatkih o zobozdravstvenih
ordinacijah, ki so del javne mreže.

## Motivacija

Iz javno dostopnih podatkov je prekleto težko zbrati ključne podatke o posamezni ordinaciji.

Želeli bi si enostavno  pridobiti strukturirane podatke javnih zobozdravsvenih ordinacij
v naslednji obliki in obsegu:

``` json
{
  "ordinacija": {
    "sifra_izvajalca": "string",
    "zdravnik": {
      "sifra": "string",
      "ime_priimek": "string"
    },
    "dejavnost": {
      "sifra": "string",
      "naziv": "string"
    },
    "doseganje_povprecja": "number",
    "lokacije": {
      "lokacija": {
        "sifra": "string",
        "naziv": "string",
        "naslov": "string",
        "telefon": "string",
        "urnik": {
          "razpored": [
            {
              "dan": "pon",
              "od": "string",
              "do": "string"
            },
            {
              "dan": "tor",
              "od": "string",
              "do": "string"
            },
            {
              "dan": "sre",
              "od": "string",
              "do": "string"
            },
            {
              "dan": "cet",
              "od": "string",
              "do": "string"
            },
            {
              "dan": "pet",
              "od": "string",
              "do": "string"
            },
            {
              "dan": "sob",
              "od": "string",
              "do": "string"
            },
          ],
          "opombe": "string"
        },
        "cakalne_dobe": {
          "storitev": {
            "id_storitve": "number",
            "ime_storitve": "string",
            "min_dni": "number",
            "max_dni": "number"
          },
          "storitev": {
            "id_storitve": "number",
            "ime_storitve": "string",
            "min_dni": "number",
            "max_dni": "number"
          },
          "opombe": "string"
        }
      }
    }
  },
}
```

## Uporaba

Najdi ordinacije na podlagi *imena* zobozdravnika in *dejavnosti*, ki jo opravlja:

``` java
import java.io.IOException;
import com.github.glusk.sveder.Dejavnost;
import com.github.glusk.sveder.excel.ExcelOrdinacije;
import com.github.glusk.sveder.iskanje.OrdinacijeImeDejavnost;
public class App {
    public static void main(String[] args) throws IOException {
        String json =
            new OrdinacijeImeDejavnost(
                new ExcelOrdinacije(),
                Dejavnost.ZDRAVLJENJE_ODRASLI,
                "POLDE",
                "NERODA"
            ).json().toString(2);
        System.out.println(json);
    }
}
```

## Releases

Use the [release](./release.sh) script with the following arguments:

1.  `release` - the next release version

2.  `snapshot` - the next snapshot version

3.  `dryRun` (optional) - if set to `true`, the changes will not be pushed
   to the remote repository

Example:

``` bash
./release.sh 0.1.1 0.1.2-SNAPSHOT
```
---

*Logo used: <a href="https://vectorified.com/bioshock-icon">Bioshock Icon</a>*
