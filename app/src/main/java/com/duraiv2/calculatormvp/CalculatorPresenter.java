package com.duraiv2.calculatormvp;

/**
 * Created by duraiv2 on 10/4/2018.
 */

public class CalculatorPresenter implements CalculatorContract.ForwardInputInteractionToPresenter,CalculatorContract.ForwardDisplayInteractionToPresenter, Calculation.CalculatorResult {

    private  CalculatorContract.PublishToView publishToView;
    private Calculation calculation;

//    an object of display fragment
    public CalculatorPresenter(CalculatorContract.PublishToView publishToView){
        this.publishToView = publishToView;
        calculation = new Calculation();
        calculation.setCalculatorResultListener(this);
    }

    @Override
    public void onDeleteShortClick() {
        calculation.deleteCharacter();
    }

    @Override
    public void onDeleteLongClick() {
        calculation.deleteExpression();
    }

    @Override
    public void onNumberClick(int number) {
        calculation.appendNumber(Integer.toString(number));
    }

    @Override
    public void onDecimalClick() {
        calculation.appendDecimal();
    }

    @Override
    public void onEvaluateClick() {
        calculation.performEvaluation();
    }

    @Override
    public void onOperatorClick(String operator) {
        calculation.appendOperator(operator);
    }

    @Override
    public void onExpressionChanged(String result, boolean successful) {
        if (successful){
            publishToView.showResult(result);
        }else {
            publishToView.showToastMessage(result);
        }
    }
}
