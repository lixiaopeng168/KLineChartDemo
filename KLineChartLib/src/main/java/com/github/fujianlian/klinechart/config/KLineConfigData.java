package com.github.fujianlian.klinechart.config;

public class KLineConfigData {

    private static final KLineConfigData k = new KLineConfigData();
    public KLineConfigData(){}
    public static KLineConfigData newInstance(){
        return k;
    }

    /**
     * 设置vol  默认 5 ,10
     */
    public int vol5 = 5;
    public int vol10 = 10;
    /**
     * 设置rsi 默认13
     */
    public int rsiIndex = 13;
    /**
     * 设置kdj  默认 5 ,10
     */
    public int kdjK = 0;
    public int kdjD = 0;
    public int kdjJ = 0;
    /**
     * 设置macd  默认 5 ,10
     */
    public int macdDif = 0;
    public int macdDea = 0;
    public int macd = 0;

    /**
     * 设置精度格式
     */
    public String  preciseText = "%.2f";
    public void setPreciseText(int precise){
        this.preciseText = "%."+precise+"f";
    }


}
