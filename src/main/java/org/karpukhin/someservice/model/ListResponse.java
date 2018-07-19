package org.karpukhin.someservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListResponse extends BaseResponse {

    private List<Person> persons;
}
