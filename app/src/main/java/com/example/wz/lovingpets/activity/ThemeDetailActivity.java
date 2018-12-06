package com.example.wz.lovingpets.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.ThemeCommentAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ThemeCommentInfo;
import com.example.wz.lovingpets.entity.ThemeInfo;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UIUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.zhuangfei.expandedittext.ExpandEditText;
import com.zhuangfei.expandedittext.entity.BaseEntity;
import com.zhuangfei.expandedittext.utils.DimensonUtils;
import com.zhuangfei.expandedittext.utils.DipUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class ThemeDetailActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv;
    private Button bt_comment;
    LayoutInflater mInflate;
    private ThemeInfo themeInfo;
    private ThemeCommentAdapter adapter;
    private int themeId,userId,commentPage = 1,maxPage=1;
    private LinearLayout ll_content,ll_noComment;
    private ImageView iv_back,iv_isCollect,iv_head,iv_left,iv_right;
    private List<ThemeCommentInfo> list_comment = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private TextView tv_title,tv_author,tv_date,tv_comment,
            tv_collect,tv_showComment,tv_petInfo,tv_page,tv_totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_detail);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        themeId = getIntent().getIntExtra("id",0);
        iv_back = findViewById(R.id.theme_detail_back);
        rv = findViewById(R.id.theme_detail_rv);
        bt_comment = findViewById(R.id.theme_detail_comment);
        iv_isCollect = findViewById(R.id.theme_detail_isCollect);
        iv_head = findViewById(R.id.theme_detail_head);
        iv_left = findViewById(R.id.theme_detail_narrow_left);
        iv_right = findViewById(R.id.theme_detail_narrow_right);
        tv_title = findViewById(R.id.theme_detail_title);
        tv_author = findViewById(R.id.theme_detail_author);
        tv_date = findViewById(R.id.theme_detail_date);
        tv_comment = findViewById(R.id.theme_detail_tv_comment);
        tv_collect = findViewById(R.id.theme_detail__tv_collect);
        tv_petInfo = findViewById(R.id.theme_detail_petInfo);
        tv_page = findViewById(R.id.theme_detail_page);
        tv_totalPage = findViewById(R.id.theme_detail_totalPage);
        ll_content = findViewById(R.id.theme_detail_ll_content);
        ll_noComment = findViewById(R.id.theme_detail_no_comment);
        tv_showComment = findViewById(R.id.theme_detail_showComment);
    }

    @Override
    protected void initData() {
        adapter = new ThemeCommentAdapter(getContext(),list_comment,0);
        rv.setAdapter(adapter);
        userId = new UserUtil(this).getUser().getId();
        mInflate = LayoutInflater.from(this);
        getThemeInfo();
        iv_back.setOnClickListener(this);
        iv_isCollect.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        bt_comment.setOnClickListener(this);
    }

    private void getThemeInfo() {
        Observable<ListResponse<ThemeInfo>> observable = api.getThemeById(themeId,userId);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ThemeInfo>>() {
            @Override
            public void onSuccess(ListResponse<ThemeInfo> listResponse) {
                if(listResponse.isSuccess()){
                    themeInfo = listResponse.getRows().get(0);
                    list_comment = themeInfo.getComments();
                    setDatas();
                }
            }
        });
    }

    private void setDatas() {
        if(themeInfo.getComments().size() == 0){
            ll_noComment.setVisibility(View.VISIBLE);
            tv_showComment.setVisibility(View.GONE);
            tv_page.setText("0");
            tv_totalPage.setText("/0页");
        }
        adapter = new ThemeCommentAdapter(this,list_comment,themeInfo.getOwnerId());
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        tv_title.setText(themeInfo.getTitle());
        tv_petInfo.setText("故事主角："+themeInfo.getNickName()
                +"("+themeInfo.getClassName()+")");
        ImageUtil.loadRoundImage(iv_head,themeInfo.getIcon());
        tv_author.setText(themeInfo.getUserName());
        tv_date.setText(DateUtil.DateToString(themeInfo.getPushTime()));
        tv_comment.setText(themeInfo.getComment()+"");
        maxPage = (int)Math.ceil(themeInfo.getComment()/10.0f);
        tv_totalPage.setText("/"+maxPage+"页");
        tv_collect.setText(themeInfo.getCollection()+"");
        iv_isCollect.setImageResource(themeInfo.getIsCollect() == 0
                ? R.drawable.ic_my_collection : R.drawable.ic_collected);
        setContent(themeInfo.getContent());
    }

    private void setContent(String text) {
        List<BaseEntity> entityList = new ArrayList<>();
        String nowStr = text;
        int preEnd = 0;
        String pattern=getPattern();
        Pattern r=Pattern.compile(pattern);
        Matcher matcher=r.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            String preString = nowStr.substring(preEnd, start);
            preEnd = matcher.end();

            final String url = matcher.group(1);

            if (!preString.isEmpty()) {
                addTextView(preString);
            }

            addImageView(url);
        }
        if (preEnd < text.length()) {
            String string = nowStr.substring(preEnd);
            addTextView(string);
        }
    }

    private void addTextView(String text){
        View view = mInflate.inflate(com.zhuangfei.expandedittext.R.layout.layout_text, null, false);
        TextView textView = view.findViewById(com.zhuangfei.expandedittext.R.id.id_expand_text);
        textView.setText(text);
        textView.setPadding(0, UIUtils.dip2Px(getContext(),10),0,UIUtils.dip2Px(getContext(),10));
        textView.setTextColor(Color.parseColor("#000000"));
        ll_content.addView(view);
    }

    private void addImageView(String text){
        View view = mInflate.inflate(com.zhuangfei.expandedittext.R.layout.layout_imageview, null, false);
        ImageView imageView=view.findViewById(com.zhuangfei.expandedittext.R.id.id_expand_imageview);
        int width= DimensonUtils.getWidthInPx(getContext()) - DipUtils.dip2px(getContext(), 35);
        setImageView(imageView,text,width);
        ll_content.addView(view);
    }

    public void setImageView(final ImageView imageView, String replace, final int width) {
        Glide.with(getContext()).asBitmap().load(replace).apply(new RequestOptions().placeholder(com.zhuangfei.expandedittext.R.drawable.ic_holder))
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        para.width = width;
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public String getPattern() {
        return "!\\[img\\]\\((\\S.*?)\\)";
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme_detail_back :
                finish();
                break;
            case R.id.theme_detail_isCollect :
                if(themeInfo.getIsCollect() == 0){
                    collectTheme();
                }else{
                    unCollectTheme();
                }
                break;
            case R.id.theme_detail_narrow_left :
                getLastPage();
                break;
            case R.id.theme_detail_narrow_right :
                getNextPage();
                break;
            case R.id.theme_detail_comment :
                intent2Activity(CommentThemeActivity.class);
                break;
        }
    }

    private void getNextPage() {
        if(commentPage == maxPage){
            showLongToast("已经是最后一页了哦");
            return;
        }
        Observable<ListResponse<ThemeCommentInfo>> observable = api.getCommentByThemeId(themeId,commentPage+1);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ThemeCommentInfo>>() {
            @Override
            public void onSuccess(ListResponse<ThemeCommentInfo> listResponse) {
                if(listResponse.isSuccess()){
                    list_comment.clear();
                    list_comment.addAll(listResponse.getRows());
                    adapter.notifyDataSetChanged();
                    rv.scrollToPosition(0);
                    commentPage += 1;
                    tv_page.setText(commentPage+"");
                }
            }
        });
    }

    private void getLastPage() {
        if(commentPage == 1){
            showLongToast("已经是第一页了哦");
            return;
        }
        Observable<ListResponse<ThemeCommentInfo>> observable = api.getCommentByThemeId(themeId,commentPage-1);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ThemeCommentInfo>>() {
            @Override
            public void onSuccess(ListResponse<ThemeCommentInfo> listResponse) {
                if(listResponse.isSuccess()){
                    list_comment.clear();
                    list_comment.addAll(listResponse.getRows());
                    adapter.notifyDataSetChanged();
                    rv.scrollToPosition(0);
                    commentPage -= 1;
                    tv_page.setText(commentPage+"");
                }
            }
        });
    }

    private void unCollectTheme() {
        Observable<ListResponse> observable = api.unCollect(themeId,userId,1);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("取消收藏主题成功");
                    themeInfo.setIsCollect(0);
                    iv_isCollect.setImageResource(R.drawable.ic_my_collection);
                }
            }
        });
    }

    private void collectTheme() {
        Observable<ListResponse> observable = api.collect(themeId,userId,1);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showLongToast("收藏主题成功");
                    themeInfo.setIsCollect(1);
                    iv_isCollect.setImageResource(R.drawable.ic_collected);
                }
            }
        });
    }
}
