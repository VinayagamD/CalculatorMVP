package com.duraiv2.calculatormvp;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

/**
 * Created by duraiv2 on 10/4/2018.
 */

public class Calculation {

    private final Symbols symbols;
    private CalculatorResult calculatorResult;
    private static String currentExpression;

    interface CalculatorResult{
        void onExpressionChanged(String result, boolean successful);
    }

    public void setCalculatorResultListener(CalculatorResult calculatorResult){
        this.calculatorResult = calculatorResult;
        currentExpression = "";
    }

    public Calculation() {
        symbols = new Symbols();
    }

    /**
     * Delete a single character from currentExpression, unless empty
     *  "" - Invalid
     *  543 - valid
     *  45+85 - valid
     */
    public void deleteCharacter(){
        if (currentExpression.length() > 0 ){
            currentExpression = currentExpression.substring(0, currentExpression.length()-1);
            calculatorResult.onExpressionChanged(currentExpression, true);
        }else {
            calculatorResult.onExpressionChanged("Invalid Input ",false);
        }
    }

    /**
     * Delete Entire expression unless empty
     * "" - Invalid
     * 543 - valid
     * 45+85 - valid
     */
    public void deleteExpression(){
        if (currentExpression.equals("")){
            calculatorResult.onExpressionChanged("Invalid input",false);
        }else {
            currentExpression = "";
            calculatorResult.onExpressionChanged(currentExpression,true);
        }

    }

    /**
     * Append number to currentExpression if valid:
     * "0" & number is 0 - invalid
     *  "12345678909876543" - invalid
     * @param number
     */
    public void appendNumber(String number){
        if (currentExpression.equals("0")&&number.equals("0")){
            calculatorResult.onExpressionChanged("Invalid input",false);
        }else{
            if (currentExpression.length() <= 16){
                currentExpression += number;
                calculatorResult.onExpressionChanged(currentExpression,true);
            }else {
                calculatorResult.onExpressionChanged("Expression Too long",false);
            }
        }
    }

    /**
     * Accept an operator to currentExpression, if valid:
     * 56 - valid
     * 56* - invalid
     * 56+2 - valid
     * "" - invalid
     * @param operator one of:
     *  - "+"
     *  - "-"
     *  - "*"
     *  - "/"
     *  - "%"
     */
    public void appendOperator(String operator){
        if (validateExpression(currentExpression)){
            currentExpression += operator;
            calculatorResult.onExpressionChanged(currentExpression,true);
        }
    }

    public void appendDecimal(){

    }

    /**
     * if expression passes checks , pass current expression to symbols object
     * then return the result
     *
     *
     */
    public void performEvaluation(){
        if (validateExpression(currentExpression)){
            try{
                Double result = symbols.eval(currentExpression);
                currentExpression = Double.toString(result);
                calculatorResult.onExpressionChanged(currentExpression,true);
            } catch (SyntaxException e) {
                calculatorResult.onExpressionChanged("Invalid input",false);
                e.printStackTrace();
            }
        }

    }


    /**
     * Helper function to validate expression against the following checks:
     * "" - invalid
     * 8765 - valid
     * @param expression
     * @return
     */
    public boolean validateExpression(String expression){
        if(
                expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith("%")
         ){
            calculatorResult.onExpressionChanged("Invalid input",false);
            return false;

        }else if (expression.equals("")){
            calculatorResult.onExpressionChanged("Invalid input",false);
            return false;
        }else if(expression.length() > 16){
            calculatorResult.onExpressionChanged("Invalid input",false);
            return false;
        }else {
            return true;
        }
    }


}
