package com.whx.ktapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.whx.ktapplication.model.BarChartItemBean;

/**
 * Created by xuqianying on 2017/8/30.
 */

public class BarChartSelectLine extends LineChartSelectLine {

    //选中的
    //private ArrayList<DashboardTendencyOrg> mDashboardTendencyOrgList = new ArrayList<>();
    //private ArrayList<OrgInfo> mOrgInfoList = new ArrayList<>();
    private String mIndicatorName = "交易额" ;

    private BarData mData;


    public BarChartSelectLine(Context context) {
        super(context);
    }

    public BarChartSelectLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartSelectLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public void setData(ArrayList<DashboardTendencyOrg> dashTendencyOrgList, ArrayList<OrgInfo> orgInfoList, String indicatorName) {
//        if (CollectionUtils.isEmpty(dashTendencyOrgList)) {
//            return;
//        }
//
//        mDashboardTendencyOrgList.clear();
//        mDashboardTendencyOrgList.addAll(dashTendencyOrgList);
//        if (!CollectionUtils.isEmpty(orgInfoList)) {
//            mOrgInfoList.clear();
//            mOrgInfoList.addAll(orgInfoList);
//        }
//        mIndicatorName = indicatorName;
//    }

    @Override
    public void prepareSelectData() {

//        DashboardTendencyCompletion completion = new DashboardTendencyCompletion();
//        completion.calibration = "";
//        completion.completionValue = "2345678";
//        completion.indicatorType = "whatever";
//        completion.monthOnMonth = "+23.33%";

//        for (DashboardTendencyOrg dashboardTendencyOrg : mDashboardTendencyOrgList) {
//            if (dashboardTendencyOrg == null) {
//                continue;
//            }
//            if (!CollectionUtils.isEmpty(mOrgInfoList)) {
//                String orgName = "";
//                //获取org name
//                for (OrgInfo orgInfo : mOrgInfoList) {
//                    if (TextUtils.equals(orgInfo.id, dashboardTendencyOrg.id)) {
//                        orgName = orgInfo.name;
//                        break;
//                    }
//                }
//                mOrgNameList.add(orgName);
//            }
//
//            //获取选中的点
//            if (CollectionUtils.isEmpty(dashboardTendencyOrg.completions) || dashboardTendencyOrg.completions.size() <= mIndex
//                    || dashboardTendencyOrg.completions.get(mIndex) == null) {
//                continue;
//            }
//            mSelectDataList.add(dashboardTendencyOrg.completions.get(mIndex));
//        }
    }

    @Override
    public void clearView() {

        super.clearView();
    }

    @Override
    public void prepare() {
//        if (CollectionUtils.isEmpty(mSelectDataList)) {
//            mIsPrepared = false;
//            return;
//        }

        String textDate = mData.itemBean.dateStr;
        String name = mData.name;
        String completion = mIndicatorName + " : " + mData.itemBean.value;
        String monthOnMonth = mMonthOnMonthText + " : " + getMonthOnMonthString(mData.itemBean.linkRatio);
        float width = Math.max(mTextPaint.measureText(textDate), mTextPaint.measureText(completion));
        width = Math.max(width, mTextPaint.measureText(monthOnMonth));
        width = Math.max(width, mTextPaint.measureText(name));
        mDescWidth = width + mTextLeftPadding + mTextRightPadding;
        mDescHeight = 70;

        mIsPrepared = true;
    }

    @Override
    public void drawDescription(Canvas canvas) {

        mTextPaint.setColor(Color.parseColor("#999999"));
        canvas.drawText(mData.itemBean.dateStr, mDesLeft + mTextLeftPadding, 18, mTextPaint);
        mTextPaint.setColor(Color.parseColor("#666666"));
        canvas.drawText(mData.name, mDesLeft + mTextLeftPadding, 34, mTextPaint);
        canvas.drawText(mIndicatorName + " : " + mData.itemBean.value, mDesLeft + mTextLeftPadding, 50, mTextPaint);
        canvas.drawText(mMonthOnMonthText + " : ", mDesLeft + mTextLeftPadding, 66, mTextPaint);
        if (TextUtils.isEmpty(mData.itemBean.linkRatio)) {
            return;
        }
        String monthOnMonth = getMonthOnMonthString(mData.itemBean.linkRatio);
        mTextPaint.setColor(getMonthOnMonthColor(monthOnMonth));
        canvas.drawText(monthOnMonth, mDesLeft + mTextLeftPadding + mTextPaint.measureText(mMonthOnMonthText + " : "), 66, mTextPaint);

    }

    private String getMonthOnMonthString(String monthOnMonth) {
        double value;
        try {
            value = Double.parseDouble(monthOnMonth);
        } catch (Exception e) {
            value = 0;
        }
        if (value > 0) {
            monthOnMonth = "+" + monthOnMonth;
        }
        return monthOnMonth + "%";
    }

    private int getMonthOnMonthColor(String monthOnMonth) {

        if (monthOnMonth.charAt(0) == '+') {
            return Color.parseColor("#fc797a");
        } else if (monthOnMonth.charAt(0) == '-') {
            return Color.parseColor("#3dc6b6");
        }
        return Color.parseColor("#666666");
    }

    public void setData(BarData data) {
        mData = data;
    }

    public static class BarData {
        public BarChartItemBean itemBean;
        public String name;

        public BarData(BarChartItemBean itemBean, String name) {
            this.itemBean = itemBean;
            this.name = name;
        }
    }

}
