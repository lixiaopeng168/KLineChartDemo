package com.github.fujianlian.klinechart;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */
public class KLineChartAdapter extends BaseKLineChartAdapter {

    private List<KLineEntity> datas = new ArrayList<>();

    public KLineChartAdapter() {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    public KLineEntity getEntity(int position) {
        return datas.get(position);
    }

    @Override
    public String getDate(int position) {
        return datas.get(position).Date;
    }

    @Override
    public long getTime(int position) {
        return datas.get(position).Time;
    }

    /**
     * 向头部添加数据
     */
    public void addHeaderData(List<KLineEntity> data) {
        if (data != null && !data.isEmpty()) {
            datas.clear();
            datas.addAll(data);
        }
    }

    /**
     * 向尾部添加数据
     */
    public void addFooterData(List<KLineEntity> data) {
        if (data != null && !data.isEmpty()) {
            datas.clear();
            datas.addAll(0, data);
        }
    }
    public void addItemData(KLineEntity data) {
        if (data != null ) {
            datas.add( data);
//            notifyDataSetChanged();
        }
    }
    public void removeItemData(int position) {
            datas.remove( position);
    }
    public List<KLineEntity> getListEntity(){
        return datas;
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLineEntity data) {
        datas.set(position, data);
//        notifyDataSetChanged();
    }

    /**
     * 数据清除
     */
    public void clearData() {
        datas.clear();
        notifyDataSetChanged();
    }
}
