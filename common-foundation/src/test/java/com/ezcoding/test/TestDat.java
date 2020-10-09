package com.ezcoding.test;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import java.util.List;
import java.util.TreeMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-28 21:50
 */
public class TestDat {

    public static void main(String[] args) {
        // Collect test data set
        TreeMap<String, String> map = new TreeMap<String, String>();
        String[] keyArray = new String[]
                {
                        "hers",
                        "his",
                        "she",
                        "he"
                };
        for (String key : keyArray) {
            map.put(key, key);
        }
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);
        // Test it
        final String text = "uhershe";
        List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = acdat.parseText(text);

        System.out.println(wordList);

//        acdat.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>() {
//            @Override
//            public void hit(int begin, int end, String value) {
//                System.out.printf("[%d:%d]=%s\n", begin, end, value);
//            }
//        });
    }

}
