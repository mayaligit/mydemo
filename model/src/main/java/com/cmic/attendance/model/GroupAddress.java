package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 组地址表实体
 */
@ApiModel(value = "GroupAddress", description = "组地址表")
public class GroupAddress extends DataEntity<GroupAddress> {

    @ApiModelProperty(value = "考勤组的经度")
    protected Float groupAttendanceLongitude;
    @ApiModelProperty(value = "考勤组的维度")
    protected Float groupAttendanceDimension;
    @ApiModelProperty(value = "考勤地址")
    protected String groupAddress;
    @ApiModelProperty(value = "关联对应的考勤组")
    protected String attendanceGroupId;

    public GroupAddress(){

    }
    public GroupAddress(String id){
        super(id);
    }

    public Float getGroupAttendanceLongitude() {
        return groupAttendanceLongitude;
    }

    public void setGroupAttendanceLongitude(Float groupAttendanceLongitude) {
        this.groupAttendanceLongitude = groupAttendanceLongitude;
    }

    public Float getGroupAttendanceDimension() {
        return groupAttendanceDimension;
    }

    public void setGroupAttendanceDimension(Float groupAttendanceDimension) {
        this.groupAttendanceDimension = groupAttendanceDimension;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getAttendanceGroupId() {
        return attendanceGroupId;
    }

    public void setAttendanceGroupId(String attendanceGroupId) {
        this.attendanceGroupId = attendanceGroupId;
    }


}