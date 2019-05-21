package com.zs.various.util.voice;

import java.util.List;

/**
 * LevenshteinDistance
 */
public class LevenshteinDistance {

    private final SimpleTextTokenizer tokenizer;

    public LevenshteinDistance(SimpleTextTokenizer token) {

        tokenizer = token;

    }

    public double comparaSentence(String originStr, String resultStr) {

        if (originStr.length() == 0) {

            return 1.0;

        }

        List<String> words1 = tokenizer.expand(originStr);
        List<String> words2 = tokenizer.expand(resultStr);

        if (words1.size() == 0) {

            return 1.0;

        }

        int maxsize = Math.max(words1.size(), words2.size());

        int distance = 0;

        if (maxsize > 1) {

            distance = compareWords(words1, words2);

        } else {

            distance = compare(originStr, resultStr);

        }

        return (1.0 - ((double) distance / (double) maxsize));

    }

    private int compareWords(List<String> words1, List<String> words2) {

        int maxtri[][];
        int n = words1.size();
        int m = words2.size();
        String str1;
        String str2;
        int i;
        int j;
        int temp;

        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        maxtri = new int[n + 1][m + 1];

        // 初始化第一列
        for (i = 0; i <= n; i++) {
            maxtri[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            maxtri[0][j] = j;
        }

        for (i = 1; i <= n; i++) {
            // 遍历str
            str1 = words1.get(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                str2 = words2.get(j - 1);
                if (str1.equals(str2)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                maxtri[i][j] = min(maxtri[i - 1][j] + 1, maxtri[i][j - 1] + 1, maxtri[i - 1][j - 1] + temp);
            }
        }

        return maxtri[n][m];

    }

    private static int compare(String str, String target) {

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
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * 获取最小的值
     */
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

}