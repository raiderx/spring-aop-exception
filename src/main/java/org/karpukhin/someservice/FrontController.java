package org.karpukhin.someservice;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.karpukhin.someservice.model.ListRequest;
import org.karpukhin.someservice.model.ListResponse;

@Controller
@RequiredArgsConstructor
public class FrontController {

    @NonNull
    private final SomeService someService;

    public ListResponse list(ListRequest request) {
        return someService.list(request);
    }
}
