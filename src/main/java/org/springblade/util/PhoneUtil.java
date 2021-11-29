package org.springblade.util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {




        //通过正则表达式识别字符串中的电话号码，并返回电话号码的集合
        public static List<String> readTelNumByPattern(String str) {

            //电话号码集合
            List<String> telNo = new ArrayList<String>();

            if(null == str || str.length() <= 0) {
                return telNo;
            }

            //识别手机号码的正则表达式
            Pattern cellPhonePattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
            //识别座机号码的正则表达式
            Pattern landLinePattern = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
            Matcher cellPhoneMatcher = cellPhonePattern.matcher(str);
            Matcher landLineMatcher = landLinePattern.matcher(str);

            while (cellPhoneMatcher.find()) {
                telNo.add(cellPhoneMatcher.group());
            }

            while (landLineMatcher.find()) {
                telNo.add(landLineMatcher.group());
            }

            for (String no : telNo) {
                System.out.println("识别出电话号码：" + no);
            }

            return telNo;
        }

        //讲电话号码处理为纯数字
        public static String doWithTelNum(String strTelNo) {
            StringBuffer sbrDailNo = new StringBuffer();

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(strTelNo);
            while (m.find()) {
                sbrDailNo.append(m.group());
            }

            return sbrDailNo.toString();
        }

        //为电话号码加上样式和事件
        public static String doWithStyle(String strTelString) {
            StringBuffer stringBuffer = new StringBuffer();

            if(null != strTelString && strTelString.length()>0) {
                String styleTelNum = doWithTelNum(strTelString);
                stringBuffer.append("<a style=\"color: red\" οnclick=\"doSomething('");
                stringBuffer.append(styleTelNum);
                stringBuffer.append("')\">");
                stringBuffer.append(styleTelNum);
                stringBuffer.append("</a>");
            }

            return stringBuffer.toString();
        }

        //将电话号码替换为加上样式之后的字符串
        public static String doWithReplace(String str) {
            List<String> telNum = readTelNumByPattern(str);

            for(String strTelNum :telNum){
                str = str.replace(strTelNum, doWithStyle(strTelNum));
            }

            return str;
        }

//        public static void main(String[] args) {
//            String beforeStr = "fasd13298098789dfadfads18728973798fsdfad1376767898fdadfa0775-6678111gyft029-89876567rdr";
//            System.out.println("处理前的字符串：" + beforeStr);
//            String afterStr = doWithReplace(beforeStr);
//            System.out.println("处理前的字符串：" + afterStr);
//        }
    }

