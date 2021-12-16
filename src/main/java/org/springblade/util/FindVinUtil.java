package org.springblade.util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FindVinUtil {



    //通过正则表达式识别字符串中的电话号码，并返回电话号码的集合
    public static List<String> readTelNumByPattern(String str) {

        //电话号码集合
        List<String> telNo = new ArrayList<String>();

        if(null == str || str.length() <= 0) {
            return telNo;
        }

        //识别vin号码的正则表达式
         Pattern vinPattern = Pattern.compile("[A-HJ-NPR-Z\\d]{17}");

        Matcher vinMatcher = vinPattern.matcher(str);

        while (vinMatcher.find()) {
            telNo.add(vinMatcher.group());
        }
        for (String no : telNo) {
            System.out.println("识别出vin号：" + no);
        }

        return telNo;
    }
    //通过正则表达式识别字符串中的电话号码，并返回电话号码的集合
    public static String readVinByPattern(String str) {
        String vin=null;
        //识别vin号码的正则表达式
        Pattern vinPattern = Pattern.compile("[A-HJ-NPR-Z\\d]{17}");

        Matcher vinMatcher = vinPattern.matcher(str);

        while (vinMatcher.find()) {
           return vinMatcher.group();
        }


        return vin;
    }

//    public static void main(String[] args) {
//        String beforeStr = "看LFV5A24G7D3131607";
//        System.out.println("处理前的字符串：" + beforeStr);
//        System.out.println(readVinByPattern(beforeStr));
//    }
}

