package Engine;

import static java.lang.Math.sqrt;

public class Szamolo {

    double a;
    double b;
    double c;
    double d;

    public Szamolo(double ia, double ib, double ic){
        a = ia;
        b = ib;
        c = ic;
        d = b*b-4*a*c;
    }

    private Szamolo(){ throw new RuntimeException("No access");}

    public double getD(){
        return d;
    }
    public Double getX1(){
        if(d >= 0){
            return (-1 * b + sqrt(d))/2*a;
        }
        return null;
    }

    public Double getX2(){
        if(d >= 0){
            return (-1 * b - sqrt(d))/2*a;
        }
        return null;
    }


}
