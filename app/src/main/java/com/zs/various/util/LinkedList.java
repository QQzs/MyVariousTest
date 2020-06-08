package com.zs.various.util;

public class LinkedList {


    class Node{
        int index;
        Node next;

        public Node(int index , Node node){
            this.index = index;
            this.next = node;
        }
    }

    /**
     * 迭代法 单向链表反转
     * @param node
     */
    public void reverse1(Node node){
        Node prew = null;
        Node now = node;
        while(now != null){
            Node next = now.next;
            now.next = prew;
            prew = now;
            now = next;
        }
    }

    /**
     * 递归法 单向链表反转
     * @param node
     * @param prew
     * @return
     */
    public Node reverse2(Node node , Node prew){
        if (node.next == null){
            node.next = prew;
            return node;
        }else{
            Node re = reverse2(node.next , node);
            node.next = prew;
            return re;
        }
    }


//    class Node<E> {
//
//        E item;
//        Node<E> next;
//
//        Node(E element){
//            this.item = element;
//            next = null;
//        }
//
//    }
//
//    /**
//     *
//     */
//    public void init(){
//
//        //头节点和尾节点都为空 链表为空
//        Node<String> head = null;
//        Node<String> tail = null;
//
//        //创建一个新的节点 并让head指向此节点
//        head = new Node("head");
//        //让尾节点也指向此节点
//        tail = head;
//
//        //创建新节点 同时和最后一个节点连接起来
//        tail.next = new Node<>("next");
//        //尾节点指向新的节点
//        tail = tail.next;
//
//
//        // 顺序遍历链表
//        Node<String> current = head;
//        while(current != null){
//            System.out.print(current.item);
//            current = current.next;
//        }
//
//
//
//    }
//
//    /**
//     * 倒序遍历链表
//     * @param head
//     */
//    public static void printList(Node<String> head){
//        if (head != null){
//            printList(head.next);
//            System.out.print(head.item);
//
//        }
//    }
//
//    /**
//     * 单链表反转
//     * @param head
//     * @return
//     */
//    public static Node revList(Node<String> head){
//
//        if (head == null){
//            return null;
//        }
//
//        Node<String> nodeResult = null;
//        Node<String> nodePre = null;
//        Node<String> current = head;
//
//        while(current != null){
//
//            Node<String> nodeNext = current.next;
//            if (nodeNext == null){
//                nodeResult = current;
//            }
//            current.next = nodePre;
//            nodePre = current;
//            current = nodeNext;
//
//        }
//        return nodeResult;
//    }

}
