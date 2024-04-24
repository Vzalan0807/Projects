package Chess.tabla;

import Chess.Babuk.*;
import Chess.Szin;
import Chess.jatekos.FeherJatekos;
import Chess.jatekos.FeketeJatekos;
import Chess.jatekos.Jatekos;

import java.util.*;


// Maganak a tablanak az osztalya
public class Tabla {

    // A tablan talalhato mezok listaja
    private final List<Mezo> jatekTabla;

    //Feherek szamon tartasa
    private final Collection<Babu> feherBabuk;

    //Feketek szamon tartasa
    private final Collection<Babu> feketeBabuk;
    public final Jatekos jelenJatekos;
    private final FeherJatekos feherJatekos;
    private final FeketeJatekos feketeJatekos;

    //A táblát egy generátor osztály leszármazottal konstruktáljuk
    private Tabla(Generator i){
        this.jatekTabla = jatekTablaGen(i);
        this.feketeBabuk = jelenlegiBabuk(this.jatekTabla, Szin.FEKETE);
        this.feherBabuk = jelenlegiBabuk(this.jatekTabla, Szin.FEHER);

        Collection<Lepes> feherLepes = lepesSzamit(this.feherBabuk);
        Collection<Lepes> feketeLepes = lepesSzamit(this.feketeBabuk);

        this.feketeJatekos = new FeketeJatekos(this, feherLepes, feketeLepes);
        this.feherJatekos = new FeherJatekos(this, feherLepes, feketeLepes);
        this.jelenJatekos = i.koviLepes.jatekosValaszt(this.feherJatekos, this.feketeJatekos);

    }

    //Getter függvények
    public Jatekos feherJatekos(){
        return this.feherJatekos;
    }

    public Jatekos feketeJatekos(){
        return this.feketeJatekos;
    }

    public Jatekos getJelenJatekos(){
        return this.jelenJatekos;
    }

    public Collection<Babu> getFeketeBabuk(){
        return feketeBabuk;
    }

    public Collection<Babu> getFeherBabuk(){
        return feherBabuk;
    }
    public Mezo getMezo(final int koord){
        return jatekTabla.get(koord);
    }


    //Kiszámítja a megadott Bábúkhoz tartozó lehetséges lépéseket
    private Collection<Lepes> lepesSzamit(Collection<Babu> babuk) {

        List<Lepes> vissza = new ArrayList<>();

        for(Babu seged : babuk){
            vissza.addAll(seged.szabLepesek(this));
        }

        return vissza;
    }

    //Visszaadja a megadott listabol a megadott szinhez tartozo babukat
    private Collection<Babu> jelenlegiBabuk(List<Mezo> jatekTabla, Szin szin) {
        List<Babu> vissza = new ArrayList<>();

        for(int i = 0; i < jatekTabla.size(); i++){
            if(jatekTabla.get(i).vanBabu() && jatekTabla.get(i).getBabu().getSzin().equals(szin)){
                vissza.add(jatekTabla.get(i).getBabu());
            }
        }

        return vissza;
    }

    //Letrehozza a mezoket a tablara
    public static List<Mezo> jatekTablaGen(final Generator gen){
        final Mezo[] mezok = new Mezo[64];
        for(int i = 0; i < 64; i++){
            mezok[i] = Mezo.mezoLetrehoz(i, gen.tablaKep.get(i));
        }
        return List.of(mezok);
    }

