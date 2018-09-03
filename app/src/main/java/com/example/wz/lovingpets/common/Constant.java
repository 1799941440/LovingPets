package com.example.wz.lovingpets.common;

import com.example.wz.lovingpets.R;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final int[] allThemes = {R.style.AppTheme_dog, R.style.AppTheme_cat, R.style.AppTheme_bird, R.style.AppTheme_fish};
    public static final int[] mainLayouts = {R.layout.fragment_layout_main,R.layout.fragment_layout_food,R.layout.fragment_layout_medical,R.layout.fragment_layout_toy,R.layout.fragment_layout_out};
    public static final String[] mainName = {"首页","主粮","医疗","玩具","外出"};
    public static final List<List<String>> classifyList = Arrays.asList(
            Arrays.asList("狗狗主粮", "狗狗玩具", "狗狗清洁", "狗狗保健", "狗狗护理", "狗狗日用", "狗狗牵引", "狗狗美容", "狗狗服饰", "出游洗澡"),
            Arrays.asList("猫咪主粮", "猫咪玩具", "猫咪清洁", "猫咪保健", "猫咪护理", "猫咪日用", "猫咪美容", "猫咪出行", "猫咪装扮", "猫咪洗浴"),
            Arrays.asList("鸟儿主粮", "鸟儿玩具", "鸟儿清洁", "鸟儿保健", "鸟儿日用", "鸟儿出行", "鸟儿装扮"),
            Arrays.asList("水族箱", "功能器材", "水族药剂", "水族饲料", "生态造景"));
    public static final List<List<Integer>> classify_iv_dog = Arrays.asList(
            Arrays.asList(R.mipmap.dog1_1,R.mipmap.dog1_2,R.mipmap.dog1_3,R.mipmap.dog1_4),
            Arrays.asList(R.mipmap.dog2_1,R.mipmap.dog2_2,R.mipmap.dog2_3,R.mipmap.dog2_4,R.mipmap.dog2_5,R.mipmap.dog2_6),
            Arrays.asList(R.mipmap.dog3_1,R.mipmap.dog3_2,R.mipmap.dog3_3,R.mipmap.dog3_4),
            Arrays.asList(R.mipmap.dog4_1,R.mipmap.dog4_2,R.mipmap.dog4_3,R.mipmap.dog4_4,R.mipmap.dog4_5,R.mipmap.dog4_6),
            Arrays.asList(R.mipmap.dog5_1,R.mipmap.dog5_2,R.mipmap.dog5_3,R.mipmap.dog5_4,R.mipmap.dog5_5,R.mipmap.dog5_6,R.mipmap.dog5_7),
            Arrays.asList(R.mipmap.dog6_1,R.mipmap.dog6_2,R.mipmap.dog6_3,R.mipmap.dog6_4,R.mipmap.dog6_5),
            Arrays.asList(R.mipmap.dog7_1,R.mipmap.dog7_2,R.mipmap.dog7_3,R.mipmap.dog7_4,R.mipmap.dog7_5,R.mipmap.dog7_6),
            Arrays.asList(R.mipmap.dog8_1,R.mipmap.dog8_2,R.mipmap.dog8_3),
            Arrays.asList(R.mipmap.dog9_1,R.mipmap.dog9_2,R.mipmap.dog9_3),
            Arrays.asList(R.mipmap.dog10_1,R.mipmap.dog10_2,R.mipmap.dog10_3,R.mipmap.dog10_4,R.mipmap.dog10_5,R.mipmap.dog10_6,R.mipmap.dog10_7)
    );

    public static final List<List<String>> classify_tv_dog = Arrays.asList(
            Arrays.asList("进口狗粮","国产狗粮","处方狗粮","冻干狗粮"),
            Arrays.asList("棉质玩具","橡胶玩具","塑料玩具","食用玩具","木质玩具","乳胶玩具"),
            Arrays.asList("狗狗厕所","狗狗尿片","狗狗湿巾","清洁除臭"),
            Arrays.asList("强化免疫","美毛增毛","肠胃调理","补钙强骨","微量元素","关节呵护"),
            Arrays.asList("体外驱虫","体内驱虫","皮肤护理","二部护理","眼部护理","口鼻护理","家用常备"),
            Arrays.asList("狗狗餐具","狗狗住所","狗狗训练","智能黑科技","主任专享"),
            Arrays.asList("伸缩牵引","日常颈圈","日常拉带","拉颈套装","拉胸套装","P链/蛇链"),
            Arrays.asList("梳剪工具","护理工具","美容配套"),
            Arrays.asList("时尚服装","精美配饰","断码反季"),
            Arrays.asList("专用香波","日常洗护","免洗干洗","洗澡工具","户外运动","外出餐具","外出箱包")
    );
}
