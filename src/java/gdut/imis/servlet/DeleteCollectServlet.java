package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Collect;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteCollectServlet extends HttpServlet {

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

        Collect c=new Collect();
        c.setUser_id(req.getParameter("user_id"));
        c.setBook_id(req.getParameter("book_id"));

        SqlSession session = MybatisUtils.getSession();
        int insert = session.insert("gdut.imis.deleteCollect", c);
        session.commit();
        session.close();

        String registerJson = mapper.writeValueAsString(insert);
        PrintWriter out = resp.getWriter();
        out.write(registerJson);
        out.close();

    }
}
