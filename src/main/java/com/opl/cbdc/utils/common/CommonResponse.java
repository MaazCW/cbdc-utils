package com.opl.cbdc.utils.common;

import java.io.Serializable;

/**
 * USE FOR COMMON REPONSE IN ALL REPOSITORY
 * 
 * @author harshit
 *
 */
public class CommonResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	private Object data;

	private Integer status;

	private Boolean flag;

	private String errorCode;

	private String sysMessage;
	private Integer msgCode;
	private Boolean isMsgDisp;

	private Integer responseType;

	private Long applicationId;

	public CommonResponse() {
		super();
	}

	public CommonResponse(String message, Integer status) {
		super();
		this.message = message;
		this.status = status;
		this.flag = false;
	}

	public CommonResponse(String message, Object data, Integer status) {
		super();
		this.message = message;
		this.data = data;
		this.status = status;
		this.flag = false;
	}

	public CommonResponse(String message, Object data, Integer status, Boolean flag) {
		super();
		this.message = message;
		this.data = data;
		this.status = status;
		this.flag = flag;
	}

	public CommonResponse(String message, Integer status, Boolean flag) {
		super();
		this.message = message;
		this.status = status;
		this.flag = flag;
	}

	public CommonResponse(Object obj, String message, int status) {

		this.message = message;
		this.status = status;
		this.data = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSysMessage() {
		return sysMessage;
	}

	public void setSysMessage(String sysMessage) {
		this.sysMessage = sysMessage;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	public Boolean getIsMsgDisp() {
		return isMsgDisp;
	}

	public void setIsMsgDisp(Boolean isMsgDisp) {
		this.isMsgDisp = isMsgDisp;
	}

	public Integer getResponseType() {
		return responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public CommonResponse(String message, Integer status, Boolean flag, Integer msgCode, Integer responseType) {
		super();
		this.message = message;
		this.status = status;
		this.flag = flag;
		this.msgCode = msgCode;
		this.responseType = responseType;
	}

	public CommonResponse(Integer status, Boolean flag, Integer msgCode, Integer responseType, Long applicationId) {
		super();
		this.status = status;
		this.flag = flag;
		this.msgCode = msgCode;
		this.responseType = responseType;
		this.applicationId = applicationId;
	}

	public CommonResponse(Integer status, Boolean flag, Integer msgCode, Integer responseType, String sysMessage,
			Long applicationId) {
		super();
		this.status = status;
		this.flag = flag;
		this.msgCode = msgCode;
		this.sysMessage = sysMessage;
		this.responseType = responseType;
		this.applicationId = applicationId;
	}

	@Override
	public String toString() {
		return "CommonResponse [message=" + message + ", data=" + data + ", status=" + status + ", flag=" + flag
				+ ", errorCode=" + errorCode + ", sysMessage=" + sysMessage + ", msgCode=" + msgCode + ", isMsgDisp="
				+ isMsgDisp + ", responseType=" + responseType + ", applicationId=" + applicationId + "]";
	}
}
