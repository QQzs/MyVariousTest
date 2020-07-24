package com.zs.various.datastructure;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/20 2:45 PM
 * @Description: 单项链表
 */
public class Node<K , V> {

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
    public Node<K , V> next;

    public Node(K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
