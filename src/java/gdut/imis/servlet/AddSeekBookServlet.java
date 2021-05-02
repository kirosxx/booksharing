package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Seekbook;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AddSeekBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        Seekbook sb=new Seekbook();
        sb.setUser_id(req.getParameter("user_id"));
        sb.setBookname(req.getParameter("bookname"));
        sb.setPhoto(req.getParameter("photo"));
        sb.setContent(req.getParameter("content"));
        sb.setSendtime(new Date());

        SqlSession session = MybatisUtils.getSession();
        int insert = session.insert("gdut.imis.addSeekBook", sb);
        //如果没有commit，数据库是不会插入数据的
        session.commit();
        session.close();

        String registerJson = mapper.writeValueAsString(insert);
        PrintWriter out = resp.getWriter();
        out.write(registerJson);
        out.close();

    }
}
