package org.springblade.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author bond
 * @date 2021/11/27 22:48
 * @desc
 */
public class SimilarityUtils {
//    public static void main(String[] args) {
//        //要比较的两个字符串
//        String keywords = "2012款一汽奥迪A6三厢2.5LCVT舒适型 奥迪A6L 30FSI CVT舒适型";
//        //String keywords = "2ABBBCCCA132";
//
//
//        // keywords=removeRepeatChar(keywords.replace(" ",""));
//
//        List<String> list = new ArrayList<>();
//        list.add("2012奥迪2012款 TFSI 舒适型2.0升 涡轮增压 180马力一汽-大众奥迪;一汽奥迪奥迪A6L");
//        list.add("2012奥迪2012款 1.8 TFSI 自动舒适型1.8升 涡轮增压 160马力一汽-大众奥迪;一汽奥迪奥迪A4L");
//        list.add("2012奥迪2012款 2.0 TFSI 自动标准型null一汽-大众奥迪;一汽奥迪奥迪A4L");
//        list.add("2012奥迪2012款 2.0TFSI 进取型2.0升 涡轮增压 211马力一汽-大众奥迪;一汽奥迪奥迪Q5");
//
//
//        //String str2 = "不能在实体店买鞋子";
//        ArrayList<Integer> aa = new ArrayList<>();
//
//        for (String str : list) {
//            str = removeRepeatChar(str.replace(" ", ""));
//            //aa.add(subCount(keywords, str));
//
//            System.out.println(compare(keywords, str,true));
//        }
//        System.out.println("最大相似度=" + Collections.max(aa));
//
//        Map<Long,Integer> maxSim=new HashMap<>();
//        maxSim.put(1l,3);
//        maxSim.put(2l,6);
//        maxSim.put(3l,4);
//        maxSim.put(4l,3);
//        System.out.println( getMaxValue(maxSim));
//   }

    //字符串去重
    public static String removeRepeatChar(String s) {
        System.out.println("keywords去重前：" + s);
        //非空判断
        if (s == null) {
            return null;
        }
        StringBuilder sbd = new StringBuilder();//创建一个空的StringBuilder
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.indexOf(c) == s.lastIndexOf(c)) {//第一次出现的下标跟最后一次相等,表示当前字符没有出现重复,直接添加进StringBuilder中
                sbd.append(c);
            } else {//出现重复
                if (s.indexOf(c) == i) {//如果重复出现的字符的位置等于当先字符的索引值,即表示当前字符为重复字符出现的第一次,将其加入StringBuilder中
                    sbd.append(c);
                }
            }
        }
        System.out.println("keywords去重后：" + sbd);
        return sbd.toString();
    }


    public static int subCount(String keywords, String str) {
        int count = 0;
        for (int i = 0; i < keywords.length(); i++) {
            //System.out.println(keywords.substring(i,i+1));
            count = count + StringUtils.countMatches(str, keywords.substring(i, i + 1));
        }
        return count;
    }

    public static Long getMaxValue(Map<Long, Integer> map) {
        int value = 0;
        Long maxKey = null;
        List list = new ArrayList();
        Iterator ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            value = Integer.parseInt(entry.getValue().toString());
            list.add(entry.getValue());
            Collections.sort(list);

            if (value == Integer.parseInt(list.get(list.size() - 1).toString())) {
                maxKey = Long.valueOf(entry.getKey().toString());
                //  System.out.println(maxKey+"/"+value);
            }
        }
        return maxKey;
    }

    private static int compare(String str, String target, boolean isIgnore) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (isIgnore) {
                    if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                        temp = 0;
                    } else {
                        temp = 1;
                    }
                } else {
                    if (ch1 == ch2) {
                        temp = 0;
                    } else {
                        temp = 1;
                    }
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;

    }
}


