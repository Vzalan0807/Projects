package Chess.jatekos;

import Chess.Babuk.Babu;
import Chess.Babuk.Bastya;
import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Chess.tabla.Lepes.*;

//Feher jatekos osztalya
public class FeherJatekos extends Jatekos{

    public FeherJatekos(Tabla tabla, Collection<Lepes> feherLepes, Collection<Lepes> feketeLepes) {
        super(tabla, feherLepes, feketeLepes);
    }

    @Override
    public Collection<Babu> getJelenBabuk() {
        return this.tabla.getFeherBabuk();
    }

    @Override
    public Szin getSzin() {
        return Szin.FEHER;
    }

    @Override
    public Jatekos getEllenfel() {
        return this.tabla.feketeJatekos();
    }

    //Megadja a Castle lépéseket ha lehetségesek
    @Override
    public Collection<Lepes> castleSzamol(Collection<Lepes> jelenJatekosLegals, Collection<Lepes> ellenfelLegals) {
        List<Lepes> kiralyCastles = new ArrayList<>();

        if(this.jelenJatekosKiralya.isElso() && !this.isSakkban()) {
            //Feher oldalt ellenorzi
            //Király és Bástya a helyükön vannak, ez az első lépésük és nincs olyan mező a közöttük lévő mezőkön, ahol sakkban lenne a király
            Mezo kiralyMezo = tabla.getMezo(60);

            if (!this.tabla.getMezo(61).vanBabu() && !this.tabla.getMezo(62).vanBabu()) {
                Mezo bastyMezoje = this.tabla.getMezo(63);

                if (bastyMezoje.vanBabu() && bastyMezoje.getBabu().isElso() && kiralyMezo.vanBabu() && kiralyMezo.getBabu().getFajta().isKiraly()) {
                    if(Jatekos.tamadasSzamol(61, ellenfelLegals).isEmpty() && Jatekos.tamadasSzamol(62, ellenfelLegals).isEmpty() && bastyMezoje.getBabu().getFajta().isBastya()){
                        kiralyCastles.add(new KiralyOldalCastle(this.tabla, this.jelenJatekosKiralya, 62, (Bastya) bastyMezoje.getBabu(), bastyMezoje.getMezoKoord(), 61));
                    }
                }
            }
            if(!this.tabla.getMezo(59).vanBabu() && !this.tabla.getMezo(58).vanBabu() && !this.tabla.getMezo(57).vanBabu()){
                Mezo bastyaMezoje = this.tabla.getMezo(56);
                if(kiralyMezo.vanBabu() && kiralyMezo.getBabu().getFajta().isKiraly() && bastyaMezoje.vanBabu() && bastyaMezoje.getBabu().isElso() &&
                   Jatekos.tamadasSzamol(58, ellenfelLegals).isEmpty() && Jatekos.tamadasSzamol(59, ellenfelLegals).isEmpty() && bastyaMezoje.getBabu().getFajta().isBastya()){
                    kiralyCastles.add(new KiralynoOldalCastle(this.tabla, this.jelenJatekosKiralya, 58, (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getMezoKoord(), 59));
                }
            }
        }
        return kiralyCastles;
    }
}
