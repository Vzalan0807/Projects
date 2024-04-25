package Chess.tabla;

import java.util.Arrays;

public class TablaUtil {

    //Megadja, hogy a jelenlegi, hogy mely koordináták tartoznak az x-edik sorba

    public static boolean[] NyolcadikSor = kivSor(56);
    public static boolean[] HetedikSor = kivSor(48);
    public static boolean[] HatodikSor = kivSor(40);
    public static boolean[] OtodikSor = kivSor(32);
    public static boolean[] NegyedikSor = kivSor(24);
    public static boolean[] HarmadikSor = kivSor(16);
    public static boolean[] MasodikSor = kivSor(8);
    public static boolean[] ElsoSor = kivSor(0);


    //Megadja, hogy a jelenlegi, hogy mely koordináták tartoznak az x-edik oszlopba
    public static boolean[] ElsoOszlop = kivOszlop(0);
    public static boolean[] MasodikOszlop = kivOszlop(1);
    public static boolean[] HetedikOszlop = kivOszlop(6);
    public static boolean[] NyolcadikOszlop = kivOszlop(7);

    private TablaUtil(){}

    // Megadja, hogy egy koordinátára léphetünk-e
    public static boolean lehetsLepes(final int koord) {
        return koord >= 0 && koord < 64;
    }

    //Megadja a megadott koordinátával egy sorban lévő koordinátákat
    private static boolean[] kivSor(int mezoKoor){
        boolean[] vissza = new boolean[64];
        Arrays.fill(vissza, false);
        do{
            vissza[mezoKoor] = true;
            mezoKoor++;
        }while(mezoKoor % 8 != 0);

        return vissza;

    }

    //Megadja a megadott koordinátával egy oszlopban lévő koordinátákat
    public static boolean[] kivOszlop(int mezoKoor){
        boolean[] vissza = new boolean[64];
        Arrays.fill(vissza, false);
        do{
            vissza[mezoKoor] = true;
            mezoKoor += 8;
        }while(mezoKoor < 64);
        return vissza;
    }


}
