package org.karpukhin.someservice;

import org.springframework.stereotype.Service;
import org.karpukhin.someservice.aop.Traceable;
import org.karpukhin.someservice.model.ListRequest;
import org.karpukhin.someservice.model.ListResponse;

@Service
public class SomeService {

    @Traceable("list")
    public ListResponse list(ListRequest request) {
        if (request.getName() == null) {
            throw new IllegalArgumentException("Parameter 'request.name' is null");
        }
        return new ListResponse();
    }
}
