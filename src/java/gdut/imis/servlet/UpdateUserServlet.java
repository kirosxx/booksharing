package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        User user = new User();
        user.setId(req.getParameter("user_id"));

        SqlSession session = MybatisUtils.getSession();
        user=session.selectOne("gdut.imis.findUser",user);

        user.setUsername(req.getParameter("username"));
        user.setPhone(req.getParameter("phone"));
        user.setWechatID(req.getParameter("wechatID"));
        user.setClassroom(req.getParameter("classroom"));
        user.setPassword(req.getParameter("password"));

        int update=session.update("updateUser",user);
        session.commit();
        session.close();

        if(update<0){
            String registerJson = mapper.writeValueAsString(update);
            out.write(registerJson);
        }else{
            String registerJson = mapper.writeValueAsString(user);
            out.write(registerJson);
        }
        out.close();
    }
}
