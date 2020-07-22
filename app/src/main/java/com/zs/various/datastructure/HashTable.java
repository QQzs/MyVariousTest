package com.zs.various.datastructure;

/**
 * @Author: zhangshuai
 * @CreateDate: 2020/7/20 2:21 PM
 * @Description:
 */
public class HashTable<K , V> {

    /**
     * 散列表默认长度 8
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * 装载因子
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * 初始化散列表数组
     */
    private Entry<K, V>[] table;

    /**
     * 实际数据数量
     */
    private int size = 0;

    /**
     * 散列表索引数量
     */
    private int useNum = 0;

    public HashTable() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    /**
     * 添加一个数据
     * @param key
     * @param value
     */
    public void put(K key , V value) {
        int index = hash(key);
        // 位置未被引用，创建哨兵节点
        if (table[index] == null) {
            table[index] = new Entry<>(null, null, null);
        }

        Entry<K, V> tmp = table[index];
        // 新增节点
        if (tmp.next == null) {
            tmp.next = new Entry<>(key, value, null);
            size++;
            useNum++;
            if (useNum >= table.length * LOAD_FACTOR) {
                // 动态扩容
                resize();
            }
        }
        // 使用链表法，解决散列冲突
        else {
            do {
                tmp = tmp.next;
                // key相同，覆盖旧的数据
                if (tmp.key == key) {
                    tmp.value = value;
                    return;
                }
            } while (tmp.next != null);

            Entry<K, V> temp = table[index].next;
            table[index].next = new Entry<>(key, value, temp);
            size++;
        }
    }

    /**
     * 删除一个数据
     *
     * @param key
     */
    public void remove(K key) {
        int index = hash(key);
        Entry e = table[index];
        if (e == null || e.next == null) {
            return;
        }
        Entry pre;
        Entry<K, V> headNode = table[index];
        do {
            pre = e;
            e = e.next;
            if (key == e.key) {
                pre.next = e.next;
                size--;
                if (headNode.next == null) useNum--;
                return;
            }
        } while (e.next != null);
    }

    /**
     * 获取一个数据
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> e = table[index];
        if (e == null || e.next == null) {
            return null;
        }
        while (e.next != null) {
            e = e.next;
            if (key == e.key) {
                return e.value;
            }
        }
        return null;
    }

    /**
     * 散列函数
     * 参考hashmap散列函数
     *
     * @param key
     * @return
     */
    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : ((h = key.hashCode()) ^ (h >>> 16));
    }

    /**
     * 扩容
     */
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[table.length * 2];
        useNum = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null || oldTable[i].next == null) {
                continue;
            }
            Entry<K, V> e = oldTable[i];
            while (e.next != null) {
                e = e.next;
                int index = hash(e.key);
                if (table[index] == null) {
                    useNum++;
                    // 创建哨兵节点
                    table[index] = new Entry<>(null, null, null);
                }
                table[index].next = new Entry<>(e.key, e.value, table[index].next);
            }
        }
    }

}
