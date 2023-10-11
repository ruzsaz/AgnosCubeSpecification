package hu.agnos.cube.specification.util;

import static org.junit.jupiter.api.Assertions.*;

import hu.agnos.cube.specification.exception.InvalidPostfixExpressionException;

class PostfixToInfixConverterTest {

    @org.junit.jupiter.api.Test
    void convert() throws InvalidPostfixExpressionException {
        System.out.println("Testing PostfixToInfixConverter.convert");

        String in0 = "a b + c + d - e - f +";
        String expected0 = "((((a + b) + c) - d) - e) + f";
        String result0 = PostfixToInfixConverter.convert(in0);
        assertEquals(expected0, result0);

        String in12 = "z b - u - t - a -";
        String expected12 = "(((z - b) - u) - t) - a";
        String result12 = PostfixToInfixConverter.convert(in12);
        assertEquals(expected12, result12);

        String in10 = "zolika buta1 - buta2 -";
        String expected10 = "(zolika - buta1) - buta2";
        String result10 = PostfixToInfixConverter.convert(in10);
        assertEquals(expected10, result10);

        String in2 = "a b 3 * +";
        String expected2 = "a + (b * 3)";
        String result2 = PostfixToInfixConverter.convert(in2);
        assertEquals(expected2, result2);

        String in3 = "zolika buta okos - -";
        String expected3 = "zolika - (buta - okos)";
        String result3 = PostfixToInfixConverter.convert(in3);
        assertEquals(expected3, result3);

        String in4 = "A B C * + D +";
        String expected4 = "(A + (B * C)) + D";
        String result4 = PostfixToInfixConverter.convert(in4);
        assertEquals(expected4, result4);

        String in5 = "A B + C D E / * - F +";
        String expected5 = "((A + B) - (C * (D / E))) + F";
        String result5 = PostfixToInfixConverter.convert(in5);
        assertEquals(expected5, result5);

        String in6 = "A B C / D A - F ^ H ^ * +";
        String expected6 = "A + ((B / C) * (((D - A) ^ F) ^ H))";
        String result6 = PostfixToInfixConverter.convert(in6);
        assertEquals(expected6, result6);

        String in7 = "A B + C D - F ^ *";
        String expected7 = "(A + B) * ((C - D) ^ F)";
        String result7 = PostfixToInfixConverter.convert(in7);
        assertEquals(expected7, result7);
    }

    @org.junit.jupiter.api.Test
    void isOperator() {
        System.out.println("Testing PostfixToInfixConverter.isOperator");
        assertTrue(PostfixToInfixConverter.isOperator("+"));
        assertTrue(PostfixToInfixConverter.isOperator("/"));
        assertFalse(PostfixToInfixConverter.isOperator("+a"));
        assertFalse(PostfixToInfixConverter.isOperator(" "));
        assertFalse(PostfixToInfixConverter.isOperator("("));
    }

    @org.junit.jupiter.api.Test
    void includeOperator() {
        System.out.println("Testing PostfixToInfixConverter.includeOperator");
        assertTrue(PostfixToInfixConverter.includeOperator("a + b"));
        assertTrue(PostfixToInfixConverter.includeOperator("zo/bgf+-"));
        assertFalse(PostfixToInfixConverter.includeOperator("(bhgf)(5)"));
        assertFalse(PostfixToInfixConverter.includeOperator(""));
        assertFalse(PostfixToInfixConverter.includeOperator(null));
    }

}