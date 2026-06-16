package org.example.tiliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {
    private Integer id;
    private String name;
    private String room;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer masterId;
    private String subject;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateTime;
    private String masterName;
}
