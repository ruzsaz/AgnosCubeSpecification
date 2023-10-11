package hu.agnos.cube.specification.util;

import static org.junit.jupiter.api.Assertions.*;

class InfixToPostfixConverterTest {

    @org.junit.jupiter.api.Test
    void convert() {
        System.out.println("Testing InfixToPostfixConverter.convert");

        String in0 = "a + b + c - d - e + f";
        String expected0 = "a b + c + d - e - f +";
        String result0 = InfixToPostfixConverter.convert(in0);
        assertEquals(expected0, result0);

        String in12 = "z-b-u-t-a";
        String expected12 = "z b - u - t - a -";
        String result12 = InfixToPostfixConverter.convert(in12);
        assertEquals(expected12, result12);

        String in10 = "(zolika -buta1 )- buta2";
        String expected10 = "zolika buta1 - buta2 -";
        String result10 = InfixToPostfixConverter.convert(in10);
        System.out.println(result10);
        assertEquals(expected10, result10);

        String in11 = "zolika -buta1- buta2";
        String expected11 = "zolika buta1 - buta2 -";
        String result11 = InfixToPostfixConverter.convert(in11);
        assertEquals(expected11, result11);

        String in2 = "a + b * 3";
        String expected2 = "a b 3 * +";
        String result2 = InfixToPostfixConverter.convert(in2);
        assertEquals(expected2, result2);

        String in3 = "zolika-(buta-okos)";
        String expected3 = "zolika buta okos - -";
        String result3 = InfixToPostfixConverter.convert(in3);
        assertEquals(expected3, result3);

        String in4 = "A + B * C + D";
        String expected4 = "A B C * + D +";
        String result4 = InfixToPostfixConverter.convert(in4);
        assertEquals(expected4, result4);

        String in5 = "((A + B) - C * (D / E)) + F";
        String expected5 = "A B + C D E / * - F +";
        String result5 = InfixToPostfixConverter.convert(in5);
        assertEquals(expected5, result5);

        String in6 = "A+B/C*(D-A)^F^H";
        String expected6 = "A B C / D A - F ^ H ^ * +";
        String result6 = InfixToPostfixConverter.convert(in6);
        assertEquals(expected6, result6);

        String in7 = "(A+B) * (C-D)^F";
        String expected7 = "A B + C D - F ^ *";
        String result7 = InfixToPostfixConverter.convert(in7);
        assertEquals(expected7, result7);
    }

    @org.junit.jupiter.api.Test
    void isOperator() {
        System.out.println("Testing InfixToPostfixConverter.isOperator");
        assertTrue(InfixToPostfixConverter.isOperator('+'));
        assertTrue(InfixToPostfixConverter.isOperator('/'));
        assertFalse(InfixToPostfixConverter.isOperator('a'));
        assertFalse(InfixToPostfixConverter.isOperator(' '));
        assertFalse(InfixToPostfixConverter.isOperator('('));
    }

    @org.junit.jupiter.api.Test
    void isFirstBiggerOrEqualInPrecedence() {
        System.out.println("Testing InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence");
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('-', '-'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('+', '-'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('^', '^'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('^', '/'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('*', '/'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('/', '*'));
        assertTrue(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('/', '+'));
        assertFalse(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('(', '-'));
        assertFalse(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('+', ')'));
        assertFalse(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('-', '*'));
        assertFalse(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('*', '^'));
        assertFalse(InfixToPostfixConverter.isFirstBiggerOrEqualInPrecedence('%', '^'));
    }
}