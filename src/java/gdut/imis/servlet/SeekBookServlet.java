package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Seekbook;
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

public class SeekBookServlet extends HttpServlet {

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

        int page=1;
        int pageSize=8;
        System.out.println(req.getParameter("page"));
        System.out.println(req.getParameter("pageSize"));

        if(req.getParameter("page")==null){
            pageSize=1000;
        }else if(req.getParameter("pageSize")==null){

        }else{
            page = Integer.parseInt(req.getParameter("page"));
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("start",(page-1)*pageSize);
        param.put("end",(page-1)*pageSize+pageSize);

        System.out.println(page);
        System.out.println(pageSize);

        SqlSession session = MybatisUtils.getSession();
        List<Seekbook> l= session.selectList("SeekBook",param);
        PrintWriter out = resp.getWriter();
        if (l == null || l.size()==0){
            String registerJson = mapper.writeValueAsString(2);
            out.write(registerJson);
        }else {
            List all=new LinkedList();
            for (int i=0;i<l.size();i++) {
                User u = new User();
                u.setId(l.get(i).getUser_id());
                User u1 = session.selectOne("findUser", u);
                String registerJson = mapper.writeValueAsString(l.get(i));
                registerJson = registerJson.substring(0, registerJson.length() - 1);
                registerJson += ",\"avatar\":\"" + u1.getAvatar() + "\",\"username\":\"" + u1.getUsername() + "\",\"classroom\":\""+u1.getClassroom()+"\"}";
                all.add(registerJson);
            }
            out.write(all.toString());
        }
        out.close();
    }


}
