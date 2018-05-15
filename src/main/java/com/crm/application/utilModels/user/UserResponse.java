package com.crm.application.utilModels.user;

import com.crm.application.model.User;
import com.crm.application.utilModels.response.OperationResponse;
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class UserResponse extends OperationResponse {
    private User data = new User();

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
