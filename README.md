# Projekt alfa2 -- Ztratova komprese textu a Logovani
## Kuta Samuel C4b 

**Tento program ztratove kompresuje textove soubory tak, aby i po kompresi byly citelne lidmi.**

## Program poskytuje funkcionalitu na kompresi textovych souboru, vytvareni log zaznamu a prohledavani techto zaznamu

### Prubeh programu
- Program nacte textovy soubor, zkompressuje text napsany v souboru a zapise ho do nove slozky.
- Nasledne provede zapis zaznamu (log) o provedene operaci
- O jakkekoliv chybe budete v programu informovani, jak ji vyresit, a bude o ni zapsan zaznam (log) s blizsimi informacemi. 
- Vsechny tyto casti programu se daji konfigurovat, vice informaci v casti [Konfigurace programu](#konfigurace-programu)

### Potreby pro spusteni
Nektere veci nejsou striktne potreba ke spusteni, ale pro plnou funkcnost a nejlepis zkusenost je nejlepsi udelat vse. Tyto kroky jsou oznaceny jako *NEPOVINNE*
- **Java verze 17.0 +**
    - **Da se zjistit pomoci konzoloveho prikazu `java --version`**
- **Gradle verze 8.4 +** - *NEPOVINNE*

### Jak spustit program


- **Linux / Unix / Mac**
    - **Pokud je nainstalovany Gradle**
        - Navigujte do adresare programu v konzoli
    pouzijte tento prikaz : `./gradlew build`
- **Windows**
    - **Pokud je nainstalovany Gradle**
        - Navigujte do adresare programu v konzoli
    pouzijte tento prikaz : `gradlew build`

### Konfigurace programu
Veskera konfigurace se da provadet upravami souboru config.json v adresari config.

Config soubor je ve formatu json, a pokud bude dodrzen presny format tak muze byt nahrazen vlastnim config souborem.

Priklad **config** souboru:


    {
    "cesta_k_souboru":"/home//Projects/alfa2/src/main/resources/testTextLong.txt",
    "adresar_umisteni_outputu":"default",
    "nazev_output_souboru":"default",
    "casovy_tag_v_nazvu":"A",
    "adresar_umisteni_error_logu":"default",
    "adresar_umisteni_operacniho_logu":"default"
    }

**Defaultni konfigurace**
- Pro defaultni konfiguraci, napriklad pro testovani, by mely byt vsechny hodnoty zapsane na `default`
- **Defaultni konfigurace**:
    
        {
        "cesta_k_souboru":"default",
        "adresar_umisteni_outputu":"default",
        "nazev_output_souboru":"default",
        "casovy_tag_v_nazvu":"default",
        "adresar_umisteni_error_logu":"default",
        "adresar_umisteni_operacniho_logu":"default"
        }
    


**Da se konfiguvrovat**:
- **Input soubor**
    - Ktery soubor bude precten a text v nem kompresovan
    - Cesta k souboru
- **Adresar output souboru**
    - Ve kterem adresari bude soubor s kompresovanym textem ulozen
    - Cesta do adresare
- **Jmeno output souboru**
    - Jak se ma jmenovat soubor s kompresovanym textem
- **Casovy tag k output souboru**
    - Jestli se do jmena kompresovaneho souboru ma pripsat cas kdy se operace stala
    - A = ano | N = ne
- **Adresar error logu**
    - Ve kterem adresari bude ulozeny zaznam o chybach
- **Adresar operacniho logu**
    - Ve kterem adresari bude ulozeny zaznam o operacich
### Dokumentace
- **Programatorska dokumentace** 
    - Po provedeni Gradle prikazu v casti [Jak spustit program](#jak-spustit-program) bude ve adresari build/docs/javadoc index.html soubor. Zobrazte v prohlizeci pro kompletni javadoc.
    - Zdrojovy kod je take dokumentovany
- **Uzivatelska dokumentace**
    - Ted ji ctete, tento soubor slouzi jako uzivatelska dokumentace

### Technicky popis programu
