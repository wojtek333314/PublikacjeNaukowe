package com.sciencepublications.controllers;

import com.sciencepublications.MailMail;
import com.sciencepublications.models.*;
import com.sciencepublications.util.HibernateUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    MailMail mailMail;

    private static final int PASSWORD_MIN_LENGTH = 4;

    @RequestMapping(value = "/")
    public String home() {
        return "publications";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "logininput", required = false) String login,
                        @RequestParam(value = "passwordinput", required = false) String password,
                        ModelMap model) {
        if (login == null && password == null) {
            return "login";
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        for (UserEntity userEntity : users) {
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login) && userEntity.getConfirmed() == 1) {
                model.addAttribute("result", true);
                model.addAttribute("userId", userEntity.getId());
                if (userEntity.getRole().equals("ADMIN")) {
                    System.out.println("Is admin!");
                    model.addAttribute("isAdmin", true);
                }
                System.out.println(userEntity.getRole() + ":" + userEntity.getLogin() + " with id:" + userEntity.getId() + " is logged in!");
                break;
            } else {
                model.addAttribute("result", false);
            }
        }
        session.close();
        return "login";
    }

    @RequestMapping(value = "/publications")
    public String publications(ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<PublicationEntity> publications = new ArrayList<PublicationEntity>(session.createCriteria(PublicationEntity.class).list());
        model.addAttribute("publications", publications);
        session.close();
        return "publications";
    }

    @RequestMapping(value = "/publication{id}")
    public String publicationDetails(@RequestParam("id") int id, ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PublicationEntity publicationEntity = (PublicationEntity) session.get(PublicationEntity.class, id);
        model.addAttribute("publication", publicationEntity);
        session.close();
        return "publicationDetails";
    }

    @RequestMapping(value = "/edit{id}")
    public String publicationEdit(@RequestParam("id") int id, ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PublicationEntity publicationEntity = (PublicationEntity) session.get(PublicationEntity.class, id);
        model.addAttribute("publication", publicationEntity);
        session.close();
        return "editPublication";
    }

    @RequestMapping(value = "/addTypePublications")
    public String addTypePublications() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
        return "addTypePublications";
    }

    @RequestMapping(value = "/deleteTypePublications")
    public String deleteTypePublications(HttpServletRequest request) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<TypeOfPublicationsEntity> typeOfPublicationsEntities = new ArrayList<TypeOfPublicationsEntity>(session.createCriteria(TypeOfPublicationsEntity.class).list());
        request.setAttribute("typesList", typeOfPublicationsEntities);
        request.setAttribute("type", typeOfPublicationsEntities.get(0).getName());
        session.close();
        return "deleteTypePublications";
    }

    @RequestMapping(value = "/fileDownload")
    public String fileDownload() {
        return "fileDownload";
    }

    @RequestMapping(value = "/create")
    public String createPublication(HttpServletRequest request) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<TypeOfPublicationsEntity> typeOfPublicationsEntities = new ArrayList<TypeOfPublicationsEntity>(session.createCriteria(TypeOfPublicationsEntity.class).list());
        request.setAttribute("typesList", typeOfPublicationsEntities);
        request.setAttribute("type", typeOfPublicationsEntities.get(0).getName());
        session.close();
        return "createPublication";
    }


    @RequestMapping(value = "/getPublication", method = RequestMethod.GET)
    @ResponseBody
    public String getPublicationDetailsView(@RequestParam("id") String id) {
        String result = "";
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<PublicationEntity> publicationEntities = new ArrayList<PublicationEntity>(session.createCriteria(PublicationEntity.class).list());
        for (PublicationEntity publicationEntity : publicationEntities) {
            if (publicationEntity.getPublicationId() == Integer.parseInt(id)) {
                result = publicationEntity.getJson();
                break;
            }
        }
        session.close();
        return result;
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    @ResponseBody
    public String getAttributesFromTypeOfPublication(@RequestParam("typePublicationId") String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String result = "";
        ArrayList<AttrubuteEntity> attributes = new ArrayList<AttrubuteEntity>(session.createCriteria(AttrubuteEntity.class).list());
        int pos = 0;
        for (AttrubuteEntity attrubuteEntity : attributes) {
            if (attrubuteEntity.getIdType() == Integer.parseInt(id)) {
                result += " <div class=\"form-group\">\n" +
                        "<p id=\"label" + pos + "\" for=\"description\"><b>" + attrubuteEntity.getName() + ":</b></p>\n" +
                        "<textarea id=\"textarea" + pos + "\" class=\"form-control\" rows=\"3\" id=\"" + attrubuteEntity.getName() + "\" " +
                        "name=\"" + attrubuteEntity.getName() + "\"></textarea>\n" +
                        "</div>";
                pos++;
            }
        }
        session.close();
        return result;
    }


    @RequestMapping(value = "/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/register")
    public String register(@RequestParam(value = "username", required = false) String login,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "email", required = false) String email,
                           ModelMap modelMap) {
        if (login == null && password == null) {
            return "register";
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            modelMap.addAttribute("passwordTooShort", "<p style=\"color:red;\">Password is too short!</p>");
            return "register";
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        for (UserEntity userEntity : users) {
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login)) {
                modelMap.addAttribute("userExist", "<p style=\"color:red;\">User is exist!</p>");
                session.close();
                return "register";
            }
        }
        Transaction transaction = session.beginTransaction();
        String randomUUID = UUID.randomUUID().toString();
        Query query = session
                .createSQLQuery("INSERT INTO user (login, password) VALUES (:login1, :password1)");
        query.setParameter("login1", login);
        query.setParameter("password1", password);
        query.executeUpdate();
        Long lastId;
        lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();

        UserConfirmEntity userConfirmEntity = new UserConfirmEntity();
        userConfirmEntity.setHash(randomUUID);
        userConfirmEntity.setUserId(lastId.intValue());
        System.out.println("new user id:" + lastId);
        session.save(userConfirmEntity);
        transaction.commit();
        session.close();

        mailMail.sendMail("sciencepublications@no-spam.com",
                email,
                "Confirmation link",
                "Click to confirm account: " + "http://panel11.mydevil.net:5521/confirm?hash=" + randomUUID);
        return "login";
    }

    @RequestMapping(value = "/uploadFile")
    public void uploadFileHandler(@RequestParam("title") String title,
                                  @RequestParam("json") String json,
                                  @RequestParam("file") MultipartFile file,
                                  @RequestParam("userId") Integer userId,
                                  ModelMap model,
                                  HttpServletResponse httpServletResponse) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                Session session = HibernateUtil.getSessionFactory().openSession();
                Criteria criteria = session.createCriteria(UserEntity.class);
                criteria.add(Restrictions.eq("id", userId));
                UserEntity user = (UserEntity) criteria.uniqueResult();

                Transaction transaction = session.beginTransaction();

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFile(bytes);
                fileEntity.setFilename(file.getOriginalFilename());
                Integer fileId = (Integer) session.save(fileEntity);
                transaction.commit();
                session.close();

                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                PublicationEntity publicationEntity = new PublicationEntity();
                publicationEntity.setAuthorId(user.getId());
                publicationEntity.setAuthorName(user.getLogin());
                publicationEntity.setTitle(title);
                publicationEntity.setFileId(fileId);
                publicationEntity.setJson(json);

                Integer id = (Integer) session.save(publicationEntity);
                model.addAttribute("publication", publicationEntity);
                transaction.commit();
                session.close();
                httpServletResponse.sendRedirect("/publications");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/download/{documentId}")
    public String download(@PathVariable("documentId")
                                   Integer documentId, HttpServletResponse response) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            FileEntity fileEntity = (FileEntity) session.get(FileEntity.class, documentId);
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileEntity.getFilename() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            out.write(fileEntity.getFile());
            out.flush();
            out.close();
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @RequestMapping(value = "/delete{id}")
    public String deletePublication(@RequestParam("id") int id, ModelMap model) {
        System.out.println("I am deleting:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        PublicationEntity publicationEntity = (PublicationEntity) session.get(PublicationEntity.class, id);
        System.out.println("Found publicationEntity to delete!" + publicationEntity.toString());
        session.delete(publicationEntity);
        transaction.commit();
        session.close();
        return "redirect:publications";
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public String addType(@RequestParam("name") String name,
                          @RequestParam(value = "item") String[] dane,
                          ModelMap model) {
        try {
            Session session;
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO TypeOfPublications ( name) VALUES (:name)");
            query.setParameter("name", name);
            query.executeUpdate();
            transaction.commit();

            transaction = session.beginTransaction();
            Long lastId;
            lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
            transaction.commit();
            int position = 0;
            for (String aDane : dane) {
                transaction = session.beginTransaction();

                query = session.createSQLQuery("INSERT INTO Attrubute (id_type,name) VALUES (:id_type,:name)");
                query.setParameter("name", aDane);
                query.setParameter("id_type", lastId);
                query.executeUpdate();
                transaction.commit();
                position++;
            }
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "addTypePublications";
    }

    @RequestMapping(value = "/update{id}", method = RequestMethod.POST)
    public String updatePublication(@PathVariable("id") Integer id,
                                    @RequestParam("title") String title,
                                    @RequestParam("json") String json
    ) {
        try {
            System.out.println("title:" + title);
            System.out.println("id:" + id);
            System.out.println("HHHH:" + json);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            PublicationEntity publicationEntityti = (PublicationEntity) session.get(PublicationEntity.class, id);

            if (!title .equals("") ) {
                publicationEntityti.setTitle(title);
            }
            publicationEntityti.setJson(json);
            session.update(publicationEntityti);
            transaction.commit();
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:publications";
    }

    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    public String deleteType(@RequestParam("types") Integer id,
                             ModelMap model
    ) {
        try {
            System.out.println("I am deleting type:" + id);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            TypeOfPublicationsEntity typeOfPublicationsEntity = (TypeOfPublicationsEntity) session.get(TypeOfPublicationsEntity.class, id);
            session.delete(typeOfPublicationsEntity);
            transaction.commit();
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:deleteTypePublications";
    }

    @RequestMapping(value = "/confirm{hash}")
    public String confirmUser(@RequestParam("hash") String hash,
                              ModelMap model, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("I am confirming hash:" + hash);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        ArrayList<UserConfirmEntity> userConfirmEntities = new ArrayList<UserConfirmEntity>(session.createCriteria(UserConfirmEntity.class).list());

        for (UserConfirmEntity userConfirmEntity : userConfirmEntities) {
            if (userConfirmEntity.getHash().equals(hash)) {
                for (UserEntity userEntity : users) {
                    if (userEntity.getId() == userConfirmEntity.getUserId()) {
                        userEntity.setConfirmed(1);
                        session.update(userEntity);
                        session.delete(userConfirmEntity);
                        transaction.commit();
                        model.addAttribute("isConfirmed", true);
                        session.close();
                        httpServletResponse.sendRedirect("/login");
                        return "login";
                    }
                }
            }
        }
        transaction.commit();
        session.close();
        return "login";
    }

}
