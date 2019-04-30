package com.zs.various.util.voice;

import java.util.List;

/**
 * EasyCompare
 */
public class EasyCompare {

    private final SimpleTextTokenizer tokenizer;

    public EasyCompare(SimpleTextTokenizer token) {

        tokenizer = token;

    }

    public double compareSentence(String originStr, String resultStr) {

        if (originStr.length() == 0) {

            return 1.0;

        }

        resultStr = resultStr.toLowerCase();

        List<String> origin_words = tokenizer.expand(originStr);

        if (origin_words.size() == 0) {

            return 1.0;
        }

        double count = 0;

        for (String word : origin_words) {

            if (resultStr.contains(word)) {

                count += 1;

            }

        }

        double final_result = count / origin_words.size();

        return final_result;

    }

}