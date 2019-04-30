package com.zs.various.util.voice;

import java.util.*;

/**
 * CosineSimilarity
 */
public class CosineSimilarity {

    private final SimpleTextTokenizer tokenizer;

    public CosineSimilarity(SimpleTextTokenizer token) {

        tokenizer = token;

    }

    public Double compareSentence(String originStr, String resultStr) {

        if (originStr.length() == 0) {

            return 1.0;

        }

        List<String> words1 = tokenizer.expandString(originStr);
        List<String> words2 = tokenizer.expandString(resultStr);

        if (words1.size() == 0) {

            return 1.0;

        }

        TreeSet<String> tSet = tokenizer.expandSentence(words1, words2);

        Iterator<String> itSet = tSet.iterator();

        Integer i = 0;
        Map<String, Integer> sMap = new HashMap<String, Integer>();
        while (itSet.hasNext()) {
            String key = itSet.next().toString();
            sMap.put(key, i);
            i++;
        }

        int[] words_onehot1 = new int[tSet.size()];
        int[] words_onehot2 = new int[tSet.size()];

        for (String word : words1) {

            words_onehot1[sMap.get(word)]++;

        }

        for (String word : words2) {

            words_onehot2[sMap.get(word)]++;

        }

        double sum = 0;
        double sq1 = 0;
        double sq2 = 0;

        for (int j = 0; j < words_onehot1.length; j++) {

            sum += words_onehot1[j] * words_onehot2[j];
            sq1 += words_onehot1[j] * words_onehot1[j];
            sq2 += words_onehot2[j] * words_onehot2[j];

        }
        double value = sum / (Math.sqrt(sq1) * Math.sqrt(sq2));
        double result = (double) Math.round(value * 100) / 100;
        return result;
    }

}