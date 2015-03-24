package com.example.jack.gascosts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.text.NumberFormat;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        costTextView =
                (TextView) findViewById(R.id.costTextView);
        mpgView =
                (TextView) findViewById(R.id.mpgView);
        gasView =
                (TextView) findViewById(R.id.gasView);
        EditText distanceAmount =
                (EditText) findViewById(R.id.distanceEditText);
        distanceAmount.addTextChangedListener(distanceAmountWatcher);
        addPicClickListener();
        updateStandard();
        updateGas();
        updateMpg();

        SeekBar mpgAmount =
                (SeekBar) findViewById(R.id.mileSeekBar);
        mpgAmount.setOnSeekBarChangeListener(mpgAmountListener);
        SeekBar gasAmount =
                (SeekBar) findViewById(R.id.gasSeekBar);
        gasAmount.setOnSeekBarChangeListener(gasAmountListener);
    }

    private void addPicClickListener() {
        ImageView picNavigator = (ImageView) findViewById(R.id.imageView);
        picNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.wikipedia.org/wiki/Lexus_LS"));
                startActivity(intent);
            }
        });
    }

    private double updateCost() {
        if (mpgAmount > 0) {
            double gallons = distanceAmount/mpgAmount;
            double cost = gallons*gasAmount;
            return cost;
        } else {
            double cost = 0;
            return cost;
        }
    }

    private void updateStandard(){
        double cost = updateCost();
        costTextView.setText(currencyFormat.format(cost));
    }
    private void updateMpg() {
        mpgView.setText(integerFormat.format(mpgAmount));
    }
    private void updateGas() {
        gasView.setText(currencyFormat.format(gasAmount));
    }
    private OnSeekBarChangeListener mpgAmountListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mpgAmount = progress;
                    updateMpg();
                    updateStandard();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    private OnSeekBarChangeListener gasAmountListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    gasAmount = progress/100;
                    updateGas();
                    updateStandard();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    private TextWatcher distanceAmountWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try
            {
                distanceAmount = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                distanceAmount = 0;
            }

            updateStandard();
        }

        @Override
        public void  beforeTextChanged(CharSequence s, int start, int count, int after){

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat integerFormat =
            NumberFormat.getIntegerInstance();

    private double distanceAmount = 0.0;
    private double mpgAmount = 30;
    private double gasAmount = 2.6;
    private TextView costTextView;
    private TextView mpgView;
    private TextView gasView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
