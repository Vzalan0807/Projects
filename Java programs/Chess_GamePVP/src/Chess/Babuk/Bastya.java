package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;

import java.util.ArrayList;
import java.util.List;

import static Chess.tabla.Lepes.*;

public class Bastya extends Babu{

    // A Táblan az aktualis helyetol ezekre a helyekre tud menni a pozíciójától számítva
    private final static int[] lepVektor = {-8, -1, 1, 8};

    public Bastya(int spos, Szin sszin) {
        super(BabuFajta.BASTYA, spos, sszin, true);
    }

    public Bastya(int spos, Szin sszin, boolean inEslo){
        super(BabuFajta.BASTYA, spos, sszin, inEslo);
    }

    @Override
    public List<Lepes> szabLepesek(Tabla stabla) {

        List<Lepes> slepes = new ArrayList<>();
        //Vegigmegyünk az összes irányon
        for (int jelenPos : lepVektor) {
            int celKoord = babuPos;

            //Végigmegyünk az irányokban az összes mezőn, ameddig valami meg nem akadályozza a tovább haladást
            while (TablaUtil.lehetsLepes(celKoord)) {

                if (ElsoMezo(celKoord, jelenPos) || nyolcadikMezo(celKoord, jelenPos)) {
                    break;
                }

                celKoord += jelenPos;

                if (TablaUtil.lehetsLepes(celKoord)) {

                    final Mezo celMez = stabla.getMezo(celKoord);

                    //Ha ott nincs bábú oda léphetünk
                    if (!celMez.vanBabu()) {
                        slepes.add(new SimaLepes(stabla, this, celKoord));
                    } else { // Ha van meg kell néznünk a színét
                        final Babu celLokBabu = celMez.getBabu();
                        final Szin celSzin = celLokBabu.getSzin();

                        //Ha ellenséges, akkor oda léphetünk, ha leütjük
                        if (babuSzin != celSzin) {
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

    //Lép egyet a Bábúval
    @Override
    public Bastya babuLep(Lepes lepes) {
        return new Bastya(lepes.getCelKoord(), lepes.getmBabu().getSzin(), false);
    }

    @Override
    public int getErtek() {
        return 3;
    }

    @Override
    public String toString(){
        return BabuFajta.BASTYA.toString();
    }

    //Elso mezon all-e
    private static boolean ElsoMezo(int jelPos, int celPos){
        return (TablaUtil.ElsoOszlop[jelPos] && (celPos == -1));
    }

    // Nyolcadik mezon all-e
    private static boolean nyolcadikMezo(int jelPos, int celPos) {
        return (TablaUtil.NyolcadikOszlop[jelPos] && (celPos == 1));
    }
}

