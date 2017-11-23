package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "GroupAddress", description = "")
public class GroupAddress extends DataEntity<GroupAddress> {

    @ApiModelProperty(value = "考勤组的经度")
    protected String groupAttendanceLongitude;
    @ApiModelProperty(value = "考勤组的维度")
    protected String groupAttendanceDimension;
    @ApiModelProperty(value = "考勤地址")
    protected String groupAddress;
    @ApiModelProperty(value = "关联对应的考勤组")
    protected String attendanceGroupId;
	@ApiModelProperty(value = "考勤范围")
    private String groupAttendanceScope;

    public GroupAddress(){

    }
    public GroupAddress(String id){
        super(id);
    }

    public String getGroupAttendanceLongitude() {
        return groupAttendanceLongitude;
    }

    public void setGroupAttendanceLongitude(String groupAttendanceLongitude) {
        this.groupAttendanceLongitude = groupAttendanceLongitude;
    }

    public String getGroupAttendanceDimension() {
        return groupAttendanceDimension;
    }

    public void setGroupAttendanceDimension(String groupAttendanceDimension) {
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
	public String getGroupAttendanceScope() {
        return groupAttendanceScope;
    }

    public void setGroupAttendanceScope(String groupAttendanceScope) {
        this.groupAttendanceScope = groupAttendanceScope;
    }


}