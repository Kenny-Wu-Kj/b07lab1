import java.util.concurrent.CountDownLatch;
import java.io.*;

public class Driver { 
    public static void main(String [] args) throws IOException{ 
    File f = new File("/Users/kennywu/b07lab1/file.txt"); //Change to your dir
    
    System.out.println("-----------------------------------------" + 
                      "\n" + "Test for Polynomial Class: " + "\n");

    //Test constructor Polynomial(File f)
    Polynomial p = new Polynomial(f);
    System.out.println("P1: " + p);
    System.out.println();
    
    //Test constructor Polynomial(double[] c, int[] e) 
    //    - Precon: each exponent number has to be an unique integer
    double [] c1 = {6,5}; 
    int [] e1 = {3,1};
    Polynomial p1 = new Polynomial(c1, e1);
    System.out.println("P2: " + p1);
    System.out.println();

    //Test constructor Polynomial()
    Polynomial p2 = new Polynomial();
    System.out.println("P3: " + p2);
    System.out.println();

    //Test method double evaluate(double x)
    System.out.println("Result of P1(x=0.5): " + "\n" + p.evaluate(0.5));
    System.out.println("Result of P1(x=1): " + "\n" + p.evaluate(1.0));
    System.out.println("Result of P1(x=2): " + "\n" + p.evaluate(2.0));
    System.out.println();

    //Test method boolean hasRoot(double r)
    System.out.println("Whether x=0 is a root of P1: " + p.hasRoot(0));
    System.out.println("Whether x=1.5 is a root of P1: " + p.hasRoot(1.5));
    System.out.println();

    //Test method Polynomial add(Polynomial p)
    System.out.println("Result of Addition (P1 + P2): " + "\n" + p.add(p1));
    System.out.println();
    
    //Test method Polynomial multiply(Polynomial p)
    System.out.println("Result of Multiplication (P1 * P2): " + "\n" + p.multiply(p1));
    System.out.println();

    //Test method void saveToFile(String filename)
    p1.saveToFile("/Users/kennywu/b07lab1/file_output.txt"); //Change to your dir
    System.out.println();
    
    //These are some public supplementary methods Polynomial class
    //Test void bubbleSort();
    p1.bubbleSort();
    System.out.println("Result of bubbleSorting P2: " + "\n" + p1);
    System.out.println();

    //Test void cleaner() - Pre: Calling object can't be empty
    double [] c2 = {-6,0,2}; 
    int [] e2 = {3,1,4};
    Polynomial p3 = new Polynomial(c2, e2);
    System.out.println("New Polynomial: " + p3);
    p3.cleaner();
    System.out.println("After calling cleaner(): " + "\n" + p3);
    System.out.println();
    } 
   } 
    