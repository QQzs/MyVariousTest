package com.zs.various.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zs
 * Date：2019年 02月 13日
 * Time：11:12
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class TestBean implements Parcelable {

    private String name;
    private int age;

    public TestBean(String name) {
        this.name = name;
    }

    public TestBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel source) {
            return new TestBean(source);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
