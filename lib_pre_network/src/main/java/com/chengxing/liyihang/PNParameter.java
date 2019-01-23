package com.chengxing.liyihang;

public class PNParameter {

    private Object one;
    private Object two;
    private Object three;
    private Object four;
    private Object five;
    private Object six;

    public static PNParameter getConvert(Object object){
        return (PNParameter) object;
    }

    public PNParameter(Object one, Object two) {
        this.one = one;
        this.two = two;
    }

    public PNParameter(Object one, Object two, Object three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public PNParameter(Object one, Object two, Object three, Object four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }

    public PNParameter(Object one, Object two, Object three, Object four, Object five) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
    }

    public PNParameter(Object one, Object two, Object three, Object four, Object five, Object six) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
    }

    public <T extends Object> T getOne() {
        return (T) one;
    }

    public <T extends Object> T getTwo() {
        return (T) two;
    }

    public <T extends Object> T getThree() {
        return (T) three;
    }

    public <T extends Object> T getFour() {
        return (T) four;
    }

    public <T extends Object> T getFive() {
        return (T) five;
    }

    public <T extends Object> T getSix() {
        return (T) six;
    }

    public void setOne(Object one) {
        this.one = one;
    }

    public void setTwo(Object two) {
        this.two = two;
    }

    public void setThree(Object three) {
        this.three = three;
    }

    public void setFour(Object four) {
        this.four = four;
    }

    public void setFive(Object five) {
        this.five = five;
    }

    public void setSix(Object six) {
        this.six = six;
    }
}
