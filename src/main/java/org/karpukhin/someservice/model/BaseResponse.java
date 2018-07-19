package org.karpukhin.someservice.model;

import lombok.Data;

@Data
public class BaseResponse {

    private boolean succeed;
    private String cause;
}
