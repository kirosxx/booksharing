package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Chatroom;
import gdut.imis.domain.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatRoomAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        SqlSession session = MybatisUtils.getSession();

        Chatroom cr=new Chatroom();
        cr.setTouserid(req.getParameter("touserid"));
        cr.setFromuserid(req.getParameter("fromuserid"));
        cr.setTextcontent(req.getParameter("textcontent"));
        cr.setMsgtype(req.getParameter("msgtype"));
        cr.setSendtime(new Date());
        int insert= session.insert("addChatRoom",cr);
        if(insert<0){
            out.write(mapper.writeValueAsString(0));
        }else {
            User u=new User();
            u.setId(cr.getFromuserid());
            u=session.selectOne("findUser",u);

            session.commit();
            session.close();

            String registerJson = mapper.writeValueAsString(cr);
            registerJson = registerJson.substring(0, registerJson.length() - 1);
            registerJson += ",\"avatar\":\"" + u.getAvatar() + "\",\"username\":\"" + u.getUsername() + "\"}";

            out.write(registerJson);
        }
        out.close();
    }
}
