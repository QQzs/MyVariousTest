package com.zs.various.util.voice;

import android.content.Context;
import android.text.TextUtils;

import com.zs.various.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * SimpleTextTokenizer
 */
public class SimpleTextTokenizer {

    private final List<String> filterWords = new ArrayList<String>();
    private  Map<String, String> mMapData;

    public SimpleTextTokenizer() {

        String pathname = "/resources/onomatopoeia.txt";

        try {
            InputStream is = this.getClass().getResourceAsStream(pathname);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            // 网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                line = line.toLowerCase();
                filterWords.add(line);
            }

            br.close();

        } catch (Exception e) {

        } finally {

        }

    }

    public SimpleTextTokenizer(Context context) {

        mMapData = new HashMap<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.cmudict_mini_new);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));;
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                int index = line.indexOf(" ");
                mMapData.put(line.substring(0,index) , line.substring(index + 1));
            }
            br.close();

        } catch (Exception e) {

        } finally {

        }

    }

    public List<String> expandString(String text) {

        text = text.toLowerCase();

        List<String> words = new ArrayList<>();
        String[] tokens = text.split("[.,?:!;()\" ]");
        for (int i = 0; i < tokens.length; i++) {
            String item = tokens[i];
            if (!TextUtils.isEmpty(item)){
                words.add(mMapData.get(item));
            }
        }
        List<String> wordList = sentenceToWords(words);
        return wordList;
    }

    public List<String> expand(String text) {

        text = text.toLowerCase();

        for (String word : filterWords) {

            text = text.replace(word, "");

        }

        text = text.replace('’', '\'');
        text = text.replace('‘', ' ');
        text = text.replace("'", "");
        text = text.replace('”', ' ');
        text = text.replace('“', ' ');
        text = text.replace('"', ' ');
        text = text.replace('»', ' ');
        text = text.replace('«', ' ');
        text = text.replace('–', '-');
        text = text.replace('—', ' ');
        text = text.replace('…', ' ');

        text = text.replace(" - ", " ");
        text = text.replaceAll("[/_*%]", " ");
        // text = text.toLowerCase();

        String[] tokens = text.split("[.,?:!;()]");

        List<String> splitString = Arrays.asList(tokens);
        List<String> wordList = sentenceToWords(splitString);

        return wordList;
    }

    public TreeSet<String> expandSentence(List<String> words1, List<String> words2) {

        TreeSet<String> tSet = new TreeSet<String>();

        for (String word : words1) {

            tSet.add(word);

        }

        for (String word : words2) {

            tSet.add(word);

        }

        return tSet;
    }

    protected List<String> sentenceToWords(List<String> sentenceTranscript) {
        ArrayList<String> transcript = new ArrayList<String>();
        for (String sentence : sentenceTranscript) {
            if (!TextUtils.isEmpty(sentence)){
                String[] words = sentence.split("\\s+");
                for (String word : words) {
                    if (word.length() > 0)
                        transcript.add(word);
                }
            }
        }
        return transcript;
    }

}