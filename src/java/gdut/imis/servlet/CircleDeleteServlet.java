package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Lendbook;
import gdut.imis.domain.Seekbook;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CircleDeleteServlet extends HttpServlet {

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

        String id=req.getParameter("id");
        String type=req.getParameter("type");

        SqlSession session = MybatisUtils.getSession();
        int delete=2;
        if(type.equals("seek")){
            Seekbook sb=new Seekbook();
            sb.setId(id);
            delete=session.delete("gdut.imis.deleteSeekBook",sb);
        }else{
            Lendbook lb=new Lendbook();
            lb.setId(id);
            session.delete("gdut.imis.deleteRelatedCollect",lb);   //先删除与外键互相关联的数据
            session.commit();
            delete=session.delete("gdut.imis.deleteLendBook",lb);  //再删除这本书
        }
        session.commit();
        session.close();

        String registerJson = mapper.writeValueAsString(delete);
        PrintWriter out = resp.getWriter();
        out.write(registerJson);
        out.close();
    }
}
