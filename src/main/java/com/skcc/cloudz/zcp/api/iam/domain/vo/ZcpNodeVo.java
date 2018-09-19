package com.skcc.cloudz.zcp.api.iam.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ZcpNodeVo {
	private String nodeName;
	private String nodeType;
	private String nodeRoles;
	private String status;
	private String allocatableCpu;
	private String allocatableMemory;
	private String cpuRequests;
	private int cpuRequestsPercentage;
	private String memoryRequests;
	private int memoryRequestsPercentage;
	private String cpuLimits;
	private int cpuLimitsPercentage;
	private String memoryLimits;
	private int memoryLimitsPercentage;
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+9")
	private Date creationTime;
    
	public String getNodeName() {
        return nodeName;
    }
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public String getNodeType() {
		return nodeType;
	}
    public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
    public String getNodeRoles() {
		return nodeRoles;
	}
    public void setNodeRoles(String nodeRoles) {
		this.nodeRoles = nodeRoles;
	}
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAllocatableCpu() {
        return allocatableCpu;
    }
    public void setAllocatableCpu(String allocatableCpu) {
        this.allocatableCpu = allocatableCpu;
    }
    public String getAllocatableMemory() {
        return allocatableMemory;
    }
    public void setAllocatableMemory(String allocatableMemory) {
        this.allocatableMemory = allocatableMemory;
    }
    public String getCpuRequests() {
        return cpuRequests;
    }
    public void setCpuRequests(String cpuRequests) {
        this.cpuRequests = cpuRequests;
    }
    public int getCpuRequestsPercentage() {
        return cpuRequestsPercentage;
    }
    public void setCpuRequestsPercentage(int cpuRequestsPercentage) {
        this.cpuRequestsPercentage = cpuRequestsPercentage;
    }
    public String getMemoryRequests() {
        return memoryRequests;
    }
    public void setMemoryRequests(String memoryRequests) {
        this.memoryRequests = memoryRequests;
    }
    public int getMemoryRequestsPercentage() {
        return memoryRequestsPercentage;
    }
    public void setMemoryRequestsPercentage(int memoryRequestsPercentage) {
        this.memoryRequestsPercentage = memoryRequestsPercentage;
    }
    public String getCpuLimits() {
        return cpuLimits;
    }
    public void setCpuLimits(String cpuLimits) {
        this.cpuLimits = cpuLimits;
    }
    public int getCpuLimitsPercentage() {
        return cpuLimitsPercentage;
    }
    public void setCpuLimitsPercentage(int cpuLimitsPercentage) {
        this.cpuLimitsPercentage = cpuLimitsPercentage;
    }
    public String getMemoryLimits() {
        return memoryLimits;
    }
    public void setMemoryLimits(String memoryLimits) {
        this.memoryLimits = memoryLimits;
    }
    public int getMemoryLimitsPercentage() {
        return memoryLimitsPercentage;
    }
    public void setMemoryLimitsPercentage(int memoryLimitsPercentage) {
        this.memoryLimitsPercentage = memoryLimitsPercentage;
    }
    public Date getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}
