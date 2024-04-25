package Chess.jatekos;

import Chess.tabla.Lepes;
import Chess.tabla.Tabla;

//Ez az osztály azért szükséges, tároljuk el az egyes lépésekről hogy megvalósítható-e
public class LepesValosit {

    private final Tabla valositasTabla;
    private final Lepes lepes;
    private final LepesAllapot allapot;

    public LepesValosit(Tabla in, Lepes inL, LepesAllapot inAllapot){
        this.valositasTabla = in;
        this.lepes = inL;
        this.allapot = inAllapot;
    }

    public LepesAllapot getLepesAllapot(){
        return this.allapot;
    }

    public Tabla getTabla(){
        return this.valositasTabla;
    }
}
