package com.csw.controller;

import com.csw.dao.FolderDao;
import com.csw.dao.UserDao;
import com.csw.entity.Folder;
import com.csw.entity.User;
import com.csw.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class LoginAndRegister {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FolderDao folderDao;
    String path = SystemConstant.USER_FILE_PATH;

    /**
     * @param username
     * @param password
     * @param session
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session, HttpServletRequest request) throws IOException {
        System.out.println("进入登录方法");
        String sysMsg = "";
        try {
            User user = userDao.selectUserByUsername(username);
            System.out.println("user}" + user);
            if (user == null || !user.getPassword().equals(password)) {
                sysMsg = "用户不存在";
                return "redirect:/jsp/index.jsp";
            } else {
                session.setAttribute("user", user);
                System.out.println("即将进行跳转");
                return "redirect:/jsp/view/main.jsp";
            }
        } catch (Exception e) {
            sysMsg = "登录报错";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }
        return "redirect:/jsp/index.jsp";
    }

    /**
     * @param username
     * @param password
     * @param rePassword
     * @param email
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/regist")
    public String Register(String username, String password, String rePassword, String email, HttpSession session) throws IOException {
        System.out.println("进入注册方法");
        User user2 = userDao.selectUserByUsername(username);
        if (username == null || password == null || rePassword == null || email == null) {
            session.setAttribute("sysMsg", "请检查后再次输入，不能输入空值");
            System.out.println("1");
            return "redirect:/jsp/index.jsp";
        } else if (user2 != null) {
            session.setAttribute("sysMsg", "已存在同样的用户名");
            return "redirect:/jsp/index.jsp";
        } else if (!password.equals(rePassword)) {
            session.setAttribute("sysMsg", "两次输入的密码不匹配");
            System.out.println("7");
            return "redirect:/jsp/index.jsp";
        }

        String sysMsg = "";
        try {
            User user = new User(UUID.randomUUID().toString(), username, password, email, new Date(), 0);
            System.out.println("user]" + user);
            userDao.addUser(user);
            sysMsg = "用户添加成功";
            //创建用户根目录
            folderDao.addRootFolder(new Folder(UUID.randomUUID().toString(), username, null, user, path));
            //本地创建
            File folder = new File(path + "\\" + user.getUsername());
            if (!folder.exists())
                folder.mkdirs();
        } catch (Exception e) {
            sysMsg = "用户添加失败";
            e.printStackTrace();
        } finally {
            session.setAttribute("sysMsg", sysMsg);
        }
        System.out.println("8");
        return "redirect:/jsp/index.jsp";
    }
}
