package com.example.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class FileInfo {
    private String file;
    @Length(max = 100)
    private String title;

}
