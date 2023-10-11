package hu.agnos.cube.specification.util;

import java.util.Stack;

/**
 *
 * @author parisek
 */
public class InfixToPostfixConverter {

    public static String convert(String infixString) {
        StringBuilder result = new StringBuilder();

        Stack<Character> stackForOperators = new Stack<>();

        for (int i = 0; i < infixString.length(); i++) {
            char c = infixString.charAt(i);
            //the spaces are removed from the expression
            if (c != ' ') {
                //if operators
                if (isOperator(c)) {
                    result.append(' ');
                    while (!stackForOperators.empty() && isFirstBiggerOrEqualInPrecedence(stackForOperators.peek(), c)) {
                        result.append(stackForOperators.pop());
                        result.append(' ');
                    }

                    stackForOperators.push(c);
                } else if (c == '(') {
                    stackForOperators.push(c);
                } else if (c == ')') {
                    while (!stackForOperators.peek().equals('(')) {
                        result.append(' ');
                        result.append(stackForOperators.pop());
                    }
                    stackForOperators.pop();
                } else {
                    result.append(c);
                }
            }
        }
        result.append(' ');
        while (!stackForOperators.empty()) {
            result.append(stackForOperators.pop());
            result.append(' ');
        }
        return result.toString().trim();
    }

    public static boolean isOperator(char c) {
        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/'
                || c == '^';
    }

    public static boolean isFirstBiggerOrEqualInPrecedence(char c1, char c2) {
        return ( (c2 == '+' || c2 == '-') && isOperator(c1) )
                || ( (c2 == '*' || c2 == '/') && (c1 == '*' || c1 == '/' || c1 == '^') )
                || (c2 == '^' && c1 == '^');
    }

}
