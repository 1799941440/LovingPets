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
            Arrays.asList("鸟儿主粮", "鸟儿玩具", "鸟儿清洁", "鸟儿保健", "鸟儿日用", "鸟儿出行"),
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
        Arrays.asList("狗狗餐具","狗狗住所","狗狗训练","智能黑科技","主人专享"),
        Arrays.asList("伸缩牵引","日常颈圈","日常拉带","拉颈套装","拉胸套装","P链/蛇链"),
        Arrays.asList("梳剪工具","护理工具","美容配套"),
        Arrays.asList("时尚服装","精美配饰","断码反季"),
        Arrays.asList("专用香波","日常洗护","免洗干洗","洗澡工具","户外运动","外出餐具","外出箱包")
    );

    public static final List<List<Integer>> classify_iv_cat = Arrays.asList(
        Arrays.asList(R.mipmap.cat1_1,R.mipmap.cat1_2,R.mipmap.cat1_3),
        Arrays.asList(R.mipmap.cat2_1,R.mipmap.cat2_2,R.mipmap.cat2_3,R.mipmap.cat2_4),
        Arrays.asList(R.mipmap.cat3_1,R.mipmap.cat3_2,R.mipmap.cat3_3),
        Arrays.asList(R.mipmap.cat4_1,R.mipmap.cat4_2,R.mipmap.cat4_3,R.mipmap.cat4_4,R.mipmap.cat4_5),
        Arrays.asList(R.mipmap.cat5_1,R.mipmap.cat5_2,R.mipmap.cat5_3,R.mipmap.cat5_4,R.mipmap.cat5_5,R.mipmap.cat5_6,R.mipmap.cat5_7),
        Arrays.asList(R.mipmap.cat6_1,R.mipmap.cat6_2,R.mipmap.cat6_3,R.mipmap.dog6_4,R.mipmap.cat6_5),
        Arrays.asList(R.mipmap.cat7_1,R.mipmap.cat7_2),
        Arrays.asList(R.mipmap.cat8_1,R.mipmap.cat8_2,R.mipmap.cat8_3,R.mipmap.cat8_4),
        Arrays.asList(R.mipmap.cat9_1,R.mipmap.cat9_2),
        Arrays.asList(R.mipmap.cat10_1,R.mipmap.cat10_2,R.mipmap.cat10_3,R.mipmap.cat10_4,R.mipmap.cat10_5)
    );

    public static final List<List<String>> classify_tv_cat = Arrays.asList(
        Arrays.asList("进口猫粮","国产猫粮","处方猫粮"),
        Arrays.asList("磨爪玩具","爬架/家具","休闲玩具","逗猫杆"),
        Arrays.asList("猫咪猫砂","猫咪厕所","清洁除臭"),
        Arrays.asList("强化免疫","补钙强骨","微量元素","美毛增毛","肠胃调理"),
        Arrays.asList("体内驱虫","体外驱虫","耳部护理","眼部护理","口腔护理","皮肤护理","家用常备"),
        Arrays.asList("猫碗专区","猫咪餐具","猫咪住所","智能黑科技","主人专享"),
        Arrays.asList("指甲剪","梳剪工具"),
        Arrays.asList("外出餐具","猫咪牵引","外出用具","防走丢"),
        Arrays.asList("猫咪服饰","猫咪饰品"),
        Arrays.asList("专用配方","日常洗护","护毛美毛","免洗干洗","洗澡工具")
    );
