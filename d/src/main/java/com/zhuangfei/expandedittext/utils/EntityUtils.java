package com.zhuangfei.expandedittext.utils;

import com.zhuangfei.expandedittext.entity.BaseEntity;
import com.zhuangfei.expandedittext.entity.EditEntity;
import com.zhuangfei.expandedittext.entity.EntityType;
import com.zhuangfei.expandedittext.entity.ImageEntity;

/**
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class EntityUtils {

    public static boolean isEditEntity(BaseEntity entity){
        if(entity.getType()== EntityType.TYPE_EDIT){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isImageEntity(BaseEntity entity){
        if(entity.getType()== EntityType.TYPE_IMAGE){
            return true;
        }else {
            return false;
        }
    }

    public static EditEntity getEditEntity(BaseEntity entity){
        if(isEditEntity(entity)){
            return (EditEntity) entity;
        }
        return null;
    }

    public static ImageEntity getImageEntity(BaseEntity entity){
        if(isImageEntity(entity)){
            return (ImageEntity) entity;
        }
        return null;
    }
}