    //Létrehoz egy sakktáblát alap felállásban
    public static Tabla alapTabla(){
        final Generator gen = new Generator();
        //Feketek letrehozasa
        gen.setBabu(new Bastya(0, Szin.FEKETE));
        gen.setBabu(new Csiko(1, Szin.FEKETE));
        gen.setBabu(new Futo(2, Szin.FEKETE));
        gen.setBabu(new Kiralyno(3, Szin.FEKETE));
        gen.setBabu(new Kiraly(4, Szin.FEKETE));
        gen.setBabu(new Futo(5, Szin.FEKETE));
        gen.setBabu(new Csiko(6, Szin.FEKETE));
        gen.setBabu(new Bastya(7, Szin.FEKETE));
        for(int i = 8; i <= 15; i++){
            gen.setBabu(new Gyalog(i, Szin.FEKETE));
        }

        //Feherek letrehozasa
        for(int i = 48; i <= 55; i++){
            gen.setBabu(new Gyalog(i, Szin.FEHER));
        }
        gen.setBabu(new Bastya(56, Szin.FEHER));
        gen.setBabu(new Csiko(57, Szin.FEHER));
        gen.setBabu(new Futo(58, Szin.FEHER));
        gen.setBabu(new Kiralyno(59, Szin.FEHER));
        gen.setBabu(new Kiraly(60, Szin.FEHER));
        gen.setBabu(new Futo(61, Szin.FEHER));
        gen.setBabu(new Csiko(62, Szin.FEHER));
        gen.setBabu(new Bastya(63, Szin.FEHER));

        //Feherek lepnek eloszor
        gen.setLepes(Szin.FEHER);

        return gen.generate();
    }

    //A megadott String[] segítségével beállít egy korábban be nem fejezett páylát és a Szin értékkel beállítja a kezdő játékost
    public static Tabla korabbiBetolt(String[] babuPosTomb, Szin kezdo){
        final Generator gen = new Generator();
        for(int i = 0; i < 64; i++){
            switch (babuPosTomb[i]){
                //Fehérek beállítása
                case "WG":
                    gen.setBabu(new Gyalog(i, Szin.FEHER));
                    break;
                case "WC":
                    gen.setBabu(new Csiko(i, Szin.FEHER));
                    break;
                case "WB":
                    gen.setBabu(new Bastya(i, Szin.FEHER));
                    break;
                case "WF":
                    gen.setBabu(new Futo(i, Szin.FEHER));
                    break;
                case "WQ":
                    gen.setBabu(new Kiralyno(i, Szin.FEHER));
                    break;
                case "WK":
                    gen.setBabu(new Kiraly(i, Szin.FEHER));
                    break;

                //Feketék beállítása
                case "BG":
                    gen.setBabu(new Gyalog(i, Szin.FEKETE));
                    break;
                case "BC":
                    gen.setBabu(new Csiko(i, Szin.FEKETE));
                    break;
                case "BB":
                    gen.setBabu(new Bastya(i, Szin.FEKETE));
                    break;
                case "BF":
                    gen.setBabu(new Futo(i, Szin.FEKETE));
                    break;
                case "BQ":
                    gen.setBabu(new Kiralyno(i, Szin.FEKETE));
                    break;
                case "BK":
                    gen.setBabu(new Kiraly(i, Szin.FEKETE));
            }

        }
        //kezdő játékos beállítása
        gen.setLepes(kezdo);
        return gen.generate();
    }

    //Az osszes lehetséges lépés kiszámítása
    public Collection<Lepes> getLehetsLepesek() {
        List<Lepes> vissza = new ArrayList<>();
        vissza.addAll(feherJatekos.getLehetsLepesek());
        vissza.addAll(feketeJatekos.getLehetsLepesek());
        return vissza;
    }

    // Generator osztaly, ennek segitsegevel hozzuk letre a tablankat
    //Lehettek volna az alábbi metódusok a Tabla osztály tagjai is, azonban így egyszerűbb volt őket definiálni, illetve
    //későbbi esetleges fejlesztéseknél előnyösebb ez a megvalósítás
    public static class Generator{

        //minden mezo szamahoz tartozik egy Babu
        Map<Integer, Babu> tablaKep;

        // A babuk szinet segit eldonteni
        Szin koviLepes;

        public Generator(){
            this.tablaKep = new HashMap<>();

        }

        //Beallitja a babut a megadott mezore
        public Generator setBabu(final Babu uj){
            this.tablaKep.put(uj.getBabuPos(), uj);
            return this;
        }

        //Beallitja a kovetkezo szint
        public Generator setLepes(final Szin ujSz){
            this.koviLepes = ujSz;
            return this;
        }

        //Legeneral egy Tabla-t
        public Tabla generate(){
            return new Tabla(this);
        }

    }

}
