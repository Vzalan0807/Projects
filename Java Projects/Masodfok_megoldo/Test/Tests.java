import Engine.Szamolo;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void setDtest(){
        Szamolo sz1 = new Szamolo(1, 3, -4);
        assertEquals(25, sz1.getD(), 0);
    }

    @Test
    public void x1Test(){
        Szamolo sz1 = new Szamolo(1, 3 ,-4);
        assertEquals(1, sz1.getX1(), 0);
    }

    @Test
    public void x2Test(){
        Szamolo sz1 = new Szamolo(1, 3 ,-4);
        assertEquals(-4, sz1.getX2(), 0);
    }


}
