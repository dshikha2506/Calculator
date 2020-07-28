package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Path;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText NewNumber;
    private TextView Operation;

    private Double operand1= null;
    private Double operand2= null;
    private String pendingOperation= "=";

    private static final String STATE_PENDING_OPERATION= "PendingOperation";
    private static final String STATE_OPERAND1="Operand1";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=(EditText) findViewById(R.id.result);
        NewNumber=(EditText ) findViewById(R.id.NewNumber);
        Operation = (TextView) findViewById(R.id.Operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);


        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        Button buttonSub = (Button) findViewById(R.id.buttonSub);
        Button buttonMul = (Button) findViewById(R.id.buttonMul);
        Button buttonDiv = (Button) findViewById(R.id.buttonDiv);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);


        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b =(Button) view;
                NewNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b= (Button) view;
                String op= b.getText().toString();
                String value = NewNumber.getText().toString();
                try{
                    Double doubleValue= Double.valueOf(value);
                    performOperation(doubleValue,op);
                } catch (NumberFormatException e)
                {
                    NewNumber.setText("");
                }

                pendingOperation = op;
                Operation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListner);
        buttonAdd.setOnClickListener(opListner);
        buttonSub.setOnClickListener(opListner);
        buttonMul.setOnClickListener(opListner);
        buttonDiv.setOnClickListener(opListner);

        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);

        View.OnClickListener neglistener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = NewNumber.getText().toString();
                if (value.length() == 0) {
                    NewNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        NewNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        // newNumber was "-" or ".", so clear it
                        NewNumber.setText("");
                    }
                }
            }
        };
        buttonNeg.setOnClickListener(neglistener);

      Button buttonClear = (Button) findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewNumber.setText("");
                result.setText("");
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1!=null)
        {
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1=savedInstanceState.getDouble(STATE_OPERAND1);
        Operation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation)
    {
        if(null== operand1){
            operand1= value;

        }
        else{
            if (pendingOperation.equals("=")){
                pendingOperation=operation;
            }
            switch (pendingOperation)
            {
                case "=":
                    operand1=value;
                    break;
                case "/":
                    if(value==0){
                        operand1=0.0;

                    }else {
                        operand1/=value;
                    }
                    break;
                case "*":
                    operand1*=value;
                    break;
                case "-":
                    operand1-=value;
                    break;
                case "+":
                    operand1+=value;
                    break;
            }
        }
        result.setText(operand1.toString());
        NewNumber.setText("");
    }
}
