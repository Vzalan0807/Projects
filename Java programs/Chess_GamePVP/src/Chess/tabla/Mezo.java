package Chess.tabla;

import Chess.Babuk.Babu;

import java.util.HashMap;
import java.util.Map;

//Mezők
public abstract class Mezo {

    //Minden mezőnek van egy koordinátája és egy Map-ja a mezőkről és a számukról
    protected int koord;
    public static Map<Integer, UresMezo> Ures_Mezo = createlAllUresMezo();


    //Feltölti az egeész Map-ot üres mezőkkel
    private static Map<Integer, UresMezo> createlAllUresMezo() {

        Map<Integer, UresMezo> uresMezoMap = new HashMap<>();
        for(int i = 0; i < 64; i++){
            uresMezoMap.put(i, new UresMezo(i));
        }
        return uresMezoMap;
    }

    //Mezok letrehozasa attol fuggoen, hogy kell-e lennie valaminek a mezőn
    public static Mezo mezoLetrehoz(int skoord, Babu sbabu){
        return sbabu != null ? new TeliMezo(skoord, sbabu) : Ures_Mezo.get(skoord);
    }

    public int getMezoKoord(){
        return this.koord;
    }

    private Mezo (int skoord){
        koord = skoord;
    }
    //Megadja, hogy van-e valami a mezőn
    public abstract boolean vanBabu();

    //Megadja, hogy mi van a mezőn
    public abstract Babu getBabu();

    //Üres mezők osztálya
    public static class UresMezo extends Mezo{
        public UresMezo(int skoord){
            super(skoord);
        }

        @Override
        public boolean vanBabu(){
            return false;
        }

        @Override
        public Babu getBabu(){
            return null;
        }

    }

    //Teli mezők osztálya (amelyeken van bábú)
    public static class TeliMezo extends Mezo{

        //Minde teli mezőn van egy bábú
        private final Babu babuOn;

        public TeliMezo(int skoord, Babu sbabu){
            super(skoord);
            babuOn = sbabu;
        }

        @Override
        public boolean vanBabu(){
            return true;
        }

        @Override
        public Babu getBabu(){
            return babuOn;
        }


    }


}
