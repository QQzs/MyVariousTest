package com.zs.various.datastructure;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/20 2:45 PM
 * @Description: 单项链表
 */
public class Entry<K , V> {

    /**
     * 键值
     */
    public K key;

    /**
     * 数据
     */
    public V value;

    /**
     * 下一节点指针
     */
    public Entry<K , V> next;

    public Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
