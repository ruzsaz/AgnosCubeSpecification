package hu.agnos.cube.specification.util;

import hu.agnos.cube.specification.exception.InvalidPostfixExpressionException;
import java.util.Stack;

/**
 *
 * @author parisek
 */
public class PostfixToInfixConverter {

      public static boolean isOperator(String text) {
          return text != null && text.length() == 1
                  && InfixToPostfixConverter.isOperator(text.toCharArray()[0]);
     }

    public static boolean includeOperator(String text) {
        return text != null && (text.contains("+") || text.contains("-") || text.contains("/") || text.contains("*") || text.contains("^"));
    }

    // Converting the given postfix expression to 
    // infix expression
    public static String convert(String postfix) throws InvalidPostfixExpressionException {

        Stack<String> s = new Stack<>();

        for(String actualSegment : postfix.split(" ")){
            
            // Check whether given postfix location
            // at actualSegment is an operator or not
            if (isOperator(actualSegment)) {
                // When operator exist
                // Check that two operands exist or not
                if (s.size() > 1) {
                    String operand1 = s.pop();
                    String operand2 = s.pop();
                    if (includeOperator(operand1)) {
                        operand1 = "(" + operand1 + ")";
                    }
                    if (includeOperator(operand2)) {
                        operand2 = "(" + operand2 + ")";
                    }
                    s.push( operand2 + " " + actualSegment  + " " + operand1);
                } else {
                    throw new InvalidPostfixExpressionException(postfix);
                }
            } else {
                // When get valid operands
                s.push(actualSegment);
            }
        }
        return s.pop();
    }
}

