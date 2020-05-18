package com.github.fujianlian.klinechart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.R;
import com.github.fujianlian.klinechart.BaseKLineChartView;
import com.github.fujianlian.klinechart.base.IChartDraw;
import com.github.fujianlian.klinechart.base.IValueFormatter;
import com.github.fujianlian.klinechart.entity.IVolume;
import com.github.fujianlian.klinechart.formatter.BigValueFormatter;

/**
 * Created by hjm on 2017/11/14 17:49.
 */
public class VolumeDraw implements IChartDraw<IVolume> {

    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma5Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma10Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    private int pillarWidth = 0;
    //是否绘制矩形
    private boolean isLine = false;
    //矩形图宽度
    private float candleWidth = 0f;
    private KLineChartView kChartView;
    public VolumeDraw(BaseKLineChartView view) {
        Context context = view.getContext();
        kChartView = (KLineChartView) view;
        mRedPaint.setColor(ContextCompat.getColor(context, R.color.chart_red));
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.chart_green));
        mLinePaint.setColor(ContextCompat.getColor(context, R.color.chart_line));
//        pillarWidth = ViewUtil.Dp2Px(context, 4);
    }

    @Override
    public void drawTranslated(
            @Nullable IVolume lastPoint, @NonNull IVolume curPoint, float lastX, float curX,
            @NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position) {
        if (isLine){
            drawMinute(canvas,curPoint,lastPoint,curX,view,position);
        }else {
            drawHistogram(canvas, curPoint, lastPoint, curX, view, position);
            if (lastPoint.getMA5Volume() != 0f) {
                view.drawVolLine(canvas, ma5Paint, lastX, lastPoint.getMA5Volume(), curX, curPoint.getMA5Volume());
            }
            if (lastPoint.getMA10Volume() != 0f) {
                view.drawVolLine(canvas, ma10Paint, lastX, lastPoint.getMA10Volume(), curX, curPoint.getMA10Volume());
            }
        }

//        if (position == view.getmStopIndex()){
////            Log.i(KLineConstant.TAG,"相等 :"+position +"\tstop:"+view.getmStopIndex()+"\tsingle:"+view.getSingleSpace()
////                    +"\tcurX:"+curX+"\tall:"+(curX+view.getSingleSpace()*5));
////            canvas.drawLine(curX,100,curX+view.getSingleSpace()*5,200,mRedPaint);
//        }
    }

    /**
     * 绘制成交量矩形 分时
     */
    private void drawMinute(Canvas canvas, IVolume curPoint, IVolume lastPoint, float curX,
            BaseKLineChartView view, int position) {
        float r = candleWidth / 2;
        float top = view.getVolY(curPoint.getVolume());
        int bottom = view.getVolRect().bottom;

        canvas.drawRect(curX - r, top, curX + r, bottom, mLinePaint);
    }
    /**
     * 绘制成交量矩形 非分时
     */
    private void drawHistogram(
            Canvas canvas, IVolume curPoint, IVolume lastPoint, float curX,
            BaseKLineChartView view, int position) {
        float r = candleWidth / 2;
        float top = view.getVolY(curPoint.getVolume());
        int bottom = view.getVolRect().bottom;

        if (curPoint.getClosePrice() >= curPoint.getOpenPrice()) {//涨
            canvas.drawRect(curX - r, top, curX + r, bottom, mRedPaint);
        } else {
            canvas.drawRect(curX - r, top, curX + r, bottom, mGreenPaint);
        }
    }

    @Override
    public void drawText(
            @NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position, float x, float y) {
        IVolume point = (IVolume) view.getItem(position);
        String text = "VOL:" + getValueFormatter().format(point.getVolume()) + "  ";
        canvas.drawText(text, x, y, view.getTextPaint());
        x += view.getTextPaint().measureText(text);
        text = "MA5:" + getValueFormatter().format(point.getMA5Volume()) + "  ";
        canvas.drawText(text, x, y, ma5Paint);
        x += ma5Paint.measureText(text);
        text = "MA10:" + getValueFormatter().format(point.getMA10Volume());
        canvas.drawText(text, x, y, ma10Paint);
    }

    @Override
    public float getMaxValue(IVolume point) {
        return Math.max(point.getVolume(), Math.max(point.getMA5Volume(), point.getMA10Volume()));
    }

    @Override
    public float getMinValue(IVolume point) {
        return Math.min(point.getVolume(), Math.min(point.getMA5Volume(), point.getMA10Volume()));
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new BigValueFormatter();
    }

    /**
     * 设置 MA5 线的颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        this.ma5Paint.setColor(color);
    }

    /**
     * 设置 MA10 线的颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        this.ma10Paint.setColor(color);
    }

    public void setLineWidth(float width) {
        this.ma5Paint.setStrokeWidth(width);
        this.ma10Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.ma5Paint.setTextSize(textSize);
        this.ma10Paint.setTextSize(textSize);
    }

    /**
     * 绘制分时图
     */
    public void setLine(boolean isLine){
        this.isLine = isLine;
    }
    /**
     * 矩形图宽度
     */
    public void setCandleWidth(float candleWidth) {
        this.candleWidth = candleWidth;
    }
}
