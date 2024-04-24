package Chess.tabla;

import Chess.Babuk.Babu;
import Chess.Babuk.Bastya;
import Chess.Babuk.Gyalog;
import Chess.tabla.Tabla.Generator;


//Lepesek deklarációja és definíciója
public abstract class Lepes {

    //Minde lépésnek van egy hozzátartozó táblája, mozgatott Bábúja, cél koordinátája, és, hogy elso lépés-e
    final Tabla mtabla;
    final Babu mBabu;
    final int celKoord;

    private Lepes(Tabla stabla, Babu sBabu, int skoord){
        mtabla = stabla;
        mBabu = sBabu;
        celKoord = skoord;
    }

    private Lepes(Tabla stabla, int skoord){
        mtabla = stabla;
        mBabu = null;
        celKoord = skoord;
    }

    @Override
    public String toString(){
        if(mBabu == null){
            return "--";
        }
        return Integer.toString(mBabu.getBabuPos()) + "-->" + Integer.toString(getCelKoord());
    }


    @Override
    public boolean equals(Object masik){
        Lepes masik2 = (Lepes) masik;
        if(this.mBabu.equals(masik2.mBabu) && this.celKoord == masik2.celKoord){
            return true;
        }
        return false;
    }

    //Létrehoz egy lépést a megadott kiinduló és célpozíció segítségével
    public static Lepes lepesCsinal(Tabla tabla, int jelenPos, int celPos){
        for(Lepes lepes : tabla.getLehetsLepesek()){
            if(lepes.getJelenPos() == jelenPos && lepes.getCelKoord() == celPos){
                return lepes;
            }
        }
        return new RosszLepes();
    }


    public int getJelenPos(){
        return this.mBabu.getBabuPos();
    }

    //Megvalósít, egy lépést, úgyhogy a mozgatott bábú kivételével az összes bábút felállítja az eredeti pozíciójába.
    public Tabla megvalosit() {
        Generator generalo = new Generator();
        for(Babu b : this.mtabla.getJelenJatekos().getJelenBabuk()){
            if(!this.mBabu.equals(b)){
                generalo.setBabu(b);
            }
        }

        for(Babu ba : this.mtabla.getJelenJatekos().getEllenfel().getJelenBabuk()){
            generalo.setBabu(ba);
        }
        //A mozgatott bábút pedig felállítja új pozícióba
        generalo.setBabu(this.mBabu.babuLep(this));
        generalo.setLepes(this.mtabla.getJelenJatekos().getEllenfel().getSzin());

        return generalo.generate();
    }



    public Babu getmBabu(){
        return this.mBabu;
    }

    public boolean isTamadas(){
        return false;
    }


    public Babu getTamadottBabu(){
        return null;
    }

    // Sima az amikor egy olyan mezőre lépünk, ahol nem áll bábú
    public static class SimaLepes extends Lepes{
        public SimaLepes(Tabla stabla, Babu sBabu, int skoord) {
            super(stabla, sBabu, skoord);
        }

    }

    public int getCelKoord(){
        return this.celKoord;
    }


    // Támadó az amikor olyan mezőre lépünk ahol ellenséges bábú áll
    public static class TamadoLepes extends Lepes{
        Babu tamadott;
        public TamadoLepes(Tabla stabla, Babu sBabu, int skoord, Babu stamadott) {
            super(stabla, sBabu, skoord);
            tamadott = stamadott;
        }

        @Override
        public boolean isTamadas(){
            return true;
        }

        @Override
        public Babu getTamadottBabu(){
            return this.tamadott;
        }
    }


    //Castle lépés, az a lépés, amikor a bástyát és a kiráylt felcseréljük
    //Az elv és az osztály és leszármazottainak egy pár metódusát az internet segítségével csináltam, mivel nem tudtam, hogy létezik ez a szabály
    public static abstract class CastleLepes extends Lepes{

        protected Bastya castleBastya;
        protected int castleBastyaJelenPos;
        protected int castleBastyaCelPos;

        public CastleLepes(Tabla stabla, Babu sBabu, int skoord, Bastya castleBastya, int castleBastyaJelenPos, int castleBastyaCelPos) {
            super(stabla, sBabu, skoord);
            this.castleBastya = castleBastya;
            this.castleBastyaJelenPos = castleBastyaJelenPos;
            this.castleBastyaCelPos = castleBastyaCelPos;
        }


        @Override
        public Tabla megvalosit(){

            Generator gen = new Generator();
            for(Babu babu : this.mtabla.getJelenJatekos().getJelenBabuk()){
                if(!this.mBabu.equals(babu) && !this.castleBastya.equals(babu)){
                    gen.setBabu(babu);
                }
            }
            for(Babu babu : this.mtabla.getJelenJatekos().getEllenfel().getJelenBabuk()){
                gen.setBabu(babu);
            }
            gen.setBabu(this.mBabu.babuLep(this));
            gen.setBabu(new Bastya(this.castleBastyaCelPos, this.castleBastya.getSzin()));
            gen.setLepes(this.mtabla.getJelenJatekos().getEllenfel().getSzin());
            return gen.generate();
        }

    }

    //Ha a király felöli oldalon csináljuk
    public static class KiralyOldalCastle extends CastleLepes{
        public KiralyOldalCastle(Tabla stabla, Babu sBabu, int skoord, Bastya castleBastya, int castleBastyaJelenPos, int castleBastyaCelPos) {
            super(stabla, sBabu, skoord, castleBastya, castleBastyaJelenPos, castleBastyaCelPos);
        }

    }

    //Ha a királynő felőli oldalon csináljuk
    public static class KiralynoOldalCastle extends CastleLepes{
        public KiralynoOldalCastle(Tabla stabla, Babu sBabu, int skoord, Bastya castleBastya, int castleBastyaJelenPos, int castleBastyaCelPos) {
            super(stabla, sBabu, skoord, castleBastya, castleBastyaJelenPos, castleBastyaCelPos);
        }

    }

    //Roszzlépés, ha egy lépés valamilyen okból nem valósítható meg.
    public static class RosszLepes extends Lepes{
        public RosszLepes() {
            super(null,65);
        }

        @Override
        public Tabla megvalosit(){
            throw new RuntimeException("Nem lehetseges lepes.");
        }

        @Override
        public int getJelenPos(){
            return -1;
        }

    }


}
