package projet.test;

import java.util.Objects;

//Systeme de Test Unitaire fait main.
public class Assertion {

    public static void AssertTrue(boolean condition, String message){
        if (!condition) throw new RuntimeException("Assertion failed : " + message);
    }

    public static void AssertEqual (Object a, Object b){
        AssertTrue(Objects.equals(a, b), a.toString() + " != " + b.toString());
    }
}
