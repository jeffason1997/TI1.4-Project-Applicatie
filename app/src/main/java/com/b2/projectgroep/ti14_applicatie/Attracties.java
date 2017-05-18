package com.b2.projectgroep.ti14_applicatie;

/**
 * Created by tmbro on 18-5-2017.
 */

public class Attracties {

    private String name;
    private String type;
    private int image;
    private String info;

    @Override
    public String toString() {
        return "Attracties{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image=" + image +
                ", info='" + info + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Attracties(String name, String type, int image, String info){
        this.name = name;
        this.type = type;
        this.image = image;
        this.info = info;

    }
}
