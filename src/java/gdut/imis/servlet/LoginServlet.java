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

public class LoginServlet extends HttpServlet {

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

        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        SqlSession session = MybatisUtils.getSession();
        User user = session.selectOne("gdut.imis.User", phone);


        if (user != null&&user.getPassword().equals(password)){
            String registerJson = mapper.writeValueAsString(user);
            out.write(registerJson);
        }else if (user != null && !user.getPassword().equals(password)){
            String registerJson = mapper.writeValueAsString(2);
            out.write(registerJson);
        }else {
            String registerJson = mapper.writeValueAsString(3);
            out.write(registerJson);
        }
        out.close();


    }


}
