package org.karpukhin.someservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.karpukhin.someservice.model.ListRequest;
import org.karpukhin.someservice.model.ListResponse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = Config.class)
@RunWith(SpringRunner.class)
@Slf4j
public class FrontControllerTest {

    @Autowired
    private FrontController frontController;

    @Test
    public void testListWhenRequestIsNull() {
        ListResponse response = frontController.list(null);
        log.info("Response: {}", response);

        assertThat(response, is(not(nullValue())));
        assertThat(response.isSucceed(), is(false));
        assertThat(response.getCause(), is("Internal error"));
    }

    @Test
    public void testListWhenRequestNameIsNull() {
        ListResponse response = frontController.list(new ListRequest(null));
        log.info("Response: {}", response);

        assertThat(response, is(not(nullValue())));
        assertThat(response.isSucceed(), is(false));
        assertThat(response.getCause(), is("Parameter 'request.name' is null"));
    }

    @Test
    public void testList() {
        ListRequest request = new ListRequest("some-name");
        ListResponse response = frontController.list(request);
        log.info("Response: {}", response);

        assertThat(response, is(not(nullValue())));
        assertThat(response.isSucceed(), is(true));
        assertThat(response.getCause(), is(nullValue()));
    }
}
