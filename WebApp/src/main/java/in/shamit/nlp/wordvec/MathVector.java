package in.shamit.nlp.wordvec;

import java.io.PrintStream;

/******************************************************************************
 *  Compilation:  javac Vector.java
 *  Execution:    java Vector
 *
 *  Implementation of a vector of real numbers.
 *
 *  This class is implemented to be immutable: once the client program
 *  initialize a Vector, it cannot change any of its fields
 *  (N or data[i]) either directly or indirectly. Immutability is a
 *  very desirable feature of a data type.
 *
 *
 *  % java Vector
 *  x        =  (1.0, 2.0, 3.0, 4.0)
 *  y        =  (5.0, 2.0, 4.0, 1.0)
 *  x + y    =  (6.0, 4.0, 7.0, 5.0)
 *  10x      =  (10.0, 20.0, 30.0, 40.0)
 *  |x|      =  5.477225575051661
 *  <x, y>   =  25.0
 *  |x - y|  =  5.0990195135927845
 *
 *  Note that java.util.Vector is an unrelated Java library class.
 *
 ******************************************************************************/

public class MathVector { 

    private final int n;         // length of the vector
    private float[] data;       // array of vector's components

    // create the zero vector of length n
    public MathVector(int n) {
        this.n = n;
        this.data = new float[n];
    }

    // create a vector from an array
    public MathVector(float[] data) {
        n = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new float[n];
        for (int i = 0; i < n; i++)
            this.data[i] = data[i];
    }

    // create a vector from either an array or a vararg list
    // this constructor uses Java's vararg syntax to support
    // a constructor that takes a variable number of arguments, such as
    // Vector x = new Vector(1.0, 2.0, 3.0, 4.0);
    // Vector y = new Vector(5.0, 2.0, 4.0, 1.0);
/*
    public Vector(float... data) {
        n = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new float[n];
        for (int i = 0; i < n; i++)
            this.data[i] = data[i];
    }
*/
    // return the length of the vector
    public int length() {
        return n;
    }

    // return the inner product of this Vector a and b
    public float dot(MathVector that) {
        if (this.length() != that.length())
            throw new IllegalArgumentException("dimensions disagree");
        float sum = 0.0f;
        for (int i = 0; i < n; i++)
            sum = sum + (this.data[i] * that.data[i]);
        return sum;
    }

    // return the Euclidean norm of this Vector
    public float magnitude() {
        return (float) Math.sqrt(this.dot(this));
    }

    // return the Euclidean distance between this and that
    public float distanceTo(MathVector that) {
        if (this.length() != that.length())
            throw new IllegalArgumentException("dimensions disagree");
        return this.minus(that).magnitude();
    }

    // return this + that
    public MathVector plus(MathVector that) {
        if (this.length() != that.length())
            throw new IllegalArgumentException("dimensions disagree");
        MathVector c = new MathVector(n);
        for (int i = 0; i < n; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    // return this - that
    public MathVector minus(MathVector that) {
        if (this.length() != that.length())
            throw new IllegalArgumentException("dimensions disagree");
        MathVector c = new MathVector(n);
        for (int i = 0; i < n; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    // return the corresponding coordinate
    public float cartesian(int i) {
        return data[i];
    }

    // create and return a new object whose value is (this * factor)
    @Deprecated
    public MathVector times(float factor) {
        MathVector c = new MathVector(n);
        for (int i = 0; i < n; i++)
            c.data[i] = factor * data[i];
        return c;
    }

    // create and return a new object whose value is (this * factor)
    public MathVector scale(float factor) {
        MathVector c = new MathVector(n);
        for (int i = 0; i < n; i++)
            c.data[i] = factor * data[i];
        return c;
    }


    // return the corresponding unit vector
    public MathVector direction() {
        if (this.magnitude() == 0.0)
            throw new ArithmeticException("zero-vector has no direction");
        return this.times(1.0f / this.magnitude());
    }

    // return a string representation of the vector
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('(');
        for (int i = 0; i < n; i++) {
            s.append(data[i]);
            if (i < n-1) s.append(", ");
        }
        s.append(')');
        return s.toString();
    }


    // test client
    public static void main(String[] args) {
        PrintStream StdOut = System.out;
    	float[] xdata = { 1.0f, 2.0f, 3.0f, 4.0f };
        float[] ydata = { 5.0f, 2.0f, 4.0f, 1.0f };

        MathVector x = new MathVector(xdata);
        MathVector y = new MathVector(ydata);

        StdOut.println("x        =  " + x);
        StdOut.println("y        =  " + y);
        StdOut.println("x + y    =  " + x.plus(y));
        StdOut.println("10x      =  " + x.times((float) 10.0));
        StdOut.println("|x|      =  " + x.magnitude());
        StdOut.println("<x, y>   =  " + x.dot(y));
        StdOut.println("|x - y|  =  " + x.minus(y).magnitude());
    }

	public float[] getValues() {
		return data;
	}
}
