package com.csw.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class CloudFile implements Serializable {

    private String id;
    private String name;
    private Integer size;
    private String fileType;
    private Date uploadTime;
    private Integer status;
    private Folder fatherFolder;
    private User uploadUser;


}