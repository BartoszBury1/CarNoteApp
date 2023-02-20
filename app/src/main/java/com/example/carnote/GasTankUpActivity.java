package com.example.carnote;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnote.model.AutoData;
import com.example.carnote.model.TankUpRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GasTankUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    // TODO Zostawiamy tytul w stalej
    public static final String NOWE_TANKOWANIE = "Nowe tankowanie";
    public static final String NOWE_TANKOWANIE_RECORD = "NEW_TANKUP_RECORD";
    private EditText dateEditText;
    private EditText mileageEditText;
    private EditText costEditText;
    private EditText litersEditText;
    private TextView litersEditTextLabel;
    private TextView costEditTextLabel;

    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_tank_up_layout);
        obtainExtras();
        viewInit();
        setTitle(NOWE_TANKOWANIE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void obtainExtras() {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MainMenuActivity.SPECIAL_DATA);
    }

    private void viewInit() {
        dateEditText = (EditText) findViewById(R.id.date);
        mileageEditText = (EditText) findViewById(R.id.przebieg);
        mileageEditTextLabel = (TextView) findViewById(R.id.milage_label);
        litersEditText = (EditText) findViewById(R.id.liters);
        litersEditTextLabel = (TextView) findViewById(R.id.liters_label);
        costEditText = (EditText) findViewById(R.id.cost);
        costEditTextLabel = (TextView) findViewById(R.id.cost_label);
        confirmButton = (Button) findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(GasTankUpActivity.this,GasTankUpActivity.this,year, month, day);
                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateMileage() && validateCost() && validateLiters()) {
                    TankUpRecord tank = new TankUpRecord(getDateEditTextDate(), getMilageInteger(), getLitersInteger(), getCostInteger());
                    Intent intent = new Intent();
                    intent.putExtra(NOWE_TANKOWANIE_RECORD, tank);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }


        });

        mileageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                //Wyjscie z kontrolki
                if (hasFocus == false) {

                    validateMileage();
                }
            }
        });
        costEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus == false) {

                    validateCost();
                }
            }
        });

        litersEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus == false) {

                    validateLiters();
                }
            }
        });
    }

    private boolean validateLiters() {
        if(TextUtils.isEmpty(litersEditText.getText().toString())){
            litersEditTextLabel.setText("Litry musza byc podane");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(litersEditText.getText().toString())<=0){
            litersEditTextLabel.setText("litry musza byc dodatnie");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }

        else {
            litersEditTextLabel.setText(getResources().getString(R.string.tanked_liters));
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
    }

    private boolean validateCost() {
        if(TextUtils.isEmpty(costEditText.getText().toString())){
            costEditTextLabel.setText("Koszt musi byc podany");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(costEditText.getText().toString())<=0){
            costEditTextLabel.setText("Kosztu musi byc dodatni");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }

        else {
            costEditTextLabel.setText(getResources().getString(R.string.tank_cost));
            costEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
        }


    }

    private boolean validateMileage() {
        if(TextUtils.isEmpty(mileageEditText.getText().toString())){
            mileageEditTextLabel.setText("Przebieg musi zostac podany");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        if(Integer.valueOf(mileageEditText.getText().toString())<=0){
            mileageEditTextLabel.setText("Przebieg musi byc dodatni");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        int size = autoData.getTankUpRecord().size();
        if (autoData.getTankUpRecord().size() != 0) {
            Integer newMilage = Integer.valueOf(mileageEditText.getText().toString());
            Integer oldMilage = autoData.getTankUpRecord().get(size - 1).getMilage();
            if (newMilage <= oldMilage) {
                mileageEditTextLabel.setText("Przebieg jest mniejszy lub rowny ostatniemu");
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
                return false;

            } else {
                mileageEditTextLabel.setText(getResources().getString(R.string.milage));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black));
                return true;
            }
        }
        return true;
    }

    private Date getDateEditTextDate() {
        try {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return date;

    }

    private Integer getMilageInteger() {
        return Integer.valueOf(mileageEditText.getText().toString());

    }

    private Integer getLitersInteger() {
        return Integer.valueOf(litersEditText.getText().toString());

    }
    private Integer getCostInteger() {
        return Integer.valueOf(costEditText.getText().toString());

    }


    private String getCurrentDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);

        dateEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
