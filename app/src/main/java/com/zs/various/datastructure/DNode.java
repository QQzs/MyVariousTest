package com.zs.various.datastructure;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/22 7:25 PM
 * @Description: 双向链接
 */
public class DNode<K , V> {
    /**
     * 键值
     */
    public K key;

    /**
     * 数据
     */
    public V value;

    /**
     * 前驱指针
     */
    public DNode<K, V> prev;

    /**
     * 后继指针
     */
    public DNode<K, V> next;

    DNode() {

    }

    DNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
