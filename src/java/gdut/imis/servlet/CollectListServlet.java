package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Collect;
import gdut.imis.domain.Lendbook;
import gdut.imis.domain.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CollectListServlet extends HttpServlet {

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

        Collect c=new Collect();
        c.setUser_id(req.getParameter("user_id"));

        SqlSession session = MybatisUtils.getSession();
        List<Collect> l= session.selectList("selectCollect",c);

        if (l.size()==0){
            out.write(mapper.writeValueAsString(2));
        }else{
            List all=new LinkedList();
            for(int i=0;i<l.size();i++) {
                Lendbook lb = new Lendbook();
                lb.setId(l.get(i).getBook_id());
                lb = session.selectOne("selectLendBook", lb);

                User u = new User();
                u.setId(lb.getUser_id());
                u = session.selectOne("findUser", u);
                l.get(i).setUser_id(u.getId());

                String registerJson = mapper.writeValueAsString(l.get(i));
                registerJson = registerJson.substring(0, registerJson.length() - 1);

                registerJson += ",\"bookname\":\"" + lb.getBookname() + "\",\"photo\":\"" + lb.getPhoto() + "\",\"content\":\"" + lb.getContent() + "\",\"type\":\"" + lb.getType() + "\",\"category\":\"" + lb.getCategory() + "\",\"username\":\"" + u.getUsername() + "\",\"avatar\":\"" + u.getAvatar() + "\"}";
                all.add(registerJson);
            }
            out.write(all.toString());
        }
        session.close();
        out.close();
    }


}
