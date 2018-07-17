package com.hdsoft.buildingcollection.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hdsoft.buildingcollection.R;
import com.hdsoft.buildingcollection.adapter.FormListAdapter;
import com.hdsoft.buildingcollection.common.CustomCode;
import com.hdsoft.buildingcollection.entity.BuildingEntity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class FormListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String flag;
    private LinearLayout llButtonGroup;
    private ListView lvFormList;

    private View preView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formlist);
        flag = getIntent().getStringExtra("req");
        lvFormList = findViewById(R.id.lvFormList);
        llButtonGroup = findViewById(R.id.llButtonGroup);

        final List<BuildingEntity> list = DataSupport.findAll(BuildingEntity.class);
        lvFormList.setAdapter(new FormListAdapter(list, FormListActivity.this, lvFormList, flag));
        switch (flag) {
            case "edit":
                Button btnEdit = new Button(this);
                btnEdit.setText("编辑");
                btnEdit.setTextSize(20);
                btnEdit.setId(CustomCode.BTN_CUSTOM_EDIT);
                btnEdit.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                LinearLayout.LayoutParams lpEdit = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lpEdit.weight = 1;
                lpEdit.setMargins(5, 5, 5, 5);
                btnEdit.setLayoutParams(lpEdit);
                llButtonGroup.addView(btnEdit);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Log.d("id", "onClick: " + v.getId());

                        Intent editIntent = new Intent(getApplicationContext(), FormActivity.class);
                        editIntent.putExtra("req", "edit");
                        SparseBooleanArray sparseBooleanArray = lvFormList.getCheckedItemPositions();
                        for (int i = 0; i < sparseBooleanArray.size(); i++) {
                            int key = sparseBooleanArray.keyAt(i);
                            boolean val = sparseBooleanArray.get(key);
                            if (val) {
                                //当前key就是ListView中的Position
                                editIntent.putExtra("id", list.get(key).getId() + "");
                            }
                        }
                        startActivity(editIntent);
                    }
                });
                lvFormList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                break;
            case "upload":
                Button btnUpload = new Button(this);
                btnUpload.setText("上传");
                btnUpload.setTextSize(20);
                btnUpload.setId(CustomCode.BTN_CUSTOM_UPLOAD);
                btnUpload.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                LinearLayout.LayoutParams lpUpload = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lpUpload.weight = 1;
                lpUpload.setMargins(5, 5, 5, 5);
                btnUpload.setLayoutParams(lpUpload);
                llButtonGroup.addView(btnUpload);
                btnUpload.setOnClickListener(new View.OnClickListener() {
                    private int uploadIndex = 1;

                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(FormListActivity.this)
                                .setTitle("上传提示")
                                .setMessage("确定上传所选 " + lvFormList.getCheckedItemCount() + " 项么？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SparseBooleanArray sparseBooleanArray = lvFormList.getCheckedItemPositions();
                                        for (int i = 0; i < sparseBooleanArray.size(); i++) {
                                            int key = sparseBooleanArray.keyAt(i);
                                            boolean val = sparseBooleanArray.get(key);
                                            if (val) {
                                                //当前key就是ListView中的Position
                                                Toast.makeText(getApplicationContext(), "正在上传第" + uploadIndex + "个", Toast.LENGTH_SHORT).show();
                                                uploadIndex++;
                                            }
                                        }
                                        Toast.makeText(getApplicationContext(), "上传完成！", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();
                    }
                });
                lvFormList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                break;
            case "delete":
                Button btnDelete = new Button(this);
                btnDelete.setText("删除");
                btnDelete.setTextSize(20);
                btnDelete.setId(CustomCode.BTN_CUSTOM_DELETE);
                btnDelete.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                LinearLayout.LayoutParams lpDelete = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lpDelete.weight = 1;
                lpDelete.setMargins(5, 5, 5, 5);
                btnDelete.setLayoutParams(lpDelete);
                llButtonGroup.addView(btnDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(FormListActivity.this)
                                .setTitle("删除提示")
                                .setMessage("确定删除所选 " + lvFormList.getCheckedItemCount() + " 项么？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SparseBooleanArray sparseBooleanArray = lvFormList.getCheckedItemPositions();
                                        for (int i = 0; i < sparseBooleanArray.size(); i++) {
                                            int key = sparseBooleanArray.keyAt(i);
                                            boolean val = sparseBooleanArray.get(key);
                                            if (val) {
                                                //当前key就是ListView中的Position
                                                DataSupport.delete(BuildingEntity.class, list.get(key).getId());
                                            }
                                        }
                                        finish();
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();
                    }
                });
                lvFormList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                break;
        }
        Button btnBack = new Button(this);
        btnBack.setText("返回");
        btnBack.setTextSize(20);
        btnBack.setId(CustomCode.BTN_CUSTOM_DELETE);
        btnBack.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        LinearLayout.LayoutParams lpBack = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBack.weight = 1;
        lpBack.setMargins(5, 5, 5, 5);
        btnBack.setLayoutParams(lpBack);
        llButtonGroup.addView(btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvFormList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (flag.equals("edit")) {
            lvFormList.clearChoices();
            if (preView != null)
                preView.setBackgroundColor(getResources().getColor(R.color.normal));
            preView = view;
            lvFormList.setItemChecked(position, true);
        }
        boolean itemChecked = lvFormList.isItemChecked(position);
        if (itemChecked)
            view.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        else
            view.setBackgroundColor(getResources().getColor(R.color.normal));
    }
}
