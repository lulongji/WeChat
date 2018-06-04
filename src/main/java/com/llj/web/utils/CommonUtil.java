package com.llj.web.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * Created by lu on 2017/2/13.
 * <p>
 * Description:常用类
 */
public class CommonUtil {
    /**
     * 日志
     */
    private static Logger logger = LogManager.getLogger(CommonUtil.class.getName());


    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return jsonObject;
    }


    /**
     * 随机获取人名
     *
     * @return
     */
    public static String getNickName() {
        String[] firsname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈",
                "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕",
                "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢",
                "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范",
                "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任",
                "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
                "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于",
                "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟",
                "平", "黄", "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛",
                "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋",
                "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮",
                "蓝", "闽", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童",
                "颜", "郭", "梅", "盛", "林", "刁", "锺", "徐", "丘", "骆", "高", "夏",
                "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管",
                "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣",
                "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉",
                "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊",
                "於", "惠", "甄", "麹", "家", "封", "芮", "羿", "储", "靳", "汲", "邴",
                "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗",
                "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲",
                "伊", "宫", "宁", "仇", "栾", "暴", "甘", "斜", "厉", "戎", "祖", "武",
                "符", "刘", "景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎",
                "蓟", "薄", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸",
                "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥", "能",
                "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申",
                "扶", "堵", "冉", "宰", "郦", "雍", "郤", "璩", "桑", "桂", "濮", "牛",
                "寿", "通", "边", "扈", "燕", "冀", "郏", "浦", "尚", "农", "温", "别",
                "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾",
                "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居",
                "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄",
                "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩",
                "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简",
                "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关",
                "蒯", "相", "查", "后", "荆", "红", "游", "竺", "权", "逑", "盖", "益",
                "桓", "公", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连",
                "皇甫", "尉迟", "公羊", "澹台", "公冶", "宗政", "濮阳", "淳于", "单于", "太叔",
                "申屠", "公孙", "仲孙", "轩辕", "令狐", "锺离", "宇文", "长孙", "慕容", "鲜于",
                "闾丘", "司徒", "司空", "丌官", "司寇", "仉", "督", "子车", "颛孙", "端木", "巫马",
                "公西", "漆雕", "乐正", "壤驷", "公良", "拓拔", "夹谷", "宰父", "谷梁", "晋", "楚",
                "阎", "法", "汝", "鄢", "涂", "钦", "段干", "百里", "东郭", "南门", "呼延",
                "归", "海", "羊舌", "微生", "岳", "帅", "缑", "亢", "况", "后", "有", "琴",
                "梁丘", "左丘", "东门", "西门", "商", "牟", "佘", "佴", "伯", "赏", "南宫",
                "墨", "哈", "谯", "笪", "年", "爱", "阳", "佟"};
        String[] namelist = {"伟", "伟", "芳", "伟", "秀英", "秀英", "娜", "秀英", "伟",
                "敏", "静", "丽", "静", "丽", "强", "静", "敏", "敏", "磊", "军", "洋",
                "勇", "勇", "艳", "杰", "磊", "强", "军", "杰", "娟", "艳", "涛", "涛",
                "明", "艳", "超", "勇", "娟", "杰", "秀兰", "霞", "敏", "军", "丽", "强",
                "平", "刚", "杰", "桂英", "芳", "　嘉懿", "煜城", "懿轩", "烨伟", "苑博", "伟泽",
                "熠彤", "鸿煊", "博涛", "烨霖", "烨华", "煜祺", "智宸", "正豪", "昊然", "明杰",
                "立诚", "立轩", "立辉", "峻熙", "弘文", "熠彤", "鸿煊", "烨霖", "哲瀚", "鑫鹏",
                "致远", "俊驰", "雨泽", "烨磊", "晟睿", "天佑", "文昊", "修洁", "黎昕", "远航",
                "旭尧", "鸿涛", "伟祺", "荣轩", "越泽", "浩宇", "瑾瑜", "皓轩", "擎苍", "擎宇",
                "志泽", "睿渊", "楷瑞", "子轩", "弘文", "哲瀚", "雨泽", "鑫磊", "修杰", "伟诚",
                "建辉", "晋鹏", "天磊", "绍辉", "泽洋", "明轩", "健柏", "鹏煊", "昊强", "伟宸",
                "博超", "君浩", "子骞", "明辉", "鹏涛", "炎彬", "鹤轩", "越彬", "风华", "靖琪",
                "明诚", "高格", "光华", "国源", "冠宇", "晗昱", "涵润", "翰飞", "翰海", "昊乾",
                "浩博", "和安", "弘博", "宏恺", "鸿朗", "华奥", "华灿", "嘉慕", "坚秉", "建明",
                "金鑫", "锦程", "瑾瑜", "晋鹏", "经赋", "景同", "靖琪", "君昊", "俊明", "季同",
                "开济", "凯安", "康成", "乐语", "力勤", "良哲", "理群", "茂彦", "敏博", "明达",
                "朋义", "彭泽", "鹏举", "濮存", "溥心", "璞瑜", "浦泽", "奇邃", "祺祥", "荣轩",
                "锐达", "睿慈", "绍祺", "圣杰", "晟睿", "思源", "斯年", "泰宁", "天佑", "同巍",
                "奕伟", "祺温", "文虹", "向笛", "心远", "欣德", "新翰", "兴言", "星阑", "修为",
                "旭尧", "炫明", "学真", "雪风", "雅昶", "阳曦", "烨熠", "英韶", "永贞", "咏德",
                "宇寰", "雨泽", "玉韵", "越彬", "蕴和", "哲彦", "振海", "正志", "子晋", "自怡",
                "德赫", "君平"};
        int a = (int) Math.abs(firsname.length * Math.random());
        int b = (int) Math.abs(namelist.length * Math.random());
        String name = firsname[a] + namelist[b];
        return name;
    }


}
