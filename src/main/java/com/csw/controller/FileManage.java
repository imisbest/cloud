package com.csw.controller;

import com.csw.dao.CloudFileDao;
import com.csw.dao.FolderDao;
import com.csw.entity.CloudFile;
import com.csw.entity.Folder;
import com.csw.entity.User;
import com.csw.util.SystemConstant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value = "/file")
public class FileManage {
    @Autowired
    private FolderDao folderDao;
    @Autowired
    private CloudFileDao cloudFileDao;

    /**
     * @param fileList
     * @param folderId
     * @param session
     * @return
     */
    @RequestMapping("/upload")
    public String FileUpload(@RequestParam("dataList") MultipartFile[] fileList,
                             @RequestParam("path") String folderId, HttpSession session) {
        String sysMsg = "";
        if (fileList.length == 0) {
            //session.setAttribute("请选择文件");
            return "/view/file?dir=" + folderId;
        }
        try {
            User user = (User) session.getAttribute("user");
            Folder folder;
            if (folderId == null)
                folder = folderDao.selectRootFolderByUserId(user.getId());
            else
                folder = folderDao.selectFolderById(folderId);

            String uploadPath = SystemConstant.USER_FILE_PATH + folder.getPath() +
                    "\\" + folder.getName();
            for (MultipartFile files : fileList) {
                String name = files.getOriginalFilename();
                int size = (int) (files.getSize() / 1024);
                if (size == 0)
                    size = 1;

                int sub = files.getOriginalFilename().lastIndexOf('.');

                Map<String, Object> params = new HashMap<>();
                params.put("fileName", name);
                params.put("userId", user.getId());
                params.put("dirId", folder.getId());
//////////////////////////////////////////////////////////////
                Integer sameNamefile = cloudFileDao.selectFileByFileName(params).size();

                if (sameNamefile > 0) {
                    name += ("(" + sameNamefile + ")");
                }
                CloudFile cloudFile = new CloudFile(UUID.randomUUID().toString(), name,
                        size,
                        files.getOriginalFilename().substring(sub + 1).toLowerCase(),
                        new Date(), null, folder, user);

                cloudFileDao.addFile(cloudFile);

                File uploadFile = new File(uploadPath, name);
                files.transferTo(uploadFile);
            }
            sysMsg = "文件上传成功";
        } catch (Exception e) {
            sysMsg = "请选择文件";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }
        return "/view/file?dir=" + folderId;
    }

    /**
     * @param folderName
     * @param path
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/folderAdd")
    public String addFolder(String folderName, String path, HttpSession session) {
        System.out.println("folderName]" + folderName);
        System.out.println("path]" + path);
        if (folderName == null || folderName.equals("")) {
            session.setAttribute("sysMsg", "请输入文件夹名");
            return "/view/file?dir=0";
        }
        if (!SystemConstant.isValidFileName(folderName)) {
            session.setAttribute("sysMsg", "文件夹名不规范，请重新输入");
            return "/view/file?dir=0";
        }
        String sysMsg = "";
        Folder fatherFolder, newFolder = null;
        File realFolder;
        try {
            User user = (User) session.getAttribute("user");
            System.out.println("user]" + user);

            String realPath = SystemConstant.USER_FILE_PATH;
            if (path == null || path == "")
                fatherFolder = folderDao.selectRootFolderByUserId(user.getId());
            else
                fatherFolder = folderDao.selectFolderById(path);
            //一个新的子目录对象
            System.out.println("fatherFolder]" + fatherFolder);
            newFolder = new Folder(UUID.randomUUID().toString(), folderName, fatherFolder.getId(), user, fatherFolder.getPath()
                    + fatherFolder.getName() + "\\");
            System.out.println("newFolder]" + newFolder);
//本地创建
            realFolder = new File(realPath + fatherFolder.getPath() + fatherFolder.getName()
                    + "\\" + folderName);
            if (!realFolder.exists())
                realFolder.mkdirs();
//数据库上传
            folderDao.addFolder(newFolder);
            sysMsg = "上传成功";
        } catch (Exception e) {
            sysMsg = "系统异常";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }
        return "/view/file?dir=" + path;
    }

    /**
     * @param fileId
     * @param folderId
     * @param session
     * @return
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(@RequestParam("file") String fileId, @RequestParam("folder") String folderId, HttpSession session) {

        ResponseEntity<byte[]> responseEntity = null;
        try {
            User user = (User) session.getAttribute("user");
            Folder folder;
            if (folderId == null)
                folder = folderDao.selectRootFolderByUserId(user.getId());
            else
                folder = folderDao.selectFolderById(folderId);
            CloudFile cloudFile = cloudFileDao.selectFileById(fileId);
            if (cloudFile.getUploadUser().getId() != user.getId() && cloudFile.getStatus() == 0) {

                return null;
            }

            String path = SystemConstant.USER_FILE_PATH + folder.getPath() + folder.getName() + "\\" + cloudFile.getName();

            File file = new File(path);
            HttpHeaders headers = new HttpHeaders();
            String fileName = new String(cloudFile.getName().getBytes("UTF-8"), "ISO-8859-1");
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }
        return responseEntity;
    }

    /**
     * @param fileId
     * @param folderId
     * @param session
     * @return
     */
    @RequestMapping("/delete")
    public String fileDelete(@RequestParam("file") String fileId, @RequestParam("folder") String folderId, HttpSession session) {
        String sysMsg = "";
        try {
            User user = (User) session.getAttribute("user");
            Folder folder;
            if (folderId == null)
                folder = folderDao.selectRootFolderByUserId(user.getId());
            else
                folder = folderDao.selectFolderById(folderId);
            CloudFile cloudFile = cloudFileDao.selectFileById(fileId);

            String path = SystemConstant.USER_FILE_PATH + folder.getPath() + folder.getName() +
                    "\\" + cloudFile.getName();

            File file = new File(path);
            if (file.exists())
                file.delete();
            cloudFileDao.deleteFileById(fileId);

            sysMsg = "删除成功";
        } catch (Exception e) {
            sysMsg = "系统异常";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }

        return "/view/file?dir=" + folderId;
    }

