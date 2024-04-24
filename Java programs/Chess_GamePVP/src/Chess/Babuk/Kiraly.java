package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;

import java.util.ArrayList;
import java.util.List;

import static Chess.tabla.Lepes.*;

public class Kiraly extends Babu {

    private final int[] lehetsLepes = {-1, -7, -8, -9, 1, 7, 8, 9};

    public Kiraly(int spos, Szin sszin) {
        super(BabuFajta.KIRALY, spos, sszin, true);
    }

    public Kiraly(int spos, Szin sszin, boolean isElso) {
        super(BabuFajta.KIRALY, spos, sszin, isElso);
    }

    //Majdnem ugyanazok a lépések mint a csikónál csak mások a koordináták
    @Override
    public List<Lepes> szabLepesek(Tabla stabla) {

        final List<Lepes> sLepes = new ArrayList<>();

        //Vegignezzuk a lehetseges lepeseket
        for (int jelenPos : lehetsLepes) {
            int celPos = babuPos + jelenPos;

            if (elsoMezo(babuPos, jelenPos) || nyolcadikMezo(babuPos, jelenPos)) {
                continue;
            }

            if (TablaUtil.lehetsLepes(celPos)) {
                Mezo celMez = stabla.getMezo(celPos);

                //Ha ott nincs bábú oda léphetünk
                if (!celMez.vanBabu()) {
                    sLepes.add(new SimaLepes(stabla, this, celPos));
                } else { // Ha van meg kell néznünk a színét
                    Babu celLokBabu = celMez.getBabu();
                    Szin celSzin = celLokBabu.getSzin();

                    //Ha ellenséges, akkor oda léphetünk, ha leütjük
                    if (babuSzin != celSzin) {
                        sLepes.add(new TamadoLepes(stabla, this, celPos, celLokBabu));
                    }

                }

            }
        }
        return sLepes;
    }

    @Override
    public Kiraly babuLep(Lepes lepes) {
        return new Kiraly(lepes.getCelKoord(), lepes.getmBabu().getSzin(), false);
    }

    @Override
    public int getErtek() {
        return 6;
    }

    @Override
    public String toString(){
        return BabuFajta.KIRALY.toString();
    }

        //Ellenőrizzük a szélsőséges eseteket
        public boolean elsoMezo(int jelPos, int celPos){
            return TablaUtil.ElsoOszlop[jelPos] && ((celPos == -9 || celPos == -1 || celPos == 7));
        }

        public boolean nyolcadikMezo(int jelPos, int celPos){
            return TablaUtil.NyolcadikOszlop[jelPos] && ((celPos == 9 || celPos == 1 || celPos == -7));
        }
}

