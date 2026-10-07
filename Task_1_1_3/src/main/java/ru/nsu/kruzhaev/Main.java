package ru.nsu.kruzhaev;

import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Expression e = new Add("(3+(2*x))");
//        ExpressionParser ep = new ExpressionParser("((3-(129/x))+(0*z))");
//        Expression es = ep.getRes();
        Expression e2 = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        System.out.println(e.equals(e2));


//        int result = e.eval("x=3 ; z =1; y=8");
//        System.out.println(result);
//
//        e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
//
//        Expression de = e.derivative("x");
//        de.print();
    }
}
