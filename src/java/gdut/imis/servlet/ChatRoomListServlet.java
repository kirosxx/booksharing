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
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatRoomListServlet extends HttpServlet {

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

        String touserid=req.getParameter("touserid");
        String fromuserid=req.getParameter("fromuserid");

        Chatroom cr=new Chatroom();

        cr.setTouserid(touserid);
        cr.setFromuserid(fromuserid);
        List<Chatroom> l1= session.selectList("getChatRoomList",cr);

        cr.setTouserid(fromuserid);
        cr.setFromuserid(touserid);
        List<Chatroom> l2= session.selectList("getChatRoomList",cr);

        if(l1.size()==0 && l2.size()==0){
            String registerJson = mapper.writeValueAsString(2);
            out.write(registerJson);
        }else{
            for(int i=0;i<l2.size();i++) {
                l1.add(l2.get(i));
            }
            ListSort(l1);
            List all=new LinkedList();
            for(int j=0;j<l1.size();j++){
                User u=new User();
                u.setId(l1.get(j).getFromuserid());
                u=session.selectOne("findUser",u);

                String registerJson = mapper.writeValueAsString(l1.get(j));
                registerJson = registerJson.substring(0, registerJson.length() - 1);
                registerJson += ",\"avatar\":\"" + u.getAvatar() + "\",\"username\":\"" + u.getUsername() + "\"}";
                all.add(registerJson);
            }
            out.write(all.toString());
        }
        out.close();
    }

    private static void ListSort(List<Chatroom> list) {
        Collections.sort(list, new Comparator<Chatroom>() {
            @Override
            public int compare(Chatroom o1, Chatroom o2) {
                try {
                    Date dt1 = o1.getSendtime();
                    Date dt2 = o2.getSendtime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
