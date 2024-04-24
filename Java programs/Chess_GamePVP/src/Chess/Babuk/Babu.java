package Chess.Babuk;

import Chess.Szin;
import Chess.tabla.Lepes;
import Chess.tabla.Tabla;

import java.util.List;

//A Babuk osztálya
//A Basic metódushoz, hogy hogyan indexeljem a lehetságes lépéseket és hogyan adjam meg, hogy melyik Bábú merre tud mozogni
//internetről vettem segítségeket
public abstract class Babu {

    protected BabuFajta babuFajta;
    protected boolean elsoLepesE;
    protected int babuPos;
    protected Szin babuSzin;

    Babu(BabuFajta babuFajta, int spos, Szin sszin, boolean elsoBeallit){
        this.babuFajta = babuFajta;
        babuSzin = sszin;
        babuPos = spos;
        elsoLepesE = elsoBeallit;
    }

    public enum BabuFajta {

        //Ilyen fajta babuk vannak es a isKiraly vissza adja, hogy ilyen fajta bábú-e
        GYALOG("G") {
            @Override
            public boolean isKiraly() {
                return false;
            }

            @Override
            public boolean isGyalog() {
                return true;
            }

            @Override
            public boolean isKiralyno() {
                return false;
            }

            @Override
            public boolean isFuto() {
                return false;
            }

            @Override
            public boolean isCsiko() {
                return false;
            }

            @Override
            public boolean isBastya() {
                return false;
            }
        }, CSIKO("C") {
            @Override
            public boolean isKiraly() {
                return false;
            }

            @Override
            public boolean isGyalog() {
                return false;
            }

            @Override
            public boolean isKiralyno() {
                return false;
            }

            @Override
            public boolean isFuto() {
                return false;
            }

            @Override
            public boolean isCsiko() {
                return true;
            }

            @Override
            public boolean isBastya() {
                return false;
            }
        }, FUTO("F") {
            @Override
            public boolean isKiraly() {
                return false;
            }

            @Override
            public boolean isGyalog() {
                return false;
            }

            @Override
            public boolean isKiralyno() {
                return false;
            }

            @Override
            public boolean isFuto() {
                return true;
            }

            @Override
            public boolean isCsiko() {
                return false;
            }

            @Override
            public boolean isBastya() {
                return false;
            }
        }, BASTYA("B") {
            @Override
            public boolean isKiraly() {
                return false;
            }

            @Override
            public boolean isGyalog() {
                return false;
            }

            @Override
            public boolean isKiralyno() {
                return false;
            }

            @Override
            public boolean isFuto() {
                return false;
            }

            @Override
            public boolean isCsiko() {
                return false;
            }

            @Override
            public boolean isBastya() {
                return true;
            }
        }, KIRALY("K") {
            @Override
            public boolean isKiraly() {
                return true;
            }

            @Override
            public boolean isGyalog() {
                return false;
            }

            @Override
            public boolean isKiralyno() {
                return false;
            }

            @Override
            public boolean isFuto() {
                return false;
            }

            @Override
            public boolean isCsiko() {
                return false;
            }

            @Override
            public boolean isBastya() {
                return false;
            }
        }, KIRALYNO("Q") {
            @Override
            public boolean isKiraly() {
                return false;
            }

            @Override
            public boolean isGyalog() {
                return false;
            }

            @Override
            public boolean isKiralyno() {
                return true;
            }

            @Override
            public boolean isFuto() {
                return false;
            }

            @Override
            public boolean isCsiko() {
                return false;
            }

            @Override
            public boolean isBastya() {
                return false;
            }
        };

        private final String babuNev;

        BabuFajta(String babuNev){
            this.babuNev = babuNev;
        }

        @Override
        public String toString(){
            return this.babuNev;
        }

        public abstract boolean isKiraly();

        public abstract boolean isGyalog();

        public abstract boolean isKiralyno();

        public abstract boolean isFuto();

        public abstract boolean isCsiko();

        public abstract boolean isBastya();
    }

    @Override
    public boolean equals(Object masik){
        if(this == masik){
            return true;
        }else if(!(masik instanceof Babu)){
            return false;
        }else{
            Babu babu = (Babu) masik;
            return this.babuPos == babu.getBabuPos() && this.babuFajta == babu.getFajta() && this.babuSzin == babu.getSzin();
        }
    }

    public int getBabuPos(){
        return this.babuPos;
    }

    //A lehetséges lépések listája
    public abstract List<Lepes> szabLepesek(Tabla stabla);

    public Szin getSzin(){
        return babuSzin;
    }

    public BabuFajta getFajta(){
       return this.babuFajta;
    }

    //Visszaad egy uj babut a lepett babu szinevel es a jelenlegi poziciojat atallitja a celpoziciojara(vegrehajt egy lepest)
    public abstract Babu babuLep(Lepes lepes);

    //Minden bábúnak van egy értéke(a már leütött bábúk sorrendjéhez kell)
    public abstract int getErtek();

    //Megadja, hogy első lépés-e a Bábúval
    public boolean isElso(){
        return elsoLepesE;
    }

}
