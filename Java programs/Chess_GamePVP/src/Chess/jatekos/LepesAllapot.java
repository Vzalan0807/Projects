package Chess.jatekos;

public enum LepesAllapot {
    //3 fajta állapotot külnböztettem meg ezek az alábbiak:
    Vegrehajtva {
        @Override
        public boolean isVegrehajtva() {
            return true;
        }
    },
    NemLehetsLepes{
        @Override
        public boolean isVegrehajtva() {
            return false;
        }
    },
    SakkbanHagy{
        @Override
        public boolean isVegrehajtva() {
            return false;
        }
    };

    //Visszaadja, hogy a lepes lehetséges-e
    public abstract boolean isVegrehajtva();
}
