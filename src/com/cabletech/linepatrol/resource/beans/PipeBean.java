package com.cabletech.linepatrol.resource.beans;

import com.cabletech.commons.base.BaseBean;

public class PipeBean extends BaseBean {
		private String id;
		private String assetno;
		private String issueNumber;
		private String workName;
		private String pipeAddress;
		private String pipeLine;
		private Float pipeLengthChannel;
		private Float pipeLengthHole;
		private String pipeType;
		private String routeRes;
		private String picture;
		private Float mobileScareChannel;
		private Float mobileScareHole;
		private String principle;
		private String finishTime;
		private String scetion;
		private String remark;
		private String maintenanceId;
		
		private String MIS;
		private String finishStartTime;
		private String finishEndTime;
		private String[] pipeTypes;
		private String[] routeRess;
		
		private String targetMaintenanceId;//管道分配时目标维护单位
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAssetno() {
			return assetno;
		}
		public void setAssetno(String assetno) {
			this.assetno = assetno;
		}
		public String getWorkName() {
			return workName;
		}
		public void setWorkName(String workName) {
			this.workName = workName;
		}
		public String getPipeAddress() {
			return pipeAddress;
		}
		public void setPipeAddress(String pipeAddress) {
			this.pipeAddress = pipeAddress;
		}
		public String getPipeLine() {
			return pipeLine;
		}
		public void setPipeLine(String pipeLine) {
			this.pipeLine = pipeLine;
		}
		public Float getPipeLengthChannel() {
			return pipeLengthChannel;
		}
		public void setPipeLengthChannel(Float pipeLengthChannel) {
			this.pipeLengthChannel = pipeLengthChannel;
		}
		public Float getPipeLengthHole() {
			return pipeLengthHole;
		}
		public void setPipeLengthHole(Float pipeLengthHole) {
			this.pipeLengthHole = pipeLengthHole;
		}
		public String getPipeType() {
			return pipeType;
		}
		public void setPipeType(String pipeType) {
			this.pipeType = pipeType;
		}
		public String getRouteRes() {
			return routeRes;
		}
		public void setRouteRes(String routeRes) {
			this.routeRes = routeRes;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}
		public Float getMobileScareChannel() {
			return mobileScareChannel;
		}
		public void setMobileScareChannel(Float mobileScareChannel) {
			this.mobileScareChannel = mobileScareChannel;
		}
		public Float getMobileScareHole() {
			return mobileScareHole;
		}
		public void setMobileScareHole(Float mobileScareHole) {
			this.mobileScareHole = mobileScareHole;
		}
		public String getPrinciple() {
			return principle;
		}
		public void setPrinciple(String principle) {
			this.principle = principle;
		}
		public String getFinishTime() {
			return finishTime;
		}
		public void setFinishTime(String finishTime) {
			this.finishTime = finishTime;
		}
		public String getScetion() {
			return scetion;
		}
		public void setScetion(String scetion) {
			this.scetion = scetion;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getMaintenanceId() {
			return maintenanceId;
		}
		public void setMaintenanceId(String maintenanceId) {
			this.maintenanceId = maintenanceId;
		}
		public String getIssueNumber() {
			return issueNumber;
		}
		public void setIssueNumber(String issueNumber) {
			this.issueNumber = issueNumber;
		}
		public String getTargetMaintenanceId() {
			return targetMaintenanceId;
		}
		public void setTargetMaintenanceId(String targetMaintenanceId) {
			this.targetMaintenanceId = targetMaintenanceId;
		}
		public String getMIS() {
			return MIS;
		}
		public void setMIS(String mIS) {
			MIS = mIS;
		}
		public String getFinishStartTime() {
			return finishStartTime;
		}
		public void setFinishStartTime(String finishStartTime) {
			this.finishStartTime = finishStartTime;
		}
		public String getFinishEndTime() {
			return finishEndTime;
		}
		public void setFinishEndTime(String finishEndTime) {
			this.finishEndTime = finishEndTime;
		}
		public String[] getPipeTypes() {
			return pipeTypes;
		}
		public void setPipeTypes(String[] pipeTypes) {
			this.pipeTypes = pipeTypes;
		}
		public String[] getRouteRess() {
			return routeRess;
		}
		public void setRouteRess(String[] routeRess) {
			this.routeRess = routeRess;
		}
		
}