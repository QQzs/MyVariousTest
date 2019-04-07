package com.zs.various.util;

public class LinkedList {

    class Node<E> {

        E item;
        Node<E> next;

        Node(E element){
            this.item = element;
            next = null;
        }

    }

    /**
     *
     */
    public void init(){

        //头节点和尾节点都为空 链表为空
        Node<String> head = null;
        Node<String> tail = null;

        //创建一个新的节点 并让head指向此节点
        head = new Node("head");
        //让尾节点也指向此节点
        tail = head;

        //创建新节点 同时和最后一个节点连接起来
        tail.next = new Node<>("next");
        //尾节点指向新的节点
        tail = tail.next;


        // 顺序遍历链表
        Node<String> current = head;
        while(current != null){
            System.out.print(current.item);
            current = current.next;
        }



    }

    /**
     * 倒序遍历链表
     * @param head
     */
    public static void printList(Node<String> head){
        if (head != null){
            printList(head.next);
            System.out.print(head.item);

        }
    }

    /**
     * 单链表反转
     * @param head
     * @return
     */
    public static Node revList(Node<String> head){

        if (head == null){
            return null;
        }

        Node<String> nodeResult = null;
        Node<String> nodePre = null;
        Node<String> current = head;

        while(current != null){

            Node<String> nodeNext = current.next;
            if (nodeNext == null){
                nodeResult = current;
            }
            current.next = nodePre;
            nodePre = current;
            current = nodeNext;

        }
        return nodeResult;
    }

}
