package com.example.carnote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carnote.historylist.HistoryAdapter;
import com.example.carnote.model.AutoData;
import com.example.carnote.model.TankUpRecord;


import java.util.ArrayList;
import java.util.Date;


public class MainMenuActivity extends AppCompatActivity {

    public static final String SPECIAL_DATA = "SPECIAL_DATA";
    public static final int NEW_CAR_REQUEST_CODE = 123456;
    private static final int TANK_REQUEST_CODE = 6789 ;
    private Button goToTankFormButton;
    private Spinner autoChoseSpinner;
    private ArrayList<AutoData> autoList;
    private ArrayAdapter<AutoData> arrayAdapter;
    private Button goToNewCarButton;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        initVievs();
    }

    private void initVievs() {
        goToTankFormButton = (Button) findViewById(R.id.go_to_tank_form_button);
        goToNewCarButton = (Button) findViewById(R.id.new_car_button);
        autoChoseSpinner = (Spinner) findViewById(R.id.auto_chose_spinner);
        historyRecyclerView = (RecyclerView)findViewById(R.id.historyRecyclerView);


        initAutoList();

        initArrayAdapter();

        autoChoseSpinner.setAdapter(arrayAdapter);

        initRecyclerView();


        goToNewCarButton.setOnClickListener(goToNewCarActivity());



        goToTankFormButton.setOnClickListener(GoToTankUpActivity());
    }

    private void initRecyclerView() {
        historyLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);

        historyAdapter = new HistoryAdapter(this, getCurrentAuto().getTankUpRecord());
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private View.OnClickListener goToNewCarActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
                startActivityForResult(intent, NEW_CAR_REQUEST_CODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CAR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    AutoData newAutoData = (AutoData) data.getExtras().get(AddCarActivity.AUTO_DATA_NEW_CAR);
                    Boolean isNewCarMasterCar = (Boolean) data.getExtras().get(AddCarActivity.IS_NEW_CAR_MASETER_CAR);
                    if (isNewCarMasterCar!=null && isNewCarMasterCar)
                    {
                        autoList.add(0,newAutoData);
                    }
                    else
                    {
                        autoList.add(newAutoData);
                    }
                }
            }
        }
        if (requestCode == TANK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    TankUpRecord newTankUp = (TankUpRecord) data.getExtras().get(GasTankUpActivity.NOWE_TANKOWANIE_RECORD);

                    if (newTankUp != null)
                    {
                        getCurrentAuto().getTankUpRecord().add(0,newTankUp);
                        historyAdapter.notifyDataSetChanged();
                    }

                }
            }
        }
    }


    @NonNull
    private View.OnClickListener GoToTankUpActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, GasTankUpActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentAuto());
                startActivityForResult(intent, TANK_REQUEST_CODE);
            }
        };
    }

    private void initArrayAdapter() {
        arrayAdapter = new ArrayAdapter<AutoData>(this, android.R.layout.simple_spinner_item, autoList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initAutoList() {
        autoList = new ArrayList<AutoData>();
        autoList.add(new AutoData("Golf VI", "VolksWagen", "Czerwony"));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),1, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),3, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),5, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),7, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),8, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),23, 10,50));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(),77, 10,50));
        autoList.add(new AutoData("Passat B5", "VolksWagen", "Szary"));
    }

    @NonNull
    private AutoData getCurrentAuto() {

        return (AutoData) autoChoseSpinner.getSelectedItem();
    }
}
