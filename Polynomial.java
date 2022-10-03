import java.io.File;
import java.lang.*;
import java.io.*;

public class Polynomial{
    private double[] coefficients;
    private int[] exponents;

    public Polynomial(){
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    public Polynomial(double[] c, int[] e){
        //this.coefficients = new double[a.length];
        this.coefficients = c;
        this.exponents = e;
    }

    public Polynomial(File f) throws IOException{
        
        //System.out.println("Exists: " + f.exists());
        //if (f.exists()) {
        //    System.out.println("Is readable: " + f.canRead());
            FileReader input = new FileReader(f);
            BufferedReader b = new BufferedReader(input);
            String s = b.readLine();
            String[] terms = s.split("\\+|(?=-)");
            String[][] num_of_terms = new String[terms.length][];

            for(int i = 0; i < terms.length; i++){
                num_of_terms[i] = terms[i].split("(?=x)");
            }

            this.coefficients = new double[terms.length];
            this.exponents = new int[terms.length];

            for(int i = 0; i < num_of_terms.length; i++){
                this.coefficients[i] = Double.parseDouble(num_of_terms[i][0]);
                if(num_of_terms[i].length > 1){
                    if(num_of_terms[i][1].length() > 1){
                        this.exponents[i] 
                        = Integer.parseInt(num_of_terms[i][1].substring(1, 2));
                    }else{
                        this.exponents[i] = 1;
                    }
                }else{
                    this.exponents[i] = 0;
                }
            }
            input.close();
            b.close();
        //}

    }

    //A public method used to sort the polynomial exponents from small to large
    public void bubbleSort(){
        int n = this.exponents.length - 1;
        int temp_exponent = 0;
        double temp_coefficient = 0;
        while(n >= 1){
            for(int i = 0; i < n; i++){
                if(this.exponents[i] > this.exponents[i+1]){
                    temp_exponent = this.exponents[i];
                    temp_coefficient = this.coefficients[i];

                    this.exponents[i] = this.exponents[i+1];
                    this.coefficients[i] = this.coefficients[i+1];

                    this.exponents[i+1] = temp_exponent;
                    this.coefficients[i+1] = temp_coefficient;
                }
            }
            n--;
        }
    }

    //This method return the # of terms in this having exponents that are not in p;
    private int count(Polynomial p){
        int counter = 0;
        int x = 0;
        this.bubbleSort();
        p.bubbleSort();
        for(int i = 0; i < this.exponents.length; i++){
            while(this.exponents[i] > p.exponents[x]){
                x++;
                if(x >= p.exponents.length) return counter + (this.exponents.length - i);
            }
            if(this.exponents[i] < p.exponents[x]){
                counter++;
                //System.out.println("this.exp.in: " + i + "; p.exp.in: " + x);
            }
        }
        return counter;
    }

    //clean the empty term in polynomial
    public void cleaner(){

        //find the numbers of 0 in cofficient array
        int counter = 0;
        for(double coef : this.coefficients){
            if (coef == 0) counter++;
            //System.out.println("Found " + counter + " zeros!");
        }

        double[] new_c = new double[this.exponents.length - counter];
        int[] new_e = new int[this.exponents.length - counter];

        int index_new_of_p = 0;
        for(int i = 0; i < this.exponents.length; i++){
            if(this.coefficients[i] != 0){
                new_c[index_new_of_p] = this.coefficients[i];
                new_e[index_new_of_p] = this.exponents[i];
                index_new_of_p++;
            }
        }

        this.coefficients = new_c;
        this.exponents = new_e;
    }
    
    //This method adds up Polynomial p to the calling Polynomial, redundant terms will be
    //removed by calling cleaner()
    public Polynomial add(Polynomial p){
        int sum_length = this.exponents.length + p.count(this); //count including sorting
        double[] s_coef = new double[sum_length];
        int[] s_expo = new int[sum_length];
        int s_index = this.exponents.length;

        for(int i = 0; i < this.exponents.length; i++) s_expo[i] = this.exponents[i];
        for(int i = 0; i < this.coefficients.length; i++) s_coef[i] = this.coefficients[i];

        int j = 0;
        for(int i = 0; i < p.exponents.length; i++){
          
            while(j < this.exponents.length){
                //System.out.println("Compare " + p.coefficients[i] + 
                //                   "x" + p.exponents[i] + " with "+this.coefficients[j] + 
                //                   "x"+this.exponents[j]);
                if(p.exponents[i] == this.exponents[j]){
                    s_coef[j] = p.coefficients[i] + this.coefficients[j];
                    break;
                }else if(p.exponents[i] < this.exponents[j]){
                    //System.out.println(s_index);
                    s_expo[s_index] = p.exponents[i];
                    s_coef[s_index] = p.coefficients[i];
                    s_index++;
                    break;
                }
                j++;
            }
            if(j == this.exponents.length){
                //System.out.println(s_index);
                s_expo[s_index] = p.exponents[i];
                s_coef[s_index] = p.coefficients[i];
                s_index++;
            }
        }
    
        Polynomial result = new Polynomial(s_coef, s_expo);
        result.cleaner();
        return result;
    }

    //multiply(Polynomial p) that takes one argument of type Polynomial and 
    //returns the polynomial resulting from multiplying the calling object and the 
    //argument. The resulting polynomial should not contain redundant exponents. 
    public Polynomial multiply(Polynomial p){
        Polynomial result = new Polynomial();
        Polynomial[] poly_array = new Polynomial[this.exponents.length];

        for(int i = 0; i < this.exponents.length; i++){
            double[] c = new double[p.exponents.length];
            int[] e = new int[p.exponents.length];

            for(int j = 0; j < p.coefficients.length; j++){
                c[j] = this.coefficients[i] * p.coefficients[j];
                e[j] = this.exponents[i] + p.exponents[j];
            }
            poly_array[i] = new Polynomial(c, e);
        }

        for(Polynomial P : poly_array)
            result = result.add(P);
        
        result.cleaner();
        return result;
    }
     
    public double evaluate(double x){
        double sum = 0;

        for(int i = 0; i < this.coefficients.length; i++){
            sum += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double r){
        return (this.evaluate(r) == 0);
    }
    
    public void saveToFile(String filename) throws IOException{
        PrintStream ps = new PrintStream(filename);
        ps.println(this);
        ps.close();
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < this.exponents.length; i++){

            //Convention for signed term
            if(this.coefficients[i] >= 0){
                s +=  "+";
            }

            //Convention for decimal point of coefficients
            if(this.coefficients[i] == (int)this.coefficients[i]){
                s +=  (int)this.coefficients[i] + "x";
            }else{
                s +=  this.coefficients[i] + "x";
            }
            
            //Convention for exponents
            if(this.exponents[i] == 0){
                s.substring(0, s.length()-1);
            }else if(this.exponents[i] == 1){

            }else{
                s += this.exponents[i];
            }
        }
        
        //Convention for first signed term
        if(this.coefficients[0] >= 0){
            return s.substring(1, s.length());
        }
        return s;  
    }
}

/***
public class main{

    Polynomial poly1 = new Polynomial();
    Polynomial poly2 = new Polynomial(new double[] {1.0, 2.0});

    poly1.add(poly)

}
**/