package com.duraiv2.calculatormvp;

/**
 * Created by duraiv2 on 10/4/2018.
 */

public interface CalculatorContract {

    // Our View Handles these methods(DisplayFragment)
    interface PublishToView{

        void showResult(String result);

        void showToastMessage(String message);

    }

    // passes click events from our view (DisplayFragment) , to the presenter
    interface ForwardDisplayInteractionToPresenter{

        void onDeleteShortClick();
        void onDeleteLongClick();
    }

    // passes click events from our view (InputFragment) , to the presenter
    interface ForwardInputInteractionToPresenter{

        void onNumberClick(int number);
        void onDecimalClick();
        void onEvaluateClick();
        void onOperatorClick(String operator);
    }


}