//    "鸟儿主粮"国产、进口、鹩哥专用, "鸟儿玩具"玩偶、休闲玩具、学话机、站立玩具, "鸟儿清洁"打扫用具、澡盆, "鸟儿保健"体内体外
// , "鸟儿日用"鸟窝、笼子、喂食器、饮水盆、食盆, "鸟儿出行"便携式遛鸟笼,

    public static final List<List<Integer>> classify_iv_bird = Arrays.asList(
        Arrays.asList(R.mipmap.bird1_1,R.mipmap.bird1_2,R.mipmap.bird1_3),
        Arrays.asList(R.mipmap.bird2_1,R.mipmap.bird2_2,R.mipmap.bird2_3,R.mipmap.bird2_4),
        Arrays.asList(R.mipmap.bird3_1,R.mipmap.bird3_2),
        Arrays.asList(R.mipmap.bird4_1,R.mipmap.bird4_2),
        Arrays.asList(R.mipmap.bird5_1,R.mipmap.bird5_2,R.mipmap.bird5_3,R.mipmap.bird5_4,R.mipmap.bird5_5),
        Arrays.asList(R.mipmap.bird6_1)
    );
    public static final List<List<String>> classify_tv_bird = Arrays.asList(
            Arrays.asList("国产鸟粮","进口鸟粮","鹩哥专用"),
            Arrays.asList("毛绒玩偶","休闲玩具","学话机","站立玩具"),
            Arrays.asList("打扫用具","澡盆"),
            Arrays.asList("体内驱虫","体外驱虫"),
            Arrays.asList("鸟窝","笼子","喂食器","饮水盆","食盆"),
            Arrays.asList("遛鸟笼")
    );


    public static final List<List<Integer>> classify_iv_fish = Arrays.asList(
        Arrays.asList(R.mipmap.fish1_1,R.mipmap.fish1_2),
        Arrays.asList(R.mipmap.fish2_1,R.mipmap.fish2_2,R.mipmap.fish2_3,R.mipmap.fish2_4,R.mipmap.fish2_5,R.mipmap.fish2_6),
        Arrays.asList(R.mipmap.fish3_1,R.mipmap.fish3_2,R.mipmap.fish3_3),
        Arrays.asList(R.mipmap.fish4_1,R.mipmap.fish4_2),
        Arrays.asList(R.mipmap.fish5_1,R.mipmap.fish5_2,R.mipmap.fish5_3)
    );
    public static final List<List<String>> classify_tv_fish = Arrays.asList(
        Arrays.asList("成品鱼缸","桌面鱼缸"),
        Arrays.asList("造流过滤","曝气增氧","加热温控","照明灯具","CO2器材","工具配件"),
        Arrays.asList("水质调理","水草养护","鱼病防治"),
        Arrays.asList("鱼粮饲料","虾粮龟粮"),
        Arrays.asList("底床泥沙","造景饰物","造景工具")
    );

    //添加宠物界面dialog的选择列表
    public static final List<String> list_pet_sex = Arrays.asList("公","母");
    public static final List<String> list_user_sex = Arrays.asList("男","女");
    public static final List<String> list_class = Arrays.asList("猫","狗","鸟","水族","其他");
    public static final List<String> list_state = Arrays.asList("正常","征婚","需要被领养","已丢失","已去世");
    public static final List<List<String>> list_family = Arrays.asList(
            Arrays.asList("曼基康猫","热带草原猫","塞斯克猫","波斯猫","白猫","英国短尾猫","异国短尾猫（加菲）","暹罗猫","苏格兰折耳猫","布偶猫","孟加拉豹猫","蓝猫","缅因猫",
            "波斯猫","金吉拉","加拿大无毛猫","田园猫","奥西猫","埃及猫","阿比西尼亚猫","巴厘猫","波米拉猫","伯曼猫","德国卷毛猫","蒂法尼猫","东方长毛猫","高低折耳猫","荒漠猫","哈瓦那棕猫",
            "挪威森林猫","拉邦猫","狸花猫","孟买猫","美国刚毛猫","内华达猫","欧西猫","四川简州猫","塞伦盖蒂猫","山东狮子猫","索马里猫",
            "土耳其梵猫","玩具虎猫","雪鞋猫","新加坡猫","小精灵短尾猫","爪哇猫","云猫"),
            Arrays.asList(
                    "阿拉斯加","艾斯摩基犬","澳大利亚梗", "爱尔兰水猎犬","爱尔兰梗","澳大利亚牧羊犬","爱尔兰雪达犬","阿芬平斯澈犬","阿富汗猎犬","阿根廷杜高犬","澳大利亚灵缇","比熊犬","博美","边境牧羊犬","八哥犬","比格","巴吉度","比利时牧羊犬",
                    "贝灵顿","边境梗","波音达","比利牛斯獒犬","波登可犬","比特犬","巴西菲勒犬","捕鼠梗","波利犬","布里塔尼犬","伯瑞犬","波士顿梗","伯恩山犬","柴犬","查尔斯小猎犬","长须牧羊犬","川东猎犬","串串","德国牧羊犬","大麦町","大白熊","杜宾犬","大丹犬","斗牛獒","佛兰德","法老王猎犬","法国斗牛犬","古代牧羊犬","中国冠毛犬","高加索牧羊犬","戈登雪达犬","贵宾犬",
                    "哈士奇","蝴蝶犬","湖畔梗","荷兰毛狮犬","惠比特犬","哈威那犬","狐狸犬","金毛","京巴犬","吉娃娃","杰克罗素梗","加纳利","美国可卡犬","柯基犬","凯利蓝梗","卡斯罗","昆明犬","可蒙犬","凯恩梗","库维斯犬","库瓦兹犬","拉布拉多","腊肠犬","罗威纳","拉萨犬","意大利灵缇犬","狼狗","小鹿犬","罗威士梗","爱尔兰猎狼犬","罗得西亚脊背犬","鹿犬","猎狐梗","拉普杭犬","迷你杜宾","玛尔济斯","迷你牛头梗","曼彻斯特","美国斗牛犬","蒙古细犬","马犬","牛头梗","纽芬兰犬","纽波利顿藏獒","挪威猎麋犬","拳师犬","秋田犬","其他","萨摩耶","苏格兰牧羊犬","松狮",
                    "沙皮犬","圣伯纳","苏格兰梗","苏俄猎狼","丝毛梗","斯宾诺","史宾格","舒伯齐犬","苏联红", "雪纳瑞","西施犬","西部高地犬","英国斗牛犬","银狐","藏獒","中华田园犬", "中亚牧羊犬"
            )
    );
}
