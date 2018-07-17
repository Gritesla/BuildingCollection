package com.hdsoft.buildingcollection.activity;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.hdsoft.buildingcollection.R;
import com.hdsoft.buildingcollection.adapter.FormPageAdapter;
import com.hdsoft.buildingcollection.common.StateCode;
import com.hdsoft.buildingcollection.entity.BuildingEntity;
import com.hdsoft.buildingcollection.utils.ExternalStorageUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    private ImageButton ibtnTag1;
    private ImageButton ibtnTag2;
    private ImageButton ibtnTag3;
    private ImageButton ibtnTag4;
    private View vwUnit;
    private View vwBuilding;
    private View vwAround;
    private View vwMedia;

    private Button btnMediaSave;
    private Button btnMediaCancel;
    private Button btnMediaPic;
    private Button btnMediaVid;

    private EditText edtUnitName;//单位名称
    private EditText edtUnitNum;//单位电话
    private EditText edtUnitAddr;//单位地址
    private EditText edtUnitPos;//单位坐标
    private EditText edtUnitLia;//责任人名称
    private EditText edtUnitLiaNum;//责任人电话
    private EditText edtUnitJur;//所属辖区
    private EditText edtUnitJurLia;//辖区责任人
    private EditText edtUnitJurNum;//辖区责任人电话

    private EditText edtBuildingNature;//建筑使用性质
    private EditText edtBuildingStr;//建筑结构
    private EditText edtBuildingLandArea;//占地面积
    private EditText edtBuildingArea;//建筑面积
    private EditText edtBuildingUpHeight;//地上高度
    private EditText edtBuildingOverHeight;//地下高度
    private EditText edtBuildingUpLayer;//地上层数
    private EditText edtBuildingOverLayer;//地下层数
    private EditText edtBuildingRefuge;//避难层
    private EditText edtBuildingFun;//功能分区说明
    private EditText edtBuildingImp;//重点部位说明
    private EditText edtBuildingWay;//周边消防通道

    private EditText edtAroundEast;//东面建筑
    private EditText edtAroundNorth;//北面建筑
    private EditText edtAroundSouth;//南面建筑
    private EditText edtAroundWest;//西面建筑


    private static File tempPictureFile;
    private static File tempVideoFile;
    private Uri imageUri;
    private Uri videoUri;

    private ImageView ivMediaPic;
    private ImageView ivMediaVid;
    private String videoFilePath;
    private String photoFilePath;
    private String flag;
    private int dataId;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case StateCode.PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, StateCode.CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case StateCode.CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath);
                    ivMediaPic.setImageBitmap(bitmap);
                }
                break;
            case StateCode.VIDEO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(videoFilePath);
                    Bitmap bitmap = retriever.getFrameAtTime(100);
                    ivMediaVid.setImageBitmap(bitmap);
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
                break;
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_form);
        ViewPager vpForm = findViewById(R.id.vpForm);

        flag = getIntent().getStringExtra("req");

        LayoutInflater inflater = getLayoutInflater();
        vwUnit = inflater.inflate(R.layout.activity_form_unit, null);
        vwBuilding = inflater.inflate(R.layout.activity_form_building, null);
        vwAround = inflater.inflate(R.layout.activity_form_around, null);
        vwMedia = inflater.inflate(R.layout.activity_form_media, null);

        //region 获取Unit页面的输入框控件
        edtUnitName = vwUnit.findViewById(R.id.edtUnitName);
        edtUnitNum = vwUnit.findViewById(R.id.edtUnitNum);
        edtUnitAddr = vwUnit.findViewById(R.id.edtUnitAddr);
        edtUnitPos = vwUnit.findViewById(R.id.edtUnitPos);
        edtUnitLia = vwUnit.findViewById(R.id.edtUnitLia);
        edtUnitLiaNum = vwUnit.findViewById(R.id.edtUnitLiaNum);
        edtUnitJur = vwUnit.findViewById(R.id.edtUnitJur);
        edtUnitJurLia = vwUnit.findViewById(R.id.edtUnitJurLia);
        edtUnitJurNum = vwUnit.findViewById(R.id.edtUnitJurNum);
        //endregion

        //region 获取Building页面的输入框控件
        edtBuildingNature = vwBuilding.findViewById(R.id.edtBuildingNature);
        edtBuildingStr = vwBuilding.findViewById(R.id.edtBuildingStr);
        edtBuildingLandArea = vwBuilding.findViewById(R.id.edtBuildingLandArea);
        edtBuildingArea = vwBuilding.findViewById(R.id.edtBuildingArea);
        edtBuildingUpHeight = vwBuilding.findViewById(R.id.edtBuildingUpHeight);
        edtBuildingOverHeight = vwBuilding.findViewById(R.id.edtBuildingOverHeight);
        edtBuildingUpLayer = vwBuilding.findViewById(R.id.edtBuildingUpLayer);
        edtBuildingOverLayer = vwBuilding.findViewById(R.id.edtBuildingOverLayer);
        edtBuildingRefuge = vwBuilding.findViewById(R.id.edtBuildingRefuge);
        edtBuildingFun = vwBuilding.findViewById(R.id.edtBuildingFun);
        edtBuildingImp = vwBuilding.findViewById(R.id.edtBuildingImp);
        edtBuildingWay = vwBuilding.findViewById(R.id.edtBuildingWay);
        //endregion

        //region 获取Around页面的输入框控件
        edtAroundEast = vwAround.findViewById(R.id.edtAroundEast);
        edtAroundNorth = vwAround.findViewById(R.id.edtAroundNorth);
        edtAroundSouth = vwAround.findViewById(R.id.edtAroundSouth);
        edtAroundWest = vwAround.findViewById(R.id.edtAroundWest);
        //endregion

        //region获取Media页面的控件
        ivMediaPic = vwMedia.findViewById(R.id.ivMediaPic);
        ivMediaVid = vwMedia.findViewById(R.id.ivMediaVid);

        FormPageClick formPageClick = new FormPageClick();
        btnMediaPic = vwMedia.findViewById(R.id.btnMediaPic);
        btnMediaVid = vwMedia.findViewById(R.id.btnMediaVid);
        btnMediaSave = vwMedia.findViewById(R.id.btnMediaSave);
        btnMediaCancel = vwMedia.findViewById(R.id.btnMediaCancel);
        btnMediaPic.setOnClickListener(formPageClick);
        btnMediaVid.setOnClickListener(formPageClick);
        btnMediaSave.setOnClickListener(formPageClick);
        btnMediaCancel.setOnClickListener(formPageClick);


        ibtnTag1 = findViewById(R.id.ibtnTag1);
        ibtnTag2 = findViewById(R.id.ibtnTag2);
        ibtnTag3 = findViewById(R.id.ibtnTag3);
        ibtnTag4 = findViewById(R.id.ibtnTag4);

        ArrayList<View> viewList = new ArrayList<View>();
        viewList.add(vwUnit);
        viewList.add(vwBuilding);
        viewList.add(vwAround);
        viewList.add(vwMedia);

        FormPageAdapter formPageAdapter = new FormPageAdapter(viewList);
        vpForm.setAdapter(formPageAdapter);

        vpForm.addOnPageChangeListener(new FormPageChangeListener());
        //endregion

        if (flag.equals("edit")) {
            dataId = Integer.parseInt(getIntent().getStringExtra("id"));
            BuildingEntity entity = DataSupport.find(BuildingEntity.class, dataId);
            edtUnitName.setText(entity.getUnitName());
            edtUnitNum.setText(entity.getUnitNum());
            edtUnitAddr.setText(entity.getUnitAddr());
            edtUnitPos.setText(entity.getUnitPos());
            edtUnitLia.setText(entity.getUnitLia());
            edtUnitLiaNum.setText(entity.getUnitLiaNum());
            edtUnitJur.setText(entity.getUnitJur());
            edtUnitJurLia.setText(entity.getUnitJurLia());
            edtUnitJurNum.setText(entity.getUnitJurNum());
            edtBuildingNature.setText(entity.getBuildingNature());
            edtBuildingStr.setText(entity.getBuildingStr());
            edtBuildingLandArea.setText(entity.getBuildingLandArea());
            edtBuildingArea.setText(entity.getBuildingArea());
            edtBuildingUpHeight.setText(entity.getBuildingUpHeight());
            edtBuildingOverHeight.setText(entity.getBuildingOverHeight());
            edtBuildingUpLayer.setText(entity.getBuildingUpLayer());
            edtBuildingOverLayer.setText(entity.getBuildingOverLayer());
            edtBuildingRefuge.setText(entity.getBuildingRefuge());
            edtBuildingFun.setText(entity.getBuildingFun());
            edtBuildingImp.setText(entity.getBuildingImp());
            edtBuildingWay.setText(entity.getBuildingWay());
            edtAroundEast.setText(entity.getAroundEast());
            edtAroundNorth.setText(entity.getAroundNorth());
            edtAroundSouth.setText(entity.getAroundSouth());
            edtAroundWest.setText(entity.getAroundWest());


            if (entity.getMeidaPic() != null) {
                Bitmap bmPic = BitmapFactory.decodeFile(entity.getMeidaPic());
                ivMediaPic.setImageBitmap(bmPic);
            }


            if (entity.getMediaVid() != null) {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(entity.getMediaVid());
                Bitmap bmVid = retriever.getFrameAtTime(100);
                ivMediaVid.setImageBitmap(bmVid);
            }
        }
    }

    private class FormPageClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMediaSave:
                    saveFormToDatabase();
                    break;
                case R.id.btnMediaCancel:
                    removePhoroAndVideo();
                    finish();
                    break;
                case R.id.btnMediaPic:
                    openCameraForPhoto(FormActivity.this);
                    break;
                case R.id.btnMediaVid:
                    openCameraForVideo(FormActivity.this);
                    break;


            }
        }
    }

    private void saveFormToDatabase() {
        boolean result ;
        BuildingEntity entity = new BuildingEntity();
        entity.setUnitName(edtUnitName.getText().toString());//单位名称
        entity.setUnitNum(edtUnitNum.getText().toString());//单位电话
        entity.setUnitAddr(edtUnitAddr.getText().toString());//单位地址
        entity.setUnitPos(edtUnitPos.getText().toString());//单位坐标
        entity.setUnitLia(edtUnitLia.getText().toString());//责任人名称
        entity.setUnitLiaNum(edtUnitLiaNum.getText().toString());//责任人电话
        entity.setUnitJur(edtUnitJur.getText().toString());//所属辖区
        entity.setUnitJurLia(edtUnitJurLia.getText().toString());//辖区责任人
        entity.setUnitJurNum(edtUnitJurNum.getText().toString());//辖区责任人电话

        entity.setBuildingNature(edtBuildingNature.getText().toString());//建筑使用性质
        entity.setBuildingStr(edtBuildingStr.getText().toString());//建筑结构
        entity.setBuildingLandArea(edtBuildingLandArea.getText().toString());//占地面积
        entity.setBuildingArea(edtBuildingArea.getText().toString());//建筑面积
        entity.setBuildingUpHeight(edtBuildingUpHeight.getText().toString());//地上高度
        entity.setBuildingOverHeight(edtBuildingOverHeight.getText().toString());//地下高度
        entity.setBuildingUpLayer(edtBuildingUpLayer.getText().toString());//地上层数
        entity.setBuildingOverLayer(edtBuildingOverLayer.getText().toString());//地下层数
        entity.setBuildingRefuge(edtBuildingRefuge.getText().toString());//避难层
        entity.setBuildingFun(edtBuildingFun.getText().toString());//功能分区说明
        entity.setBuildingImp(edtBuildingImp.getText().toString());//重点部位说明
        entity.setBuildingWay(edtBuildingWay.getText().toString());//周边消防通道

        entity.setAroundEast(edtAroundEast.getText().toString());//东面建筑
        entity.setAroundNorth(edtAroundNorth.getText().toString());//北面建筑
        entity.setAroundSouth(edtAroundSouth.getText().toString());//南面建筑
        entity.setAroundWest(edtAroundWest.getText().toString());//西面建筑

        if (photoFilePath != null)
            entity.setMeidaPic(photoFilePath);
        if (videoFilePath != null)
            entity.setMediaVid(videoFilePath);

        if (flag.equals("edit")) {
            result = entity.updateAll()>0;
        } else {
            result = entity.save();
        }
        if (result) {
            Toast.makeText(getApplicationContext(), "表单保存成功！", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(getApplicationContext(), "表单保存出错！", Toast.LENGTH_SHORT).show();
    }

    private void removePhoroAndVideo() {
        if (photoFilePath != null) {
            ExternalStorageUtils.deleteFileByPath(photoFilePath);
        }
        if (videoFilePath != null) {
            ExternalStorageUtils.deleteFileByPath(videoFilePath);
        }
    }

    private void openCameraForPhoto(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (ExternalStorageUtils.isSDCardMounted()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyyMMddHHmmss");
            String filename = timeStampFormat.format(new Date());
            File sdFile = Environment.getExternalStorageDirectory();
            String dirPath = sdFile + "/bdg/pic";
            ExternalStorageUtils.dirIsExistOrCreateByPath(dirPath);
            photoFilePath = dirPath + "/" + filename + ".png";
            tempPictureFile = new File(photoFilePath);
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempPictureFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempPictureFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    openCameraForPhoto(activity);
                    return;
                    // Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    // return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }

            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            activity.startActivityForResult(intent, StateCode.PHOTO_REQUEST_CAREMA);

        }


    }

    /**
     * @param activity
     */
    private void openCameraForVideo(Activity activity) {

        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (ExternalStorageUtils.isSDCardMounted()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyyMMddHHmmss");
            String filename = timeStampFormat.format(new Date());
            String dirPath = Environment.getExternalStorageDirectory() + "/bdg/vid";
            ExternalStorageUtils.dirIsExistOrCreateByPath(dirPath);
            videoFilePath = dirPath + "/" + filename + ".mp4";
            tempVideoFile = new File(videoFilePath);
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                videoUri = Uri.fromFile(tempVideoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);

                contentValues.put(MediaStore.Video.Media.DATA, tempVideoFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(FormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    openCameraForVideo(activity);
                    return;
                    // Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    // return;
                }
                videoUri = activity.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, StateCode.VIDEO_REQUEST_CAREMA);

    }

    private class FormPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    ibtnTag1.setImageResource(R.drawable.dot_32px_g);
                    ibtnTag2.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag3.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag4.setImageResource(R.drawable.dot_32px_b);
                    break;
                case 1:
                    ibtnTag1.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag2.setImageResource(R.drawable.dot_32px_g);
                    ibtnTag3.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag4.setImageResource(R.drawable.dot_32px_b);
                    break;
                case 2:
                    ibtnTag1.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag2.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag3.setImageResource(R.drawable.dot_32px_g);
                    ibtnTag4.setImageResource(R.drawable.dot_32px_b);
                    break;
                case 3:
                    ibtnTag1.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag2.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag3.setImageResource(R.drawable.dot_32px_b);
                    ibtnTag4.setImageResource(R.drawable.dot_32px_g);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            //Log.d(TAG, "onPageScrollStateChanged: ");
        }
    }
}
