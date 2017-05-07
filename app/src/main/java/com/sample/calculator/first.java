package com.sample.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.regex.Pattern;

public class first extends AppCompatActivity {
    private TextView Scr;
    private String dis="";
    private String currentOp="";
    private String res="";
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Scr=(TextView)findViewById(R.id.text);
        Scr.setText(dis);
    }
    private void updateScr(){
        Scr.setText(dis);
    }

    public void onClick(View v){
        if(res!=""){
            clear();
            updateScr();
        }
        Button b=(Button) v;
        dis+=b.getText();
        updateScr();
    }

    private boolean isOperator(char op){
        switch(op){
            case '+':
            case '-':
            case '*':
            case '/':return true;
            default: return false;
        }
    }

    public void onClickSigns(View v){
        if(dis=="") return;

        Button b=(Button)v;

        if(res!=""){
            String _dis=res;
            clear();
            dis=_dis;
        }

        if(currentOp!=""){
            Log.d("CalcX",""+dis.charAt(dis.length()-1));
            if(isOperator(dis.charAt(dis.length()-1))){
                dis=dis.replace(dis.charAt(dis.length()-1),b.getText().charAt(0));
                updateScr();
                return;
            }else{
                getres();
                dis=res;
                res="";
            }
            currentOp=b.getText().toString();
        }
        dis+=b.getText();
        currentOp=b.getText().toString();
        updateScr();
    }

    private void clear(){
        dis="";
        currentOp="";
        res="";
    }
    public void onBackPressed() {
        ++k;
        if(k==1){
            Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_LONG).show();
            clear();
            updateScr();
        }else if(k==2){
            finish();
        }
    }
    public void onResume(){
        super.onResume();
        k=0;
    }
    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a)+Double.valueOf(b);
            case "-": return Double.valueOf(a)-Double.valueOf(b);
            case "*": return Double.valueOf(a)*Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a)/Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc",e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getres(){
        if(currentOp=="") return false;
        String[] operation=dis.split(Pattern.quote(currentOp));
        if(operation.length<2) return false;
        res=String.valueOf(operate(operation[0], operation[1], currentOp));
        return true;
    }

    public void calculate(View v){
        if(dis=="") return;
        if(!getres()) return;
        Scr.setText(dis+"\n"+String.valueOf(res));
    }

}

