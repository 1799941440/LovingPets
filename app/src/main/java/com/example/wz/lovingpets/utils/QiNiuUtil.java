package com.example.wz.lovingpets.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.net.HttpRequest;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/7/12.
 */

public class QiNiuUtil {

    // 七牛云基地址
    public static final String QINIU_BASE_URL = "http://pjaorz5b8.bkt.clouddn.com/";

    //token
    private String token;

    private static String oldName;

    public QiNiuUtil() {
    }

    public QiNiuUtil(String oldName) {
        this.oldName = oldName;
    }

    // 写接口回调url给Activity
    public static QiNiuCloudEvent event;
    public interface QiNiuCloudEvent {
        void getUrlKey(String url);
    }
    public void setQiNiuCloudEvent(QiNiuCloudEvent event) {
        this.event = event;
    }

    //获取token，然后上传文件到七牛云
    public void startUpload(final File file, final Context context,int type,int typeId) {

        final String fileName = getFileKey(type,typeId);
        Observable<String> observable = HttpRequest.getApiservice().getToken(fileName);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("Tag", "获取上传token成功:"+s);
                updateFile(file,context,s,fileName);
            }
        });
    }

    private String getFileKey(int type, int id) {
        if(type == 0){
            return "user/head/"+id+"_"+getTempFileName();
        }else if(type == 1){
            return "user/theme/"+id+"_"+getTempFileName();
        }else if(type == 2){
            return "user/petHead/"+id+"_"+getTempFileName();
        }return "";
    }

    public String getTempFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String fileName = format.format(new Timestamp(System
                .currentTimeMillis()));
        return fileName;
    }

    /**
     * 上传文件到七牛存储空间
     *
     * @param file
     * @param context
     */
    public void updateFile(File file, final Context context , String updateToken, String upKey) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在上传头像，请稍候！");
        dialog.show();
        final UploadManager uploadManager = new UploadManager();
        Log.d("Tag", "updateFile里面的path: "+upKey);
        uploadManager.put(file, upKey, updateToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.statusCode == 200) {
                    dialog.dismiss();
                    String urlKey = "";
                    try {
                        urlKey = response.getString("key");
                        event.getUrlKey(QINIU_BASE_URL + urlKey);
                        Toast.makeText(context, "updateFile完成上传",
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "updateFile上传失败"+info.error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                dialog.setProgress((int) percent);
            }
        }, null));
    }
}
