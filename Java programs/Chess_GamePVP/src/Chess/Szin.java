package Chess;

import Chess.jatekos.FeherJatekos;
import Chess.jatekos.FeketeJatekos;
import Chess.jatekos.Jatekos;

//Szinek osztálya
//2 fajta van belőlük
public enum Szin {

    //Feher szín
    FEHER {

        @Override
        public String toString(){
            return "W";
        }

        @Override
        public boolean isFekete() {
            return false;
        }

        @Override
        public boolean isFeher() {
            return true;
        }

        @Override
        public int getIrany() {
            return -1;
        }

        @Override
        public Jatekos jatekosValaszt(FeherJatekos feherJatekos, FeketeJatekos feketeJatekos) {
            return feherJatekos;
        }

    //Fekete szín
    }, FEKETE {

        @Override
        public String toString(){
            return "B";
        }
        @Override
        public boolean isFekete() {
            return true;
        }

        @Override
        public boolean isFeher() {
            return false;
        }

        @Override
        public int getIrany() {
            return 1;
        }

        @Override
        public Jatekos jatekosValaszt(FeherJatekos feherJatekos, FeketeJatekos feketeJatekos) {
            return feketeJatekos;
        }
    };

    public abstract boolean isFekete();
    public abstract boolean isFeher();

    public abstract int getIrany();

    public abstract Jatekos jatekosValaszt(FeherJatekos feherJatekos, FeketeJatekos feketeJatekos);
}
