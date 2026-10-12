# Primerjalnik Cen

Projekt je razdeljen na tri module:
- Entitete
- Storitve
- Api

<!-- Stran je dostopna na naslednjem naslov -->
<!-- http://localhost:8080/servlet -->
Backend storitev, zgrajena z Jakarta EE in JPA, namenjena upravljanju nakupovalnih seznamov, spremljanju cen izdelkov v različnih trgovinah ter izračunu najugodnejše košarice izdelkov.

---
## Funkcionalnosti
**Upravljanje kataloga:** Organizacija izdelkov po kategorijah in njihova povezava s posameznimi trgovinami.
**Pametne nakupovalne košarice:** Ustvarjanje po meri prilagojenih košaric, dodajanje/odstranjevanje izdelkov ter preverjanje razpoložljivosti po trgovinah.
**Izračun cen:** Samodejni izračun in primerjava skupne cene nakupovalne košarice med različnimi trgovci.
**Podatkovne in poslovne logike(zrna):** Ločena poslovna logika preko CDI zrn za izdelke, kategorije, trgovine, košarice in napredno upravljanje. 
**Napredna arhitektura:**
  - CDI Prestrezniki (Interceptors): Sledenje in beleženje klicev metod z uporabo lastne anotacije (`@BeleziKlice`).
  - Globalno obravnavanje izjem: Formatirani odgovori o napakah v obliki JSON preko JAX-RS `ExceptionMapper`.
  - Vzorec DTO (Data Transfer Object): Ločevanje podatkovnih entitet od prenosnih objektov za čisto komunikacijo.
  - JPA/Hibernate: Uporaba poimenovanih poizvedb (Named Queries), kriterijskih poizvedb (Criteria Queries) in samodejnih SQL skript za inicializacijo baze.

---
- Uporabljen programski jezik Java.
- Orodja in ogrodja: Jakarte EE / Java EE (CDI, JAX-RS, JPA)
- Podatkovna baza: SQL

## Struktura projekta
* `si.fri.prpo.skupina5.entitete` — Osnovne JPA entitete (`Izdelek`, `Kosarica`, `Trgovina`, `Kategorija`).
* `si.fri.prpo.skupina5.zrna` — Poslovna logika in CDI zrna (CRUD operacije in kompleksne primerjave).
* `si.fri.prpo.skupina5.dtos` — Objektni podatkovni prenosniki za ločitev slojev.
* `si.fri.prpo.skupina5.interceptorji` — AOP prestrezniki za spremljanje in beleženje klicev.
* `si.fri.prpo.skupina5.izjeme` & `mappers` — Po meri prilagojene izjeme in preslikovalci za JSON odgovore.


## Namestitev in zagon
1. Klonirajte repozitorij
2. Nastavite povezavo do podatkovne baze v datoteki `persistence.xml`.
3. Zgradite in zaženite projekt v ustreznem strežniku/ogrodju (npr. KumuluzEE)

## Avtor
Projekt je bil razvit v okviru študijskih obveznosti na Fakulteti za računalništvo in informatiko (UL FRI) pri predmetu Postopki razvoja programske opreme.
