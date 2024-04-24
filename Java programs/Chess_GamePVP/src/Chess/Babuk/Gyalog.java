package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;

import java.util.ArrayList;
import java.util.List;

import static Chess.tabla.Lepes.*;

// Gyalog megvalósítása
public class Gyalog extends Babu{

    //8 akat vagy egyszer 16 ot tud előre menni(ugrás)
    private final int[] lehetsLepes = {7, 8, 9, 16};

    public Gyalog(int spos, Szin sszin) {
        super(BabuFajta.GYALOG, spos, sszin, true);
    }

    public Gyalog(int spos, Szin sszin, boolean inElso) {
        super(BabuFajta.GYALOG, spos, sszin, inElso);
    }

    //Gyalog lépései
    @Override
    public List<Lepes> szabLepesek(Tabla stabla) {

        List<Lepes> sLepes = new ArrayList<>();

        //Lehetséges vele simán egyenként előre haladni
        for(int jelenPos : lehetsLepes){
            int celPos = babuPos + (this.getSzin().getIrany() * jelenPos);
            if(!TablaUtil.lehetsLepes(celPos)){
                continue;
            }
            if(jelenPos == 8 && !stabla.getMezo(celPos).vanBabu()){
                sLepes.add(new SimaLepes(stabla, this, celPos));
             // Egyszer a kezdő helyről lehet vele 2őt lépni ugrani, de akkor az előtte lévő helyen sem lehet másik Bábú!!!
            }else if(jelenPos == 16 && this.isElso() && ((TablaUtil.MasodikSor[this.babuPos] && getSzin().isFekete()) || (TablaUtil.HetedikSor[this.babuPos] && getSzin().isFeher()))){
                int elottedlevo = babuPos + (getSzin().getIrany() * 8);
                if(!stabla.getMezo(elottedlevo).vanBabu() && !stabla.getMezo(celPos).vanBabu()){
                    sLepes.add(new SimaLepes(stabla, this, celPos));
                }

            // a parasztal ütni csak keresztben szabad, viszont, ha szélen vagyunk akkor ki kell térni azokra az irányokra amerre nem mehetünk
            }else if(jelenPos == 7 && !((TablaUtil.NyolcadikOszlop[this.babuPos] && getSzin().isFeher()) || (TablaUtil.ElsoOszlop[this.babuPos] && getSzin().isFekete()))){
                if(stabla.getMezo(celPos).vanBabu()){
                    Babu onBabu = stabla.getMezo(celPos).getBabu();
                    if(getSzin() != onBabu.getSzin()){
                        sLepes.add(new TamadoLepes(stabla, this, celPos, onBabu));
                    }
                }


            }else if(jelenPos == 9 && !((TablaUtil.ElsoOszlop[this.babuPos] && getSzin().isFeher()) || (TablaUtil.NyolcadikOszlop[this.babuPos] && getSzin().isFekete()))){
                if(stabla.getMezo(celPos).vanBabu()) {
                    Babu onBabu = stabla.getMezo(celPos).getBabu();
                    if (getSzin() != onBabu.getSzin()) {
                        sLepes.add(new TamadoLepes(stabla, this, celPos, onBabu));
                    }
                }
            }


        }

        return sLepes;
    }

    @Override
    public Gyalog babuLep(Lepes lepes) {
        return new Gyalog(lepes.getCelKoord(), lepes.getmBabu().getSzin(), false);
    }

    @Override
    public int getErtek() {
        return 1;
    }

    @Override
    public String toString(){
        return BabuFajta.GYALOG.toString();
    }
}
