package com.csw.dao;

import com.csw.entity.CloudFile;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CloudFileDao {
    public void addFile(@Param("file") CloudFile file);

    public List<CloudFile> selectFileListByFolderId(@Param("folderId") String folderId);

    public CloudFile selectFileById(@Param("fileId") String fileId);

    public List<CloudFile> selectPublicFile();

    public List<CloudFile> selectFileByFileName(@Param("params") Map<String, Object> params);

    public void deleteFileById(@Param("fileId") String fileId);

    public void deleteFileByFolderId(@Param("folderId") String folderId);

    public List<CloudFile> selectPhotoByUserId(@Param("userId") String userId);

    public List<CloudFile> selectDocumentByUserId(@Param("userId") String userId);

    public List<CloudFile> selectMovieByUserId(@Param("userId") String userId);

    public List<CloudFile> selectMusicByUserId(@Param("userId") String userId);

    public List<CloudFile> selectZipByUserId(@Param("userId") String userId);

    public void changeFileAccessByFileId(@Param("params") Map<String, Object> params);
}
