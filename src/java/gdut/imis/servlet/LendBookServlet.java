package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
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

public class LendBookServlet extends HttpServlet {

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
        if (req.getParameter("page")==null){   //当页面参数不存在表示查询全部
            pageSize=1000;
        }else if (req.getParameter("pageSize")==null){  //当页面参数存在而页面大小不存在时，使用默认查询大小8

        }else{
            page = Integer.parseInt(req.getParameter("page"));
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("start",(page-1)*pageSize);
        param.put("end",(page-1)*pageSize+pageSize);

        SqlSession session = MybatisUtils.getSession();
        List<Lendbook> l= session.selectList("LendBook",param);
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
