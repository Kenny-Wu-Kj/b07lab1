import java.lang.*;

public class Polynomial{
    private double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] a){
        this.coefficients = new double[a.length];
        this.coefficients = a;
    }

    public Polynomial add(Polynomial p){
        double[] sum;/*Math.max(this.coefficients.length, p.coefficients.length)*/
        if(this.coefficients.length >= p.coefficients.length){
            sum = this.coefficients;
            for(int i = 0; i < p.coefficients.length; i++)
                sum[i] = this.coefficients[i] + p.coefficients[i];
        }else{
            sum = p.coefficients;
            for(int i = 0; i < this.coefficients.length; i++)
                sum[i] = this.coefficients[i] + p.coefficients[i];
        }
    
        Polynomial result = new Polynomial(sum);
        return result;
    }
    public double evaluate(double x){
        double sum = 0;

        for(int i = 0; i < this.coefficients.length; i++){
            sum += this.coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double r){
        return this.evaluate(r) == 0;
    }
}

/***
public class main{

    Polynomial poly1 = new Polynomial();
    Polynomial poly2 = new Polynomial(new double[] {1.0, 2.0});

    poly1.add(poly)

}
**/