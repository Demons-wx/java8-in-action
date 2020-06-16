package online.wangxuan.algo;

import java.util.Arrays;
import java.util.List;

/**
 * Trie树字符串查找
 *
 * Trie 树是非常耗内存的，用的是一种空间换时间的思路
 * @author xwangr
 * @date 2020/4/10
 */
public class Trie {

    private static TrieNode root = new TrieNode('/');

    public static void insert(String text) {
        TrieNode p = root;
        char[] chars = text.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (p.children[index] == null) {
                p.children[index] = new TrieNode(chars[i]);
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    public static boolean find(String pattern) {
        TrieNode p = root;
        char[] chars = pattern.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (p.children[index] == null) return false;
            p = p.children[index];
        }
        return p.isEndingChar;
    }

    public static class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;
        public TrieNode(char data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "watermelon", "pear");
        list.forEach(Trie::insert);
        System.out.println(find("PEA"));
    }
}
