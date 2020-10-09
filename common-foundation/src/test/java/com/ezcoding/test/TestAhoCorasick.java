package com.ezcoding.test;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-30 17:17
 */
public class TestAhoCorasick {

    public static void main(String[] args) {
        Trie build = Trie.builder()
                .addKeyword("his")
                .addKeyword("hers")
                .addKeyword("she")
                .addKeyword("he")
                .build();
        Collection<Emit> uhershe = build.parseText("uhershe");
        System.out.println(uhershe.toString());
    }

}
