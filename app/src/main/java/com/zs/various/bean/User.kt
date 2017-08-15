package com.zs.various.bean

/**
 * Created by zs
 * Date：2017年 06月 30日
 * Time：14:19
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class User {

    var name: String? = null
    var age: Int = 0
    var sex: String? = null


    constructor() {}

    constructor(name: String){
        this.name = name
    }

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    constructor(name: String , age: Int , sex: String){
        this.name = name
        this.age = age
        this.sex = sex
    }

    override fun toString(): String {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}'
    }
}
