package com.hdsoft.buildingcollection.entity;

import org.litepal.crud.DataSupport;

public class BuildingEntity extends DataSupport {


    private long Id;//唯一主键
    private String UnitName;//单位名称
    private String UnitNum;//单位电话
    private String UnitAddr;//单位地址
    private String UnitPos;//单位坐标
    private String UnitLia;//责任人名称
    private String UnitLiaNum;//责任人电话
    private String UnitJur;//所属辖区
    private String UnitJurLia;//辖区责任人
    private String UnitJurNum;//辖区责任人电话

    private String BuildingNature;//建筑使用性质
    private String BuildingStr;//建筑结构
    private String BuildingLandArea;//占地面积
    private String BuildingArea;//建筑面积
    private String BuildingUpHeight;//地上高度
    private String BuildingOverHeight;//地下高度
    private String BuildingUpLayer;//地上层数
    private String BuildingOverLayer;//地下层数
    private String BuildingRefuge;//避难层
    private String BuildingFun;//功能分区说明
    private String BuildingImp;//重点部位说明
    private String BuildingWay;//周边消防通道

    private String AroundEast;//东面建筑
    private String AroundNorth;//北面建筑
    private String AroundSouth;//南面建筑
    private String AroundWest;//西面建筑

    private String MeidaPic;//图片地址
    private String MediaVid;//视频地址

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        id = System.currentTimeMillis();
//    }


    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getUnitNum() {
        return UnitNum;
    }

    public void setUnitNum(String unitNum) {
        UnitNum = unitNum;
    }

    public String getUnitAddr() {
        return UnitAddr;
    }

    public void setUnitAddr(String unitAddr) {
        UnitAddr = unitAddr;
    }

    public String getUnitPos() {
        return UnitPos;
    }

    public void setUnitPos(String unitPos) {
        UnitPos = unitPos;
    }

    public String getUnitLia() {
        return UnitLia;
    }

    public void setUnitLia(String unitLia) {
        UnitLia = unitLia;
    }

    public String getUnitLiaNum() {
        return UnitLiaNum;
    }

    public void setUnitLiaNum(String unitLiaNum) {
        UnitLiaNum = unitLiaNum;
    }

    public String getUnitJur() {
        return UnitJur;
    }

    public void setUnitJur(String unitJur) {
        UnitJur = unitJur;
    }

    public String getUnitJurLia() {
        return UnitJurLia;
    }

    public void setUnitJurLia(String unitJurLia) {
        UnitJurLia = unitJurLia;
    }

    public String getUnitJurNum() {
        return UnitJurNum;
    }

    public void setUnitJurNum(String unitJurNum) {
        UnitJurNum = unitJurNum;
    }

    public String getBuildingNature() {
        return BuildingNature;
    }

    public void setBuildingNature(String buildingNature) {
        BuildingNature = buildingNature;
    }

    public String getBuildingStr() {
        return BuildingStr;
    }

    public void setBuildingStr(String buildingStr) {
        BuildingStr = buildingStr;
    }

    public String getBuildingLandArea() {
        return BuildingLandArea;
    }

    public void setBuildingLandArea(String buildingLandArea) {
        BuildingLandArea = buildingLandArea;
    }

    public String getBuildingArea() {
        return BuildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        BuildingArea = buildingArea;
    }

    public String getBuildingUpHeight() {
        return BuildingUpHeight;
    }

    public void setBuildingUpHeight(String buildingUpHeight) {
        BuildingUpHeight = buildingUpHeight;
    }

    public String getBuildingOverHeight() {
        return BuildingOverHeight;
    }

    public void setBuildingOverHeight(String buildingOverHeight) {
        BuildingOverHeight = buildingOverHeight;
    }

    public String getBuildingUpLayer() {
        return BuildingUpLayer;
    }

    public void setBuildingUpLayer(String buildingUpLayer) {
        BuildingUpLayer = buildingUpLayer;
    }

    public String getBuildingOverLayer() {
        return BuildingOverLayer;
    }

    public void setBuildingOverLayer(String buildingOverLayer) {
        BuildingOverLayer = buildingOverLayer;
    }

    public String getBuildingRefuge() {
        return BuildingRefuge;
    }

    public void setBuildingRefuge(String buildingRefuge) {
        BuildingRefuge = buildingRefuge;
    }

    public String getBuildingFun() {
        return BuildingFun;
    }

    public void setBuildingFun(String buildingFun) {
        BuildingFun = buildingFun;
    }

    public String getBuildingImp() {
        return BuildingImp;
    }

    public void setBuildingImp(String buildingImp) {
        BuildingImp = buildingImp;
    }

    public String getBuildingWay() {
        return BuildingWay;
    }

    public void setBuildingWay(String buildingWay) {
        BuildingWay = buildingWay;
    }

    public String getAroundEast() {
        return AroundEast;
    }

    public void setAroundEast(String aroundEast) {
        AroundEast = aroundEast;
    }

    public String getAroundNorth() {
        return AroundNorth;
    }

    public void setAroundNorth(String aroundNorth) {
        AroundNorth = aroundNorth;
    }

    public String getAroundSouth() {
        return AroundSouth;
    }

    public void setAroundSouth(String aroundSouth) {
        AroundSouth = aroundSouth;
    }

    public String getAroundWest() {
        return AroundWest;
    }

    public void setAroundWest(String aroundWest) {
        AroundWest = aroundWest;
    }

    public String getMeidaPic() {
        return MeidaPic;
    }

    public void setMeidaPic(String meidaPic) {
        MeidaPic = meidaPic;
    }

    public String getMediaVid() {
        return MediaVid;
    }

    public void setMediaVid(String mediaVid) {
        MediaVid = mediaVid;
    }


}
