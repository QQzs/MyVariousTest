package com.zs.various.bean;

/**
 * Created by zs
 * Date：2019年 01月 15日
 * Time：10:33
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class GridItemBean {

    private int type;
    private String id;
    private String title;
    private String name;
    private boolean choice;
    private boolean multiChoice;
    private boolean allChoice;

    public GridItemBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public GridItemBean(int type, String title, boolean multiChoice) {
        this.type = type;
        this.title = title;
        this.multiChoice = multiChoice;
    }

    public GridItemBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GridItemBean(String id, String name, boolean allChoice) {
        this.id = id;
        this.name = name;
        this.allChoice = allChoice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public boolean isMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    public boolean isAllChoice() {
        return allChoice;
    }

    public void setAllChoice(boolean allChoice) {
        this.allChoice = allChoice;
    }

    @Override
    public String toString() {
        return "GridItemBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
