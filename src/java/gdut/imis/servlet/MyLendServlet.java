package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Lendbook;
import gdut.imis.domain.Seekbook;
import gdut.imis.domain.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class MyLendServlet extends HttpServlet {

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

        Lendbook lb=new Lendbook();
        lb.setUser_id(req.getParameter("user_id"));

        SqlSession session = MybatisUtils.getSession();
        List<Lendbook> l1= session.selectList("MyLend",lb);
        PrintWriter out = resp.getWriter();
        if(l1.size()==0){
            String registerJson = mapper.writeValueAsString(2);
            out.write(registerJson);
        }else{
            List all=new LinkedList();
            for(int i=0;i<l1.size();i++){
                User u = new User();
                u.setId(l1.get(i).getUser_id());
                User u1 = session.selectOne("findUser", u);
                String registerJson = mapper.writeValueAsString(l1.get(i));
                registerJson = registerJson.substring(0, registerJson.length() - 1);
                registerJson += ",\"avatar\":\"" + u1.getAvatar() + "\",\"username\":\"" + u1.getUsername() + "\"}";
                all.add(registerJson);
            }
            out.write(all.toString());
        }
        out.close();
    }


}
