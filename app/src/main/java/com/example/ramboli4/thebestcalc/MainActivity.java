package com.example.ramboli4.thebestcalc;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    static public int STATE = 0;
    static public double first_num=0;
    static public char lastaritmatic;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button downSelected=findViewById(R.id.DEL);
        downSelected.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView calcScreen = findViewById(R.id.calcScreen);
                calcScreen.setText("");
                STATE=0;

                return true;
            }

        });

    }

    public void numPressed(View view) {
        TextView calcScreen = findViewById(R.id.calcScreen);

        switch (STATE){
            case 0:
                calcScreen.setText(calcScreen.getText().toString()+((Button)view).getText().toString());
                break;
            case 1:
                if(calcScreen.getText().length()>0)
                    first_num=Double.parseDouble(calcScreen.getText().toString());
                else
                    first_num=0;

                calcScreen.setText("");
                calcScreen.setText(calcScreen.getText().toString()+((Button)view).getText().toString());
                STATE=2;
                break;
            case 2:
                calcScreen.setText(calcScreen.getText().toString()+((Button)view).getText().toString());
                break;

        }
    }

    public void aritmeticClick(View view) {

        if(STATE==2){
            resultClick(view);
            STATE=1;
        }
        lastaritmatic=((Button)view).getText().charAt(0);

        STATE=1;




    }

    public void delPressed(View view) {
        TextView calcScreen = findViewById(R.id.calcScreen);

        if(calcScreen.getText().length()>0)
            calcScreen.setText(calcScreen.getText().toString().substring(0,calcScreen.getText().toString().length()-1));
    }

    public void dotPressed(View view) {
        TextView calcScreen = findViewById(R.id.calcScreen);
        if(calcScreen.getText().length()==0){
            calcScreen.setText("0"+((Button)view).getText().toString());
        }
        else{
            int i;
            for(i=0;i<calcScreen.getText().length();i++){
                if(calcScreen.getText().charAt(i)=='.')
                    break;
            }
            if(i==calcScreen.getText().length())
                calcScreen.setText(calcScreen.getText().toString()+((Button)view).getText().toString());
        }

    }

    public void resultClick(View view) {
        DecimalFormat df = new DecimalFormat("###.###############");

        TextView calcScreen = findViewById(R.id.calcScreen);
        switch (STATE){
            case 2:
                if(calcScreen.getText().length()>0)
                    calcScreen.setText(String.valueOf(df.format(calculate(first_num,Double.parseDouble(calcScreen.getText().toString()),lastaritmatic))));
                else
                    calcScreen.setText(String.valueOf(df.format(calculate(first_num,0,lastaritmatic))));
                STATE=0;
                break;
            case 3:

                break;
            case 0:

                break;

        }
    }

    public double calculate(double num1, double num2, char operator){
        switch (operator){
            case '+':
                return num1+num2;
            case '-':
                return num1-num2;
            case '/':
                if(num2==0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("Error");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Can't divide by zero!")
                            .setCancelable(false)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    dialog.cancel();
                                }

                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    return 0;
                }
                return num1/num2;
            case 'X':
                return num1*num2;
        }
        return 0;
    }

    public int test(){
        int i=2;
        return i+5;
    }

}
