# Sveder

![Logo](logo.png)

[![Build Status](https://travis-ci.com/Glusk/sveder.svg?branch=master)](https://travis-ci.com/Glusk/sveder)

Java knjižnica za *vrtanje* po javno-dostopnih podatkih o zobozdravnikih v
javni mreži.

## Motivacija

Iz javno dostopnih podatkov je prekleto težko zbrati ključne podatke o posameznem zobozdravniku.

Na podlagi *imena* zobozdravnika in *dejavnosti*, ki jo opravlja, bi si želeli enostavno  pridobiti
strukturirane podatke v naslednji obliki in obsegu:

``` json
{
  "ordinacija": {
    "zdravnik": {
      "id_zdravnik": "number",
      "ime_priimek": "string"
    },
    "doseganje_povprecja": "number",
    "lokacija": {
      "id_lokacija": "number",
      "naziv": "string",
      "naslov": "string",
      "telefon": "string",
      "urnik": {
        "pon": {
          "od": "string",
          "do": "string"
        },
        "tor": {
          "od": "string",
          "do": "string"
        },
        "sre": {
          "od": "string",
          "do": "string"
        },
        "cet": {
          "od": "string",
          "do": "string"
        },
        "pet": {
          "od": "string",
          "do": "string"
        },
        "sob": {
          "od": "string",
          "do": "string"
        },
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
  },
}
```

---

*Logo used: <a href="https://vectorified.com/bioshock-icon">Bioshock Icon</a>*
