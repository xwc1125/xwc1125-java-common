package com.xwc1125.common.util.mobile;

import com.xwc1125.common.util.string.StringUtils;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: RegMobile
 * </p>
 * <p>
 * Description: 电话号码去除IP号、+86;是否是手机号
 * </p>
 * <p>
 * 对电话号码进行过滤，去掉IP号("1790", "1791", "1793", "1795","1796", "1797", "1799")，+86等前缀
 * </p>
 *
 * @author xwc1125
 * @date 2015年11月16日下午2:30:04
 */
public class MobileUtils {

    private static Logger log = LoggerFactory.getLogger(MobileUtils.class);
    private static final String[] IPPFXS4 = {"1790", "1791", "1793", "1795",
            "1796", "1797", "1799"};
    private static final String[] IPPFXS5 = {"12583", "12593", "12589",
            "12520", "10193", "11808"};
    private static final String[] IPPFXS6 = {"118321"};

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getShowNumber("15201062"));
        System.out.println(getMobileType("17091951814"));

        // boolean flag = isMobileNo("18330675309");
        // System.out.println(flag);
        // 测试数据
        // String telNum0 = "8618611503575";
        // System.out.println("before trim telNum=" + telNum0);
        // telNum0 = trimTelPhone(telNum0);
        // System.out.println("trimTelPhone telNum=" + telNum0);
        //
        // String telNum = "+8618611503575";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "008618611503575";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "17951+8618211503458";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "1795818211503458";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "1252015611503575";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "11832115611503575";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
        //
        // telNum = "118321+8615611503575";
        // System.out.println("before trim telNum=" + telNum);
        // telNum = trimTelPhone(telNum);
        // System.out.println("trimTelPhone telNum=" + telNum);
    }

    /**
     * 消除电话号码中 可能含有的 IP号码、+86、0086等前缀
     *
     * @param telNum
     * @return
     */
    public static String trimTelPhone(String telNum) {
        log.info("telNum=" + telNum);
        if (telNum == null || "".equals(telNum) || telNum.length() < 11) {
            System.out.println("【MobileUtils】trimTelPhone is null");
            return null;
        }

        String ippfx6 = substring(telNum, 0, 6);
        String ippfx5 = substring(telNum, 0, 5);
        String ippfx4 = substring(telNum, 0, 4);

        if (telNum.length() > 7
                && (substring(telNum, 5, 1).equals("0")
                || substring(telNum, 5, 1).equals("1")
                || substring(telNum, 5, 3).equals("400") || substring(
                telNum, 5, 3).equals("+86"))
                && (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4))) {
            telNum = substring(telNum, 5);
        } else if (telNum.length() > 8
                && (substring(telNum, 6, 1).equals("0")
                || substring(telNum, 6, 1).equals("1")
                || substring(telNum, 6, 3).equals("400") || substring(
                telNum, 6, 3).equals("+86"))
                && inArray(ippfx6, IPPFXS6)) {
            telNum = substring(telNum, 6);
        }
        // remove ip dial

        telNum = telNum.replace("-", "");
        telNum = telNum.replace(" ", "");

        if (substring(telNum, 0, 4).equals("0086")) {
            telNum = substring(telNum, 4);
        } else if (substring(telNum, 0, 3).equals("+86")) {
            telNum = substring(telNum, 3);
        } else if (substring(telNum, 0, 2).equals("86")) {
            telNum = substring(telNum, 2);
        } else if (substring(telNum, 0, 5).equals("00186")) {
            telNum = substring(telNum, 5);
        }
        return telNum;
    }

    /**
     * 截取字符串
     *
     * @param s
     * @param from
     * @return
     */
    protected static String substring(String s, int from) {
        try {
            return s.substring(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String substring(String s, int from, int len) {
        try {
            return s.substring(from, from + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断一个字符串是否在一个字符串数组中
     *
     * @param target
     * @param arr
     * @return
     */
    protected static boolean inArray(String target, String[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (target == null) {
            return false;
        }
        for (String s : arr) {
            if (target.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * <p>
     * Title: isMobileNo
     * </p>
     * <p>
     * Description: 判断是否为手机号
     * </p>
     * <p>
     * 电信：133、153、177、180、181、189、（1349卫通）、1700（虚拟运营商电信号段）、199<br>
     *
     * 移动：134、135、136、137、138、139、
     * 150、151、152、157（TD）、158、159、
     * 182、183、184、187、
     * 178、188、147（数据卡号段）、1705（虚拟运营商移动号段）、198
     *
     * 联通：130、131、132、145(数据卡号段)、155、156、176、185、186、1709（虚拟运营商联通号段）、166<br>
     * </p>
     *
     * @param mobiles
     * @return
     *
     * @author xwc1125
     * @date 2015-7-13下午1:52:47
     */
//    public static boolean isMobileNo(String mobiles) {
//        if (StringUtils.isEmpty(mobiles)) {
//            return false;
//        }
//        mobiles = MobileUtils.trimTelPhone(mobiles);
//        Pattern p = Pattern
//                .compile("^(0|86|17951)?(13[0-9]" +
//                        "|14[57]" +
//                        "|15[012356789]" +
//                        "|16[6]" +
//                        "|17[0-9]|170[09]"+
//                        "|18[0-9]" +
//                        "|19[89])" +
//                        "[0-9]{8}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
//    }

    /**
     * @param @param  mobile
     *                <p>
     *                电信：133、153、177、180、181、189、（1349卫通）、1700（虚拟运营商电信号段）、199<br>
     *                <p>
     *                移动：134、135、136、137、138、139、
     *                150、151、152、157（TD）、158、159、
     *                182、183、184、187、
     *                178、188、147（数据卡号段）、1705（虚拟运营商移动号段）、198
     *                <p>
     *                联通：130、131、132、145(数据卡号段)、155、156、176、185、186、1709（虚拟运营商联通号段）、166<br>
     * @param @return
     * @return int
     * @Title getMobileType
     * @Description 获取手机号的类型
     * @author xwc1125
     * @date 2016年1月8日 下午12:49:05
     */
    public static TELOPR_TYPE getMobileType(String mobile) {
        TELOPR_TYPE mobileTelOprType = TELOPR_TYPE.UNKNOW;
//        if (!isMobileNo(mobile)) {
//            return mobileTelOprType;
//        }
        mobile = trimTelPhone(mobile);
        // 联通
        List<?> chinaUnicom1 = Arrays.asList("130", "131",
                "132", "155", "156", "176", "185", "186", "145", "166");
        List<?> chinaUnicom2 = Arrays.asList("1707", "1708",
                "1709", "1750");
        // 移动
        List<?> chinaMobile1 = Arrays.asList("135", "136",
                "137", "138", "139", "150", "151", "152", "157", "158", "159",
                "178", "182", "183", "184", "187", "188", "147", "198");
        List<?> chinaMobile2 = Arrays.asList("1340", "1341",
                "1342", "1343", "1344", "1345", "1346", "1347", "1348", "1703",
                "1705", "1706");
        // 电信
        // List<?> chinaNet1 = Arrays.asList(new String[] { "133", "153", "177",
        // "180", "181", "189" });
        // List<?> chinaNet2 = Arrays.asList(new String[] { "1349", "1700" });

        boolean bolChinaUnicom1 = (chinaUnicom1
                .contains(mobile.substring(0, 3)));
        boolean bolChinaUnicom2 = (chinaUnicom2
                .contains(mobile.substring(0, 4)));
        boolean bolChinaMobile1 = (chinaMobile1
                .contains(mobile.substring(0, 3)));
        boolean bolChinaMobile2 = (chinaMobile2
                .contains(mobile.substring(0, 4)));
        if (bolChinaUnicom1 || bolChinaUnicom2) {
            mobileTelOprType = TELOPR_TYPE.CUCC;// 联通
        } else if (bolChinaMobile1 || bolChinaMobile2) {
            mobileTelOprType = TELOPR_TYPE.CMCC;
        } // 移动
        else {
            mobileTelOprType = TELOPR_TYPE.CTC;
        }
        return mobileTelOprType; // 其他为电信
    }

    /**
     * @param @param  mobile【只有前三后四被显示，中间用*代替】
     * @param @return
     * @return String
     * @Title getShowNumber
     * @Description 获取半显示手机号
     * @author xwc1125
     * @date 2016年3月6日 上午9:19:35
     */
    public static String getShowNumber(String mobile) {
        String result = null;
        if (StringUtils.isEmpty(mobile)) {
            return result;
        }
        int length = mobile.length();
        if (length < 8) {
            return mobile;
        }
        String pre_mobile = mobile.substring(0, 3);
        String post_mobile = mobile.substring(length - 4);
        String mid_mobile = "";
        for (int i = 0; i < length - 7; i++) {
            mid_mobile += "*";
        }
        return pre_mobile + mid_mobile + post_mobile;
    }

    /**
     * @param @param  is IMSI=MCC+MNC+MSIN; MCC（Mobile Country
     *                Code，移动国家码）：MCC的资源由国际电信联盟（ITU）在全世界范围内统一分配和管理
     *                ，唯一识别移动用户所属的国家，共3位，中国为460。<br>
     *                MNC（Mobile Network Code，移动网络号码）：用于识别移动用户所归属的移动通信网，2~3位。<br>
     *                在同一个国家内，如果有多个PLMN（Public Land Mobile Network，公共陆地移动网
     *                ，一般某个国家的一个运营商对应一个PLMN），可以通过MNC来进行区别 ，即每一个PLMN都要分配唯一的MNC 。<br>
     *                中国移动系统使用00、02、04、07，<br>
     *                中国联通GSM系统使用01 、06，<br>
     *                中国电信CDMA系统使用03、05、电信4G使用11，<br>
     *                中国铁通系统使用20。<br>
     *                MSIN（Mobile Subscriber Identification
     *                Number，移动用户识别号码）：用以识别某一移动通信网中的移动用户。共有10位，其结构如下： EF+M0M1M2M3+ABCD<br>
     *                其中，EF由运营商分配；M0M1M2M3和MDN（Mobile Directory
     *                Number，移动用户号码簿号码）中的H0H1H2H3可存在对应关系；ABCD：四位，自由分配。<br>
     * @param @return
     * @return TELOPR_TYPE
     * @Title getTelOprTypeByImsi
     * @Description 通过解析Imsi获取当前的手机号是什么运营商
     * @author xwc1125
     * @date 2016年3月16日 下午4:28:50
     */
    public static TELOPR_TYPE getTelOprTypeByImsi(String is) {
        TELOPR_TYPE telOprType = TELOPR_TYPE.UNKNOW;
        if (StringUtils.isNotEmpty(is)) {
            // 如果全部是0，那么将不知道运营商的信息
            String imsi_1 = is.replaceAll("0", "");
            if (StringUtils.isEmpty(imsi_1)) {
                return telOprType;
            }
            if (is.length() != 15) {
                return telOprType;
            }
            // 获取Imsi中的mnc的两位
            String mnc = is.substring(3, 5);
            if (mnc.equals("00") || mnc.equals("02") || mnc.equals("04")
                    || mnc.equals("07")) {
                telOprType = TELOPR_TYPE.CMCC;
            } else if (mnc.equals("01") || mnc.equals("06")) {
                telOprType = TELOPR_TYPE.CUCC;
            } else if (mnc.equals("03") || mnc.equals("05") || mnc.equals("11")) {
                telOprType = TELOPR_TYPE.CTC;
            }
        }
        System.out.println("【Imsi运营商】" + telOprType);
        return telOprType;
    }

    /**
     * @param @param  iccid
     *                <p>
     *                ICCID：Integrate circuit card identity 集成电路卡识别码（固化在SIM卡中） <br>
     *                ICCID为IC卡的唯一识别号码，共有20位数字组成，其编码格式为：XXXXXX 0MFSS YYGXX XXXXX。 <br>
     *                分别介绍如下： 前六位运营商代码： <br>
     *                中国移动的为：898600；898602 ， <br>
     *                中国联通的为：898601、898606、898609， <br>
     *                中国电信898603 <br>
     *                <p>
     *                中国移动：898600MFSSYYGXXXXXXP<br>
     *                89： 国际编号89<br>
     *                86： 国家编号，86：中国<br>
     *                00： 运营商编号，00：中国移动<br>
     *                M： 号段，对应用户号码前3位<br>
     *                0：159 1：158 2：150<br>
     *                3：151 4-9：134-139 A：157<br>
     *                B：188 C：152 D：147 E：187<br>
     *                F： 用户号码第4位<br>
     *                SS： 省编号<br>
     *                北京01 天津02 河北03 山西04 内蒙古05 辽宁06 吉林07 黑龙江08 上海09 江苏10 浙江11 安徽12 福建13
     *                江西14 山东15 河南16 湖北17 湖南18 广东19 广西20 海南21 四川22 贵州23 云南24 西藏25 陕西26
     *                甘肃27 青海28 宁夏29 新疆30 重庆31<br>
     *                YY： 编制ICCID时年号的后两位<br>
     *                G： SIM卡供应商代码<br>
     *                0：雅斯拓 1：GEMPLUS 2：武汉天喻 3：江西捷德 4：珠海东信和平 5：大唐微电子通 6：航天九州通 7：北京握奇
     *                8：东方英卡 9：北京华虹 A ：上海柯斯<br>
     *                X…X： 用户识别码<br>
     *                P： 校验位<br>
     *                中国联通：898601YY8SSXXXXXXXXP<br>
     *                89： 国际编号89<br>
     *                86： 国家编号，86：中国<br>
     *                01： 运营商编号，01：中国联通<br>
     *                YY： 编制ICCID时年号的后两位<br>
     *                8： 中国联通ICCID默认此为为8<br>
     *                SS：2位省份编码<br>
     *                10内蒙古 11北京 13天津 17山东 18河北 19山西 30安徽 31上海 34江苏 36浙江 38福建 50海南 51广东
     *                59广西 70青海 71湖北 74湖南 75江西 76河南 79西藏 81四川 83重庆 84陕西 85贵州 86云南 87甘肃
     *                88宁夏 89新疆 90吉林 91辽宁 97黑龙江<br>
     *                X…X： 卡商生产的顺序编码<br>
     *                P： 校验位<br>
     *                中国电信：898603MYYH HHXXX XXXXP<br>
     *                89：国际编号<br>
     *                86：国家编码，86：中国<br>
     *                03：中国电信运营商PHS网络标识，固定不变<br>
     *                M ：保留位，固定为0<br>
     *                YY：编制ICCID时的年号（取后两位），如‘05’代表2005年
     *                HHH：本地网地区代码，位数不够前补零。如上海区号为021，则HHH为
     *                '021’；长沙区号为0731，则HHH为‘731’，测试卡代码为001 XXXXXXXP：8位流水号，建议前2位作为批次号<br>
     * @param @return
     * @return TELOPR_TYPE
     * @Title getTelOprTypeByIccid
     * @Description 通过Iccid获取运营商的信息
     * @author xwc1125
     * @date 2016年5月26日 上午11:18:05
     */
    public static TELOPR_TYPE getTelOprTypeByIccid(String iccid) {
        TELOPR_TYPE telOprType = TELOPR_TYPE.UNKNOW;
        if (StringUtils.isEmpty(iccid)) {
            return telOprType;
        }
        if (iccid.length() != 20) {
            return telOprType;
        }
        // 前六位运营商代码
        String iccid_prefix = iccid.substring(0, 6);
        if (iccid_prefix.equals("898600") || iccid_prefix.equals("898602")) {
            telOprType = TELOPR_TYPE.CMCC;
        } else if (iccid_prefix.equals("898601")
                || iccid_prefix.equals("898606")
                || iccid_prefix.equals("898609")) {
            telOprType = TELOPR_TYPE.CUCC;
        } else if (iccid_prefix.equals("898603")) {
            telOprType = TELOPR_TYPE.CTC;
        }
        return telOprType;
    }

    /**
     * @param @param  is IMSI=MCC+MNC+MSIN; MCC（Mobile Country
     *                Code，移动国家码）：MCC的资源由国际电信联盟（ITU）在全世界范围内统一分配和管理
     *                ，唯一识别移动用户所属的国家，共3位，中国为460。<br>
     *                MNC（Mobile Network Code，移动网络号码）：用于识别移动用户所归属的移动通信网，2~3位。<br>
     *                在同一个国家内，如果有多个PLMN（Public Land Mobile Network，公共陆地移动网
     *                ，一般某个国家的一个运营商对应一个PLMN），可以通过MNC来进行区别 ，即每一个PLMN都要分配唯一的MNC
     *                。中国移动系统使用00、02、04、07，中国联通GSM系统使用01
     *                、06，中国电信CDMA系统使用03、05、电信4G使用11，中国铁通系统使用20。<br>
     *                MSIN（Mobile Subscriber Identification
     *                Number，移动用户识别号码）：用以识别某一移动通信网中的移动用户。共有10位，其结构如下： EF+M0M1M2M3+ABCD<br>
     *                其中，EF由运营商分配；M0M1M2M3和MDN（Mobile Directory
     *                Number，移动用户号码簿号码）中的H0H1H2H3可存在对应关系；ABCD：四位，自由分配。<br>
     * @param @return
     * @return boolean
     * @Title isCNMobileImsi
     * @Description TODO(describe the methods)
     * @author xwc1125
     * @date 2016年4月19日 上午9:42:47
     */
    public static boolean isCNMobileImsi(String is) {
        boolean flag = false;
        if (StringUtils.isNotEmpty(is)) {
            // 如果全部是0，那么将不知道运营商的信息
            String imsi_1 = is.replaceAll("0", "");
            if (StringUtils.isEmpty(imsi_1)) {
                return flag;
            }
            if (is.length() != 15) {
                return flag;
            }
            // 如果不等于460表示不是中国的
            if (!is.startsWith("460")) {
                return flag;
            }
            // 获取Imsi中的mnc的两位
            String mnc = is.substring(3, 5);
            if (!mnc.equals("00") && !mnc.equals("02") && !mnc.equals("04")
                    && !mnc.equals("07") && !mnc.equals("01")
                    && !mnc.equals("06") && !mnc.equals("03")
                    && !mnc.equals("05") && !mnc.equals("11")) {
                return flag;
            } else {
                flag = true;
            }
        }
        return flag;
    }

}
