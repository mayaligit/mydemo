package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 节假日实体
 */
@ApiModel(value = "Holidays", description = "节假日表")
public class Holidays extends DataEntity<Holidays> {

    @ApiModelProperty(value = "年")
    protected String year;

    @ApiModelProperty(value = "月日")
    protected String monthDay;

    @ApiModelProperty(value = "节假日状态，1休息日，2节假日")
    protected String dayStatus;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }
}
