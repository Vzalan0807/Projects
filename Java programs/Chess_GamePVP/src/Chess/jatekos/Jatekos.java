package Chess.jatekos;

import Chess.Babuk.Babu;
import Chess.Babuk.Kiraly;
import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// Jatekos foosztaly
public abstract class Jatekos {

    //Minden jatekos rendelkezik az alabbiakkal
    protected Tabla tabla;
    protected Kiraly jelenJatekosKiralya;
    private final boolean isSakk;
    protected Collection<Lepes> lehetsLepesek;

    Jatekos(Tabla tabla, Collection<Lepes> lehetsLepesek, Collection<Lepes> ellenfelLepesek){
        this.tabla = tabla;
        this.jelenJatekosKiralya = kiralyLetrehoz();
        this.lehetsLepesek = lehetsLepesek;
        lehetsLepesek.addAll(castleSzamol(lehetsLepesek, ellenfelLepesek));
        //Végig megy az ellenfél lépésein és ha van olyan lépése aminek a célpontja a király akkor sakkban vagyunk.
        this.isSakk = !Jatekos.tamadasSzamol(this.jelenJatekosKiralya.getBabuPos(), ellenfelLepesek).isEmpty();
        jelenJatekosKiralya.szabLepesek(tabla).addAll(castleSzamol(lehetsLepesek, ellenfelLepesek));
    }

    public Collection<Lepes> getLehetsLepesek(){
        return lehetsLepesek;
    }

    //Megnézi, hogy van e olyan lépés a megadott Collection-ben, amelynek a célja a megadott pozíció.
    protected static Collection<Lepes> tamadasSzamol(int babuPos, Collection<Lepes> ellenfelLepesek) {
        List<Lepes> tamadasok = new ArrayList<>();
        for(Lepes i : ellenfelLepesek){
            if(babuPos == i.getCelKoord()){
                tamadasok.add(i);
            }
        }
        return tamadasok;
    }

    //Végig megy az összes lehetséges lépésünkön és mindet virtuálisan végrehajtja és ha már egy is sikerül akkor tudunk máshova lépni, ha egy se sikerül akkor nem tudunk
    protected boolean vanUtMashova(){
        for(Lepes s : this.lehetsLepesek){
            LepesValosit lepVal = lep(s);
            if(lepVal.getLepesAllapot().isVegrehajtva()){
                return true;
            }
        }
        return false;
    }

    //Visszaadja a kiralyukat, ez a legfontosabb Babu-juk
    private Kiraly kiralyLetrehoz() {
        for(Babu iBabu : getJelenBabuk()){
            if(iBabu.getFajta().isKiraly()){
                return new Kiraly(iBabu.getBabuPos(), iBabu.getSzin());
            }
        }
       throw new RuntimeException("Nincs király:(");
    }

    public boolean isLegalLepes(Lepes lepes){
       for(Lepes lepes2 : this.lehetsLepesek){
           if(lepes2.equals(lepes)){
               return true;
           }
       }
       return false;
    }

    public Babu getJatekosKiraly(){
        return this.jelenJatekosKiralya;
    }

    //Vissza adja, hogysakkban vagy-e
    public boolean isSakkban(){
        return this.isSakk;
    }

    //Akkor Matt, ha sakkban vagy és nem tudsz máshova menni
    public boolean isSakkMatt(){
        return this.isSakk && !vanUtMashova();
    }

    //Patt ,ha nem vagy sakkban, de ha máshova lépnél abban lennél, vagyis nincs ut máshova
    public boolean isPatt(){
        return !this.isSakk && !vanUtMashova();
    }

    // Megnézi, hogy a megadott lépés megválósítható-e
    public LepesValosit lep(Lepes lepes){
        //Ha nem legális nem válósítható meg
        if(!this.isLegalLepes(lepes)){
            return new LepesValosit(this.tabla, lepes, LepesAllapot.NemLehetsLepes);
        }

        Tabla ujTabla = lepes.megvalosit();

        //Ha király sakkba kerül a lépés miatt, akkor szintén nem lehet megvalósítani
        Collection<Lepes> kiralyTamadoLepesek = Jatekos.tamadasSzamol(ujTabla.getJelenJatekos().getEllenfel().getJatekosKiraly().getBabuPos(),
                                                                        ujTabla.getJelenJatekos().getLehetsLepesek());
        if(!kiralyTamadoLepesek.isEmpty()){
            return new LepesValosit(this.tabla, lepes, LepesAllapot.SakkbanHagy);
        }

        //Egyébként meg lehet
        return new LepesValosit(ujTabla, lepes, LepesAllapot.Vegrehajtva);
    }


    public abstract Collection<Babu> getJelenBabuk();
    public abstract Szin getSzin();

    public abstract Jatekos getEllenfel();

    //Megnézzük, hogy van-e CatleLepes...később
    public abstract Collection<Lepes> castleSzamol(Collection<Lepes> jelenJatekosLegals, Collection<Lepes> ellenfelLegals);

}
