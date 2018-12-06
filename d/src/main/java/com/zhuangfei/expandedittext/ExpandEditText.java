package com.zhuangfei.expandedittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.expandedittext.entity.BaseEntity;
import com.zhuangfei.expandedittext.entity.EditEntity;
import com.zhuangfei.expandedittext.entity.EntityType;
import com.zhuangfei.expandedittext.entity.ImageEntity;
import com.zhuangfei.expandedittext.entity.TextEntity;
import com.zhuangfei.expandedittext.listener.OnExpandBuildListener;
import com.zhuangfei.expandedittext.listener.OnExpandImageClickListener;
import com.zhuangfei.expandedittext.loader.GlideImageLoader;
import com.zhuangfei.expandedittext.loader.ImageLoader;
import com.zhuangfei.expandedittext.utils.BitmapUtils;
import com.zhuangfei.expandedittext.utils.DipUtils;
import com.zhuangfei.expandedittext.utils.EntityUtils;
import com.zhuangfei.expandedittext.utils.InputMethodUtils;
import com.zhuangfei.expandedittext.utils.DimensonUtils;
import com.zhuangfei.expandedittext.wrapper.DefaultImageWrapper;
import com.zhuangfei.expandedittext.wrapper.ImageWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扩展的EditText控件
 * 提供图文混排的编辑功能以及解析功能
 * 基本思路：LinearLayout布局内动态添加EditText、ImageView
 * Created by Liu ZhuangFei on 2018/2/26.
 */

public class ExpandEditText extends LinearLayout {

    private LayoutInflater mInflate;

    //记录添加的BaseEntity
    private List<BaseEntity> entityList;

    private Activity activity;

    private ImageWrapper wrapper;

    private ImageLoader imageLoader;

    private String hintText = "";

    private boolean isShowMode = false;

    private boolean editadle = true;

    //图片点击监听接口
    private OnExpandImageClickListener onExpandImageClickListener;

    private OnExpandBuildListener onExpandBuildListener;

    public ExpandEditText(Context context) {
        this(context, null, 0);
    }

