package org.example.tiliaswebmanagement.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private Integer page =1;
    private Integer pageSize = 10;
    private Integer clazzId;
    private String name;
    private Integer degree;

}
