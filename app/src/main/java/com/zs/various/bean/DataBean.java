package com.zs.various.bean;

/**
 * Created by zs
 * Date：2017年 07月 31日
 * Time：9:15
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class DataBean {

    // {"data":{"id":{"courseid":4,"time":1501409165300}}}

    private IdBean data;

    public IdBean getData() {
        return data;
    }

    public void setData(IdBean data) {
        this.data = data;
    }

    public class IdBean{

        private InnerBean id;

        public InnerBean getId() {
            return id;
        }

        public void setId(InnerBean id) {
            this.id = id;
        }

        public class InnerBean{

            private String courseid;
            private String time;

            public String getCourseid() {
                return courseid;
            }

            public void setCourseid(String courseid) {
                this.courseid = courseid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

        }

    }

}


