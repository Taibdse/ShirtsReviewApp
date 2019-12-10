/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

/**
 *
 * @author HOME
 */
public class StringUtils {
     public static int computeMatchingPercent(String a, String b) {
        a = a.toLowerCase().trim();
        b = b.toLowerCase().trim();
        
        int n = a.length();
        int m = b.length();

        int dp[][] = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return (int) dp[n][m] * 100 / Math.min(m, m);
    }
    
     public static String getPriceFormat(Double price){
        if(price == null) return "";
        return String.format("%,.0f", price);
     }
     
    public static String getValidString(String str){
        if(str == null || str.trim().length() == 0) return "";
        String temp = str.replaceAll(",", "");
        if(temp.trim().length() == 0) return "";
        return str;
    }
}
