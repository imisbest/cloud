package com.csw.controller;

import com.csw.dao.CloudFileDao;
import com.csw.dao.FolderDao;
import com.csw.entity.CloudFile;
import com.csw.entity.Folder;
import com.csw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/view")
public class ViewController {
    @Autowired
    private FolderDao folderDao;
    @Autowired
    private CloudFileDao cloudFileDao;
    
    @RequestMapping("")
    public String mainPage(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/jsp/index.jsp";
        else
            return "redirect:/jsp/view/main.jsp";

    }

    /**
     *
     * @param folderId
     * @param session
     * @return
     */
    @RequestMapping("/file")
    public String filePage( String folderId, HttpSession session) {
        try {
            List<Folder> folderList = null;
            List<CloudFile> fileList = null;
            if (folderId == null) {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    return "redirect:/jsp/index.jsp";
                }
                Folder rootFolder = folderDao.selectRootFolderByUserId(user.getId());
                System.out.println("rootFolder]"+rootFolder);
                if (rootFolder != null) {
                    folderList = folderDao.selectFolderListByFolderId(rootFolder.getId());
                    fileList = cloudFileDao.selectFileListByFolderId(rootFolder.getId());
                }

            } else {
                folderList = folderDao.selectFolderListByFolderId(folderId);
                fileList = cloudFileDao.selectFileListByFolderId(folderId);
                Folder fatherFolder = folderDao.selectFatherFolderById(
                        folderDao.selectFolderById(folderId).getParentFolder());
                session.setAttribute("fatherFolder", fatherFolder);
            }
            System.out.println("folderId]"+folderId);
            session.setAttribute("folderId", folderId);
            session.setAttribute("folderList", folderList);
            session.setAttribute("fileList", fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/jsp/view/file.jsp";
    }

    /**
     *
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("/class")
    public String photoPage(String type, HttpSession session) {
        
        User user = (User) session.getAttribute("user");

        try {
            if (user == null) {
                return "redirect:/jsp/index.jsp";
            }
            
            List<CloudFile> fileList = null;
            if (type.equals("photo")) {
                fileList = cloudFileDao.selectPhotoByUserId(user.getId());
            } else if (type.equals("document")) {
                fileList = cloudFileDao.selectDocumentByUserId(user.getId());
            } else if (type.equals("movie")) {
                fileList = cloudFileDao.selectMovieByUserId(user.getId());
            } else if (type.equals("music")) {
                fileList = cloudFileDao.selectMusicByUserId(user.getId());
            } else if (type.equals("zip")) {
                fileList = cloudFileDao.selectZipByUserId(user.getId());
            }
            session.setAttribute("fileList", fileList);
            return "redirect:/jsp/view/class.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/jsp/view/class.jsp";
    }

    /**
     *
     * @param session
     * @return
     */
    @RequestMapping("/public")
    public String publicPage(HttpSession session) {
        
        try {
            List<CloudFile> fileList = cloudFileDao.selectPublicFile();
            session.setAttribute("fileList", fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/jsp/view/public.jsp";
    }
}