    /**
     * @param folderId
     * @param session
     * @return
     */
    @RequestMapping("/deleteDir")
    public String folderDelete(@RequestParam("folder") String folderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String sysMsg = "";
        try {
            Folder folder = folderDao.selectFolderById(folderId);
            if (folder.getCreateUser().getId() != user.getId()) {
                session.setAttribute("sysMsg", "用户未登录");
                return "redirect:/jsp/index.jsp";
            }
            File target = new File(SystemConstant.USER_FILE_PATH + "\\" + folder.getPath() + folder.getName());

            folder.setPath(SystemConstant.tosqlstring(folder.getPath()));
            //找出所有的子目录///
            List<Folder> targetFolder = folderDao.selectAllFolderByFatherFolder(folder);
            //删除根目录下的文件
            cloudFileDao.deleteFileByFolderId(folder.getId());
            //删除子目录下的文件和文件夹
            for (Folder f : targetFolder) {
                cloudFileDao.deleteFileByFolderId(f.getId());
                folderDao.deleteFolderById(f.getId());
            }
            //最后删除目标文件夹
            folderDao.deleteFolderById(folderId);
            //服务器执行删除实际文件夹
            if (target.exists()) {
                SystemConstant.deleteDir(target);
                if (!target.exists()) {
                    sysMsg = "删除成功";

                } else {
                    sysMsg = "删除失败，请重试";
                }
            } else {
                sysMsg = "删除失败";
            }
        } catch (Exception e) {
            sysMsg = "系统异常";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }
        return "/view/file?dir=0";
    }

    /**
     * @param folderId
     * @param fileId
     * @param session
     * @return
     */
    @RequestMapping("/setAccess")
    public String accessFile(@RequestParam("folder") String folderId, @RequestParam("file") String fileId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("sysMsg", "请重新登录");
            return "redirect:/jsp/index.jsp";
        }

        String sysMsg = "";
        try {
            Folder folder;
            if (folderId == null)
                folder = folderDao.selectRootFolderByUserId(user.getId());
            else
                folder = folderDao.selectFolderById(folderId);
            CloudFile target = cloudFileDao.selectFileById(fileId);
            Map<String, Object> params = new HashMap<>();
            if (target.getStatus() == 0) {
                sysMsg = "成功分享该文件";
                params.put("access", 1);
            } else {
                sysMsg = "已取消该文件分享";
                params.put("access", 0);
            }
            params.put("fileId", target.getId());

            cloudFileDao.changeFileAccessByFileId(params);

        } catch (Exception e) {
            sysMsg = "系统异常";

            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);

        }
        return "/view/file?dir=" + folderId;
    }
}
