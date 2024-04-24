package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;

import java.util.ArrayList;
import java.util.List;

import static Chess.tabla.Lepes.*;

public class Kiralyno extends Babu{

    // A Táblan az aktualis helyetol ezekbe az irányokba tud menni
    private final int[] lepVektor = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Kiralyno(int spos, Szin sszin) {
        super(BabuFajta.KIRALYNO, spos, sszin, true);
    }

    public Kiralyno(int spos, Szin sszin, boolean isElso) {
        super(BabuFajta.KIRALYNO, spos, sszin, isElso);
    }

    @Override
    public List<Lepes> szabLepesek(Tabla stabla) {

        List<Lepes> slepes = new ArrayList<>();
        //Vegigmegyünk az összes irányon
        for(int jelenPos : lepVektor){
            int celKoord = babuPos;

            //Végigmegyünk az irányokban az összes mezőn, ameddig valami meg nem akadályozza a tovább haladást
            while(TablaUtil.lehetsLepes(celKoord)){

                if(ElsoMezo(celKoord, jelenPos)  || nyolcadikMezo(celKoord, jelenPos)){
                    break;
                }

                celKoord += jelenPos;

                if(TablaUtil.lehetsLepes(celKoord)){

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
                        //Kilépünk, ha áll ott bábú, mivel, akkor tovább nem mehetünk
                        break;
                    }
                }

            }
        }

        return slepes;
    }

    @Override
    public Kiralyno babuLep(Lepes lepes) {
        return new Kiralyno(lepes.getCelKoord(), lepes.getmBabu().getSzin(), false);
    }

    @Override
    public int getErtek() {
        return 5;
    }

    @Override
    public String toString(){
        return BabuFajta.KIRALYNO.toString();
    }

    // Szelsoseges helyek ellenorzese
    private boolean ElsoMezo(int jelPos, int celPos){
        return (TablaUtil.ElsoOszlop[jelPos] && (celPos == 7 || celPos == -9 || celPos == -1));
    }

    private boolean nyolcadikMezo(int jelPos, int celPos) {
        return (TablaUtil.NyolcadikOszlop[jelPos] && (celPos == -7 || celPos == 9 || celPos == 1));
    }

    }