    public ExpandEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inits();
    }

    private void inits() {
        mInflate = LayoutInflater.from(getContext());
        entityList = new ArrayList<>();
        if(editadle){
            addFocusListener();
        }
        createEditEntity(getEntityList().size());
    }

    public ExpandEditText bind(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ExpandEditText setOnExpandImageClickListener(OnExpandImageClickListener onExpandImageClickListener) {
        this.onExpandImageClickListener = onExpandImageClickListener;
        return this;
    }

    public ExpandEditText setOnExpandBuildListener(OnExpandBuildListener onExpandBuildListener) {
        this.onExpandBuildListener = onExpandBuildListener;
        return this;
    }

    public ExpandEditText setWrapper(ImageWrapper wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public ExpandEditText setHintText(String hintText) {
        this.hintText = hintText;
        changeHint();
        return this;
    }

    public ExpandEditText setShowMode(boolean showMode) {
        isShowMode = showMode;
        return this;
    }

    public ExpandEditText setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public List<BaseEntity> getEntityList() {
        return entityList;
    }

    public BaseEntity getEntity(int index) {
        if (index < 0 || index >= entityList.size()) return null;
        return entityList.get(index);
    }

    public ImageWrapper getWrapper() {
        if(wrapper==null){
            wrapper=new DefaultImageWrapper();
        }
        return wrapper;
    }

    public ImageLoader getImageLoader() {
        if(imageLoader==null) imageLoader= new GlideImageLoader(getContext());
        return imageLoader;
    }

    public String getHintText() {
        return hintText;
    }

    public boolean isShowMode() {
        return isShowMode;
    }

    public void clear() {
        removeAllViews();
        entityList.clear();
    }

    public int indexOfEntity(BaseEntity entity) {
        return entityList.indexOf(entity);
    }

    /**
     * 将文本解析为图文混合
     *
     * @param text
     */
    public void load(String text) {
        Log.i("Load",text);
        clear();
        getWrapper().parse(this,text);
    }

    public ImageEntity parseImageEntity(String localPath) {
        Log.i("Path",localPath);
        View view = mInflate.inflate(R.layout.layout_imageview, null, false);
        addView(view);

        int width=DimensonUtils.getWidthInPx(getContext()) - DipUtils.dip2px(getContext(), 35);
        //view.findViewById(R.id.id_expand_imageview);
        ImageView imageView=getImageLoader().getImageView(view);
        getImageLoader().setImageView(imageView,localPath,width);
        if(imageView==null){
            return null;
        }

        ImageEntity imageEntity = new ImageEntity(localPath,imageView);
        entityList.add(imageEntity);
        setOnImageListener(imageEntity);
        return imageEntity;
    }

    /**
     * 创建TextView
     */
    public TextEntity parseTextEntity() {
        View view = mInflate.inflate(R.layout.layout_text, null, false);
        TextView textView = view.findViewById(R.id.id_expand_text);
        addView(textView);

        if (onExpandBuildListener != null) {
            onExpandBuildListener.onTextBuild(textView);
        }

        TextEntity textEntity = new TextEntity(textView);
        entityList.add(textEntity);
        return textEntity;
    }

    public TextEntity parseTextEntity(String text) {
        TextEntity textEntity=parseTextEntity();
        textEntity.setText(text);
        return textEntity;
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(String text) {
        EditEntity entity = getFinalEditEntity();
        entity.setText(text);
    }

    /**
     * 追加文本
     *
     * @param text
     */
    public void appendText(String text) {
        EditEntity entity = getFinalEditEntity();
        entity.setText(entity.getText() + text);
    }

    /**
     * 获取文本
     *
     * @return
     */
    public String getText() {
        String text = "";
        for (BaseEntity entity : entityList) {
            if (entity.getType() == EntityType.TYPE_IMAGE) {
                text += getWrapper().getImageWrapper(entity.getText());
            } else {
                text += entity.getText();
            }
        }
        return text;
    }

    /**
     * 判断当前哪个实体获取了焦点
     * @return
     */
    private int indexOfCursor() {
        for (int i = 0; i < entityList.size(); i++) {
            BaseEntity entity = entityList.get(i);
            if (entity.getType() == EntityType.TYPE_EDIT) {
                EditEntity editEntity = EntityUtils.getEditEntity(entity);
                if (editEntity.getEditText().isFocused()) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 在光标处插入Bitmap
     *
     * @param bitmap
     */
    public void insertBitmap(Bitmap bitmap, String replace) {
        int index = indexOfCursor();
        if (index != -1) {
            createImageEntity(bitmap, replace, index + 1);
        } else {
            appendBitmap(bitmap, replace);
        }
    }

    /**
     * 在文尾追加一个Bitmap
     *
     * @param bitmap
     */
    public void appendBitmap(Bitmap bitmap, String replace) {
        createImageEntity( bitmap, replace, entityList.size());
    }

    /**
     * 追加图片的逻辑封装，drawable、bitmap哪个非空即使用哪种方式
     *
     * @param bitmap
     * @param replace
     */
    public ImageEntity createImageEntity(Bitmap bitmap, String replace, int index) {
        getFinalEditEntity();
        View view = getImageLoader().getView(mInflate);
        addView(view, index);

        int width=DimensonUtils.getWidthInPx(getContext())-DipUtils.dip2px(getContext(),35);
        ImageView imageView = getImageLoader().getImageView(view);
        Bitmap newBitmap = BitmapUtils.zoomAdapter(bitmap, width);
        imageView.setImageBitmap(newBitmap);
        ImageEntity imageEntity = new ImageEntity(replace, imageView);

        entityList.add(index,imageEntity);
        setOnImageListener(imageEntity);
        createEditEntity(entityList.size());

        EditEntity afterEditEntity=getEditEntityAfter(index);
        changeHint();
        requestEditFocus(afterEditEntity);
        return imageEntity;
    }

    /**
     * 设置图片点击事件
     * @param imageEntity
     */
    private void setOnImageListener(final ImageEntity imageEntity) {
        if (imageEntity == null) return;
        final ImageView imageView = imageEntity.getImageView();
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onExpandImageClickListener != null) {
                    onExpandImageClickListener.onImageClick(imageView, imageEntity);
                }
            }
        });
    }

    /**
     * 获取最后的一个可编辑实体，如果不存在，则新建
     *
     * @return
     */
    public EditEntity getFinalEditEntity() {
        int size = entityList.size();
        EditEntity resultEntity;
        if (size > 0) {
            BaseEntity entity = entityList.get(size - 1);
            if (entity.getType() == EntityType.TYPE_EDIT) {
                resultEntity = (EditEntity) entity;
            } else {
                resultEntity = createEditEntity(entityList.size());
            }
        } else {
            resultEntity = createEditEntity(entityList.size());
        }
        return resultEntity;
    }

    public EditEntity getEditEntityAfter(int index) {
        int size = entityList.size();
        EditEntity resultEntity=null;
        if(index>=(size-1)&&index>=0){
            resultEntity=createEditEntity(size);
        }else{
            BaseEntity entity=entityList.get(index+1);
            if(entity.getType()==EntityType.TYPE_EDIT){
                resultEntity=EntityUtils.getEditEntity(entity);
            }else{
                resultEntity=createEditEntity(index+1);
            }
        }
        return resultEntity;
    }

    /**
     * 创建可编辑的实体
     */
    public EditEntity createEditEntity(int index) {
        View view = mInflate.inflate(R.layout.layout_edittext, null, false);
        EditText editText = view.findViewById(R.id.id_expand_edittext);
        addView(editText,index);
        if (entityList.size() == 0) {
            editText.setHint(hintText);
        }

        if (onExpandBuildListener != null) {
            onExpandBuildListener.onEditBuild(editText);
        }

        EditEntity entity = new EditEntity(editText);
        entityList.add(index,entity);
        setOnKeyListener(entity);
        changeHint();
        return entity;
    }

    /**
     * 设置按键监听
     * @param entity
     */
    public void setOnKeyListener(EditEntity entity) {
        final EditEntity tmpEditEntity = entity;
        final EditText tmpEditText = entity.getEditText();
        tmpEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL) {
                    Log.i("DELETE", "点击删除按键");
                    onDeleteEvent(tmpEditEntity);
                }
                return false;
            }
        });
    }

    /**
     * 删除的逻辑
     * @param entity
     */
    private void onDeleteEvent(EditEntity entity) {
        int index = entityList.indexOf(entity);
        Log.i("Index", "" + index);
        Log.i("Length", entityList.size() + "");
        if (index > 0) {
            EditText editText = entity.getEditText();
            int cursorIndex = editText.getSelectionStart();
            //执行删除操作
            if (editText != null && cursorIndex == 0) {
                Log.i("REMOVE", "prepre remove...");
                BaseEntity preEntity = entityList.get(index - 1);

                //1.上一个元素为图片时，直接清除上一个元素即可
                if (preEntity != null && EntityUtils.isImageEntity(preEntity)) {
                    removeExpandViewAt(index - 1);
                }

                //2.上一个元素是EditText时，清除本身，移动鼠标位置至上个元素末尾并将本身文字追加到上个元素末尾
                if (preEntity != null && EntityUtils.isEditEntity(preEntity)) {
                    Log.i("REMOVE", "remove editEntity...");
                    mergeEditEntity(entity, preEntity, index);
                    EditEntity preEditEntity = EntityUtils.getEditEntity(preEntity);
                }
            }
        }

        changeHint();
    }

    /**
     * 改变Hint、判断是否应该有hint
     */
    public void changeHint() {
        if (!getText().isEmpty()||entityList.size()>1) {
            clearHint();
        }

        if(getText().isEmpty()&&entityList.size()<=1){
            clearHint();
            EditEntity entity = getFirstEditEntity();
            entity.getEditText().setHint(hintText);
        }
    }

    public void clearHint() {
        //清除Hint
        for (BaseEntity entity : entityList) {
            if (entity.getType() == EntityType.TYPE_EDIT) {
                EditEntity editEntity = EntityUtils.getEditEntity(entity);
                editEntity.getEditText().setHint("");
            }
        }
    }

    /**
     * 获取第一个可编辑实体，如果不存在，则新建
     *
     * @return
     */
    public EditEntity getFirstEditEntity() {
        int size = entityList.size();
        EditEntity resultEntity = null;
        if (size > 0) {
            boolean isHaveEditEntity = false;
            for (BaseEntity entity : entityList) {
                if (entity.getType() == EntityType.TYPE_EDIT) {
                    resultEntity = (EditEntity) entity;
                    isHaveEditEntity = true;
                    break;
                }
            }
            if (!isHaveEditEntity) resultEntity = createEditEntity(0);
        } else {
            resultEntity = createEditEntity(entityList.size());
        }
        return resultEntity;
    }

    /**
     * 进行实体的合并
     *
     * @param entity
     * @param preEntity
     * @param entityIndex
     */
    public void mergeEditEntity(EditEntity entity, BaseEntity preEntity, int entityIndex) {
        String value = entity.getText();
        EditEntity preEditEntity = EntityUtils.getEditEntity(preEntity);
        String preValue = preEditEntity.getText();

        removeExpandViewAt(entityIndex);
        int start = 0;
        if (!TextUtils.isEmpty(preValue)) {
            start = preValue.length();
        }

        preEditEntity.setText(preValue + value);

        preEditEntity.getEditText().requestFocus();
        preEditEntity.getEditText().setSelection(start);
    }

    /**
     * 根据索引删除视图
     * @param index
     */
    public void removeExpandViewAt(int index) {
        entityList.remove(index);
        removeViewAt(index);
    }

    /**
     *根据实体删除视图
     * @param baseEntity
     */
    public void removeExpandViewAt(BaseEntity baseEntity) {
        int index=entityList.indexOf(baseEntity);
        if(index==-1) return;
        entityList.remove(index);
        removeViewAt(index);
    }

    /**
     * 将最后一个EditText设为焦点
     */
    public void requestFinalEditFocus() {
        EditEntity entity = getFinalEditEntity();
        requestEditFocus(entity);
    }

    /**
     * 将EditEntity所表示的EditText设为焦点
     * @param editEntity
     */
    public void requestEditFocus(EditEntity editEntity){
        EditText editText = editEntity.getEditText();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
    }

    /**
     * 设置点击空白区域则将最后一个可编辑的区域设为焦点
     */
    public void addFocusListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestFinalEditFocus();
                if (activity != null) {
                    InputMethodUtils.open(activity);
                }
            }
        });
    }

    public void setEditadle(boolean editadle) {
        this.editadle = editadle;
    }
}
