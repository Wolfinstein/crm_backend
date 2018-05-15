package com.crm.application.utilModels.session;

import com.crm.application.utilModels.response.OperationResponse;
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class SessionResponse extends OperationResponse {

    private SessionItem item;

    public SessionResponse() {
    }

    public SessionResponse(SessionItem item) {
        this.item = item;
    }

    public SessionItem getItem() {
        return item;
    }

    public void setItem(SessionItem item) {
        this.item = item;
    }
}
