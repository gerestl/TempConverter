package com.example.geres.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.view.View;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener, OnClickListener {


    //DEFINE THE VARIABLES FOR THE WIDGET
    private EditText fahrenheitET;
    private TextView celsiusTV;


    //DEFINE INSTANCE VARIABLES......................
    private String fahrenheitString = "";


    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET REFERENCE TO THE WIDGETS..................
        fahrenheitET = (EditText) findViewById(R.id.fahrenheitET);
        celsiusTV = (TextView) findViewById(R.id.celciusTV);

        //SET LISTENER FOR THE EVENT
        fahrenheitET.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues",MODE_PRIVATE);



    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onEditorAction(TextView textView,
                                  int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }
    private void calculateAndDisplay(){

        //GET FAHRENHEIT DEGREES FROM USER................
        fahrenheitString = fahrenheitET.getText().toString();
        float fDegrees;
        if(fahrenheitString.equals("")){
            fDegrees = 0;
        }
        else{
            fDegrees = Float.parseFloat(fahrenheitString);
        }

        float celciusAmount = ((fDegrees - 32)* 5/9);

        celsiusTV.setText(String.valueOf(celciusAmount));

    }
    @Override
    protected void onPause(){
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        fahrenheitString = savedValues.getString("fahrenheitString","");
       //fahrenheitET.setText(fahrenheitString);
        calculateAndDisplay();
        super.onResume();
    }
}
