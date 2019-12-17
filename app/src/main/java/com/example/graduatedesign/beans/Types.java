package com.example.graduatedesign.beans;

public class Types {

    private String tp_id,tp_cn;

    private Types(){

    }

    public Types(String tp_id, String tp_cn) {
        this.tp_id = tp_id;
        this.tp_cn = tp_cn;
    }

    public String getTp_id() {
        return tp_id;
    }

    public void setTp_id(String tp_id) {
        this.tp_id = tp_id;
    }

    public String getTp_cn() {
        return tp_cn;
    }

    public void setTp_cn(String tp_cn) {
        this.tp_cn = tp_cn;
    }

    @Override
    public String toString() {
        return "Types{" +
                "tp_id='" + tp_id + '\'' +
                ", tp_cn='" + tp_cn + '\'' +
                '}';
    }
}
