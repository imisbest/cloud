package com.csw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Folder implements Serializable {
    private String id;
    private String name;
    private String parentFolder;
    private User createUser;
    private String path;

    private Folder folder;

    public Folder(String id, String name, String parentFolder, User createUser, String path) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.createUser = createUser;
        this.path = path;
    }
}
