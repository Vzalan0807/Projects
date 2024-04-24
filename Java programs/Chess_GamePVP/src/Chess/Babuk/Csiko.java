package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.*;

import java.util.ArrayList;
import java.util.List;
import Chess.tabla.Lepes.*;

// Csikó implemetációja
public class Csiko extends Babu{

    // A Táblan az aktualis helyetol ezekre a helyekre tud menni a pozíciójától számítva
    public int[] lehetLepesek = { -17, -15, -10, -6, 6, 10, 15, 17};
    public Csiko(int spos, Szin sszin) {
        super(BabuFajta.CSIKO, spos, sszin, true);
    }

    public Csiko(int spos, Szin sszin, boolean inElso) {
        super(BabuFajta.CSIKO, spos, sszin, inElso);
    }


   //Szabad lépések kiszámítása a Csikónak
    @Override
    public List<Lepes> szabLepesek(Tabla stabla) {

        int celKoord;
        List<Lepes> slepes = new ArrayList<>();

        //Az összes lehetésges pozíciót végignézzük
        for(int jelenPos : lehetLepesek){
            //A csiko celja a jelenlegi pozíció és a céljához vett különbségének összege
            celKoord = this.babuPos + jelenPos;
            if(TablaUtil.lehetsLepes(celKoord)){
                if(elsoMezo(this.babuPos, jelenPos) || masodikMezo(this.babuPos, jelenPos) || hetedikMezo(this.babuPos, jelenPos) || nyolcadikMezo(this.babuPos, jelenPos)){
                    continue;
                }

                Mezo celMez = stabla.getMezo(celKoord);

                //Ha ott nincs bábú oda léphetünk
                if(!celMez.vanBabu()){
                    slepes.add(new SimaLepes(stabla, this, celKoord));
                }else{ // Ha van meg kell néznünk a színét
                    Babu celLokBabu = celMez.getBabu();
                    Szin celSzin = celLokBabu.getSzin();

                    //Ha ellenséges, akkor oda léphetünk, ha leütjük
                    if(babuSzin != celSzin){
                        slepes.add(new TamadoLepes(stabla, this, celKoord, celLokBabu));
                    }
                }
            }

        }
        return slepes;
    }

    @Override
    public Csiko babuLep(Lepes lepes) {
        return new Csiko(lepes.getCelKoord(), lepes.getmBabu().getSzin(), false);
    }

    @Override
    public int getErtek() {
        return 2;
    }

    @Override
    public String toString(){
        return BabuFajta.CSIKO.toString();
    }

    //Ha az első, második, hetedik, nyolcadik mezőn van meg kell nézni mi történik
    public boolean elsoMezo(int jelPos, int celPos){
        return TablaUtil.ElsoOszlop[jelPos] && (celPos == -17 || celPos == -10 || celPos == 6 || celPos == 15);
    }

    public boolean masodikMezo(int jelPos, int celPos){
        return TablaUtil.MasodikOszlop[jelPos] && (celPos == -10 || celPos == 6);
    }

    public boolean hetedikMezo(int jelPos, int celPos){
        return TablaUtil.HetedikOszlop[jelPos] && (celPos == 10 ||celPos == -6);
    }

    public boolean nyolcadikMezo(int jelPos, int celPos){
        return TablaUtil.NyolcadikOszlop[jelPos] && (celPos == 17 || celPos == 10 || celPos == -6 || celPos == -15);
    }

}
