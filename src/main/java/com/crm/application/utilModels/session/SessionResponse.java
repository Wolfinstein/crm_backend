package com.crm.application.utilModels.session;

import groovy.transform.EqualsAndHashCode;
import com.crm.application.utilModels.response.OperationResponse;

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
