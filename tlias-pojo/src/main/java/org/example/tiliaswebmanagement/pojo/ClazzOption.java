package org.example.tiliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzOption {
    private List<Object> clazzList;
    private List<Object> dataList;
}
