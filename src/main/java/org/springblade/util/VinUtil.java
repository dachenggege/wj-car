package org.springblade.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bond
 * @date 2021/11/26 22:52
 * @desc
 */
import java.util.HashMap;
import java.util.Map;

public class VinUtil {
    /**
     * 字符权重表
     */
    private static final Map<Character, Integer> CHAR_WEIGHTS = new HashMap<>();
    /**
     * 位置权重表
     */
    private static final int[] POS_WEIGHTS = new int[]{8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};


    static {
        for (int i = 0; i < 10; i++) {
            CHAR_WEIGHTS.put(String.valueOf(i).charAt(0), i);
        }
        CHAR_WEIGHTS.put('A', 1);
        CHAR_WEIGHTS.put('B', 2);
        CHAR_WEIGHTS.put('C', 3);
        CHAR_WEIGHTS.put('D', 4);
        CHAR_WEIGHTS.put('E', 5);
        CHAR_WEIGHTS.put('F', 6);
        CHAR_WEIGHTS.put('G', 7);
        CHAR_WEIGHTS.put('H', 8);
        CHAR_WEIGHTS.put('J', 1);
        CHAR_WEIGHTS.put('K', 2);
        CHAR_WEIGHTS.put('L', 3);
        CHAR_WEIGHTS.put('M', 4);
        CHAR_WEIGHTS.put('N', 5);
        CHAR_WEIGHTS.put('P', 7);
        CHAR_WEIGHTS.put('R', 9);
        CHAR_WEIGHTS.put('S', 2);
        CHAR_WEIGHTS.put('T', 3);
        CHAR_WEIGHTS.put('U', 4);
        CHAR_WEIGHTS.put('V', 5);
        CHAR_WEIGHTS.put('W', 6);
        CHAR_WEIGHTS.put('X', 7);
        CHAR_WEIGHTS.put('Y', 8);
        CHAR_WEIGHTS.put('Z', 9);
    }


    public static boolean isValidVin(String vin) {
        if (null == vin) {
            return false;
        } else if (vin.trim().length() == 17) {
            vin = vin.toUpperCase();
            int sum = 0;
            int checkSum = 0;
            for (int i = 0; i < vin.length(); i++) {
                char code = vin.charAt(i);
                Integer cw = CHAR_WEIGHTS.get(code);
                if (cw == null) {
                    return false;
                }
                int pw = POS_WEIGHTS[i];
                sum += cw * pw;
                if (i == 8) {
                    // 获取校验位的值
                    if (code == 'X') {
                        checkSum = 10;
                    } else if (code >= '0' && code <= '9') {
                        checkSum = Integer.valueOf(String.valueOf(code));
                    } else {
                        return false;
                    }
                }
            }
            return checkSum == sum % 11;
        }
        return false;
    }

//
//    public static void main(String[] args) {
//        System.out.println(isValidVin("UU6JA69691D713820"));
//
//        System.out.println(isValidVin("LFV3A21K7D4262398"));
//
//        System.out.println(isValidVin("LFV3A23C793062656"));
//
//        System.out.println(isValidVin("LSGDC82C11S10203O"));
//
//        System.out.println(isValidVin("LSGDC82C11S102030"));
//
//        System.out.println(isValidVin("LSGUD84X2BE041557"));
//
//        System.out.println(isValidVin("WDDBF4CB2EJ143048"));
//
//        System.out.println(isValidVin("1LFP3ACCXD1D99699"));
//
//    }
}
