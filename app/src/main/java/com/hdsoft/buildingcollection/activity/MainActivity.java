package com.hdsoft.buildingcollection.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.hdsoft.buildingcollection.R;

import org.litepal.tablemanager.Connector;

import java.text.Normalizer;

public class MainActivity extends AppCompatActivity {

    private Button btnCollection;
    private Button btnEdit;
    private Button btnUpload;
    private Button btnDelete;
    private Button btnMap;
    private Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connector.getDatabase();

        btnCollection = findViewById(R.id.btnCollection);
        btnEdit = findViewById(R.id.btnEdit);
        btnUpload = findViewById(R.id.btnUpload);
        btnDelete = findViewById(R.id.btnDelete);
        btnMap = findViewById(R.id.btnMap);
        btnSetting = findViewById(R.id.btnSetting);

        MainActivityOnClickListener listener = new MainActivityOnClickListener();
        btnCollection.setOnClickListener(listener);
        btnEdit.setOnClickListener(listener);
        btnUpload.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);
        btnMap.setOnClickListener(listener);
        btnSetting.setOnClickListener(listener);

    }

    private class MainActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnCollection:
                    Intent formIntent = new Intent(getApplicationContext(), FormActivity.class);
                    formIntent.putExtra("req","col");
                    //formIntent.setClass(getApplicationContext(), FormActivity.class);
                    startActivity(formIntent);
                    break;
                case R.id.btnEdit:
                    Intent editIntent = new Intent(getApplicationContext(),FormListActivity.class);
                    editIntent.putExtra("req","edit");
                    startActivity(editIntent);
                    break;
                case R.id.btnUpload:
                    Intent uploadIntent = new Intent(getApplicationContext(),FormListActivity.class);
                    uploadIntent.putExtra("req","upload");
                    startActivity(uploadIntent);
                    break;
                case R.id.btnDelete:
                    Intent deleteIntent = new Intent(getApplicationContext(),FormListActivity.class);
                    deleteIntent.putExtra("req","delete");
                    startActivity(deleteIntent);
                    break;
                case R.id.btnMap:
                    break;
                case R.id.btnSetting:
                    break;
            }
        }
    }
}
