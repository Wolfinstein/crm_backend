package com.crm.application.utilModels.response;

public class OperationResponse {
    private ResponseStatusEnum operationStatus;

    ;
    private String operationMessage;

    public OperationResponse() {
    }

    public OperationResponse(ResponseStatusEnum operationStatus, String operationMessage) {
        this.operationStatus = operationStatus;
        this.operationMessage = operationMessage;
    }

    public ResponseStatusEnum getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(ResponseStatusEnum operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public enum ResponseStatusEnum {SUCCESS, ERROR, WARNING, NO_ACCESS}


}
