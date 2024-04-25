import Chess.Babuk.*;
import Chess.Szin;
import Chess.jatekos.*;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;


public class Tester {

    @Test
    public void testLegalLepes(){
        // Teszteljük a megfelelő lépést
        Tabla tabla = Tabla.alapTabla();
        Babu testB = new Gyalog(48, Szin.FEHER, true);
        Lepes lepes = new Lepes.SimaLepes(tabla, testB, 40);

        Jatekos jatekos = new FeherJatekos(tabla, tabla.getJelenJatekos().getLehetsLepesek(), tabla.getJelenJatekos().getEllenfel().getLehetsLepesek());

        LepesValosit lepVal = jatekos.lep(lepes);

        // Ellenőrizd, hogy a lépés végrehajtva lett
        assertEquals(LepesAllapot.Vegrehajtva, lepVal.getLepesAllapot());
    }

    @Test
    public void feherCastleTest(){
        Tabla tabla = Tabla.alapTabla();

        Jatekos jatekos = new FeherJatekos(tabla, tabla.getJelenJatekos().getLehetsLepesek(), tabla.getJelenJatekos().getEllenfel().getLehetsLepesek());

        assertEquals(Collections.emptyList(), jatekos.castleSzamol(jatekos.getLehetsLepesek(), jatekos.getEllenfel().getLehetsLepesek()));
    }

    @Test
    public void feketeCastleTest(){
        Tabla tabla = Tabla.alapTabla();

        Jatekos jatekos = new FeketeJatekos(tabla, tabla.getJelenJatekos().getEllenfel().getLehetsLepesek(), tabla.getJelenJatekos().getLehetsLepesek());

        assertEquals(Collections.emptyList(), jatekos.castleSzamol(jatekos.getLehetsLepesek(), jatekos.getEllenfel().getLehetsLepesek()));
    }

    @Test
    public void bastyaKonstrukt(){
        Babu ujBastya = new Bastya(35, Szin.FEKETE, false);
        Babu ujBastya2 = new Bastya(36, Szin.FEHER);

        assertEquals(35, ujBastya.getBabuPos());
        assertEquals(36, ujBastya2.getBabuPos());

        assertEquals(Szin.FEKETE, ujBastya.getSzin());
        assertEquals(Szin.FEHER, ujBastya2.getSzin());

        assertFalse(ujBastya.isElso());
        assertTrue(ujBastya2.isElso());

    }

    @Test
    public void csikoKonstrukt(){
        Babu ujCsiko = new Csiko(45, Szin.FEKETE, false);
        Babu ujCsiko2 = new Csiko(47, Szin.FEHER);

        assertEquals(45, ujCsiko.getBabuPos());
        assertEquals(47, ujCsiko2.getBabuPos());

        assertEquals(Szin.FEKETE, ujCsiko.getSzin());
        assertEquals(Szin.FEHER, ujCsiko2.getSzin());

        assertFalse(ujCsiko.isElso());
        assertTrue(ujCsiko2.isElso());
    }

    @Test
    public void futoKonstrukt(){
        Babu ujFuto = new Futo(50, Szin.FEKETE, false);
        Babu ujFuto2 = new Futo(51, Szin.FEHER);

        assertEquals(50, ujFuto.getBabuPos());
        assertEquals(51, ujFuto2.getBabuPos());

        assertEquals(Szin.FEKETE, ujFuto.getSzin());
        assertEquals(Szin.FEHER, ujFuto2.getSzin());

        assertFalse(ujFuto.isElso());
        assertTrue(ujFuto2.isElso());
    }

    @Test
    public void gyalogKonstrukt(){
        Babu ujGyalog = new Gyalog(59, Szin.FEKETE, false);
        Babu ujGyalog2 = new Gyalog(60, Szin.FEHER);

        assertEquals(59, ujGyalog.getBabuPos());
        assertEquals(60, ujGyalog2.getBabuPos());

        assertEquals(Szin.FEKETE, ujGyalog.getSzin());
        assertEquals(Szin.FEHER, ujGyalog2.getSzin());

        assertFalse(ujGyalog.isElso());
        assertTrue(ujGyalog2.isElso());
    }

    @Test
    public void kiralyKonstrukt(){
        Babu ujKiraly = new Kiraly(62, Szin.FEHER, false);
        Babu ujKiraly2 = new Kiraly(60, Szin.FEKETE);

        assertEquals(62, ujKiraly.getBabuPos());
        assertEquals(60, ujKiraly2.getBabuPos());

        assertEquals(Szin.FEHER, ujKiraly.getSzin());
        assertEquals(Szin.FEKETE, ujKiraly2.getSzin());

        assertFalse(ujKiraly.isElso());
        assertTrue(ujKiraly2.isElso());
    }

    @Test
    public void kiralynoKonstrukt(){
        Babu ujKiralyno = new Kiralyno(55, Szin.FEKETE);
        Babu ujKiralyno2 = new Kiralyno(56, Szin.FEHER, false);

        assertEquals(55, ujKiralyno.getBabuPos());
        assertEquals(56, ujKiralyno2.getBabuPos());

        assertEquals(Szin.FEKETE, ujKiralyno.getSzin());
        assertEquals(Szin.FEHER, ujKiralyno2.getSzin());

        assertTrue(ujKiralyno.isElso());
        assertFalse(ujKiralyno2.isElso());

    }

    @Test
    public void getKiralyFeherTest(){
        Tabla tabla = Tabla.alapTabla();

        Jatekos jatekos = new FeherJatekos(tabla, tabla.getJelenJatekos().getLehetsLepesek(), tabla.getJelenJatekos().getEllenfel().getLehetsLepesek());

        Babu tKiraly = new Kiraly(60, Szin.FEHER, true);

        assertEquals(tKiraly, jatekos.getJatekosKiraly());

    }

    @Test
    public void getKiralyFeketeTest(){
        Tabla tabla = Tabla.alapTabla();

        Jatekos jatekos = new FeketeJatekos(tabla, tabla.getJelenJatekos().getEllenfel().getLehetsLepesek(), tabla.getJelenJatekos().getLehetsLepesek());

        Babu tKiraly = new Kiraly(4, Szin.FEKETE, true);

        assertEquals(tKiraly, jatekos.getJatekosKiraly());
    }

    @Test
    public void uresMezoTest(){
        Mezo ures = new Mezo.UresMezo(45);

        assertEquals(45, ures.getMezoKoord());
        assertFalse(ures.vanBabu());
        assertNull(ures.getBabu());

    }

    @Test
    public void teliMezoTest(){
        Mezo teli = new Mezo.TeliMezo(56, new Kiraly(56, Szin.FEHER, true));

        Babu ujKiraly = new Kiraly(56, Szin.FEHER, true);

        assertEquals(56, teli.getMezoKoord());
        assertTrue(teli.vanBabu());
        assertEquals(ujKiraly, teli.getBabu());

    }

    @Test
    public void tablaUtilTest(){

        boolean tElso = TablaUtil.ElsoSor[4];
        boolean fMasodik = TablaUtil.ElsoSor[40];

        boolean oElso = TablaUtil.ElsoOszlop[8];
        boolean oNyolcadik = TablaUtil.NyolcadikOszlop[20];

        assertTrue(tElso);
        assertFalse(fMasodik);

        assertTrue(oElso);
        assertFalse(oNyolcadik);

    }
}
