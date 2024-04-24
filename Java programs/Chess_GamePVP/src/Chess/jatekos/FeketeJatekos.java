package Chess.jatekos;

import Chess.Babuk.Babu;
import Chess.Babuk.Bastya;
import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Lepes.KiralyOldalCastle;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Chess.tabla.Lepes.*;

//Fekete jatekos osztalya
public class FeketeJatekos extends Jatekos {
    public FeketeJatekos(Tabla tabla, Collection<Lepes> feherLepes, Collection<Lepes> feketeLepes) {
        super(tabla, feketeLepes, feherLepes);
    }

    @Override
    public Collection<Babu> getJelenBabuk() {
        return this.tabla.getFeketeBabuk();
    }

    @Override
    public Szin getSzin() {
        return Szin.FEKETE;
    }

    @Override
    public Jatekos getEllenfel() {
        return this.tabla.feherJatekos();
    }

    //Megadja a Castle lépéseket ha lehetségesek
    @Override
    public Collection<Lepes> castleSzamol(Collection<Lepes> jelenJatekosLegals, Collection<Lepes> ellenfelLegals) {
        List<Lepes> kiralyCastles = new ArrayList<>();

        if (this.jelenJatekosKiralya.isElso() && !this.isSakkban()) {

            Mezo kiralyMezo = this.tabla.getMezo(4);

            //Fekete oldalt ellenorzi
            //Király és Bástya a helyükön vannak, ez az első lépésük és nincs olyan mező a közöttük lévő mezőkön, ahol sakkban lenne a király
            if (!this.tabla.getMezo(5).vanBabu() && !this.tabla.getMezo(6).vanBabu()) {
                Mezo bastyMezoje = this.tabla.getMezo(7);
                if (bastyMezoje.vanBabu() && bastyMezoje.getBabu().isElso() && kiralyMezo.vanBabu() && kiralyMezo.getBabu().getFajta().isKiraly()) {
                    if (Jatekos.tamadasSzamol(5, ellenfelLegals).isEmpty() && Jatekos.tamadasSzamol(6, ellenfelLegals).isEmpty() && bastyMezoje.getBabu().getFajta().isBastya()) {
                        kiralyCastles.add(new KiralyOldalCastle(this.tabla, this.jelenJatekosKiralya, 6, (Bastya) bastyMezoje.getBabu(), bastyMezoje.getMezoKoord(), 5));
                    }
                    }
                }
                if (!this.tabla.getMezo(1).vanBabu() && !this.tabla.getMezo(2).vanBabu() && !this.tabla.getMezo(3).vanBabu()) {
                    Mezo bastyaMezoje = this.tabla.getMezo(0);
                    if (kiralyMezo.vanBabu() && kiralyMezo.getBabu().getFajta().isKiraly() && bastyaMezoje.vanBabu() && bastyaMezoje.getBabu().isElso() && Jatekos.tamadasSzamol(2, ellenfelLegals).isEmpty() && Jatekos.tamadasSzamol(3, ellenfelLegals).isEmpty() && bastyaMezoje.getBabu().getFajta().isBastya()) {
                        kiralyCastles.add(new KiralynoOldalCastle(this.tabla, this.jelenJatekosKiralya, 2, (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getMezoKoord(), 3));
                    }
                }
            }
            return kiralyCastles;
        }
    }
