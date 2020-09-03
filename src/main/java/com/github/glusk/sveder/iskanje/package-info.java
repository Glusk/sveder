/**
 * Paket za iskanje ordinacij po raznoraznih ključih.
 * <p>
 * Ordinacije lahko vedno filtriramo preko *stream* API-ja:
 * <pre>
 * // Ordinacije o = ...
 * o.ordinacije()
 *  .stream()
 *  .filter(
 *      // pogoj
 *   );
 * </pre>
 * Nekater poizvedbe so bolj pogoste in zato jih je smiselno izluščiti v
 * svoj razred. Ta paket je zbirka takih razredov.
 */
package com.github.glusk.sveder.iskanje;
