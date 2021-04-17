package com.csw.dao;

import com.csw.entity.Folder;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FolderDao {
    public void addFolder(@Param("folder") Folder folder);

    public void addRootFolder(@Param("folder") Folder folder);

    public List<Folder> selectFolderListByFolderId(@Param("folderId") String folderId);

    public Folder selectRootFolderByUserId(@Param("userId") String userId);

    public Folder selectFolderById(@Param("folderId") String folderId);

    public Folder selectFatherFolderById(@Param("folderId") String folderId);

    public void deleteFolderById(@Param("id") String id);

    public List<Folder> selectAllFolderByFatherFolder(@Param("fatherFolder") Folder fatherFolder);

}
