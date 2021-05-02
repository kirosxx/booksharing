package gdut.imis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdut.imis.DBUtils.MybatisUtils;
import gdut.imis.domain.Chatroom;
import gdut.imis.domain.Lendbook;
import gdut.imis.domain.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ChatListServlet extends HttpServlet {

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

        Chatroom cr=new Chatroom();

        cr.setTouserid(touserid);
        List<Chatroom> l1= session.selectList("getChatListByTo",cr);

        cr.setFromuserid(touserid);
        List<Chatroom> l2= session.selectList("getChatListByFrom",cr);

        if(l1.size()==0 && l2.size()==0){
            String registerJson = mapper.writeValueAsString(2);
            out.write(registerJson);
        }else{
            for(int i=0;i<l2.size();i++) {
                l1.add(l2.get(i));
            }

            List<Chatroom> l=new LinkedList<>();

            for(int k=0;k<l1.size();k++){
                boolean add=true;
                for(int i=0;i<l.size();i++){
                    Chatroom c1=l1.get(k);             //要添加的：
                    String to1=c1.getTouserid();         //我给潘杰
                    String from1=c1.getFromuserid();     //潘杰给我
                                                         //我给东鹏
                                                         //东鹏给我

                    Chatroom c2=l.get(i);              //已存在的：
                    String to2=c2.getTouserid();         //我给东鹏
                    String from2=c2.getFromuserid();     //东鹏给我
                                                         //我给潘杰
                                                         //潘杰给我

                    if ((to1.equals(to2) && from1.equals(from2)) || (to1.equals(from2) && from1.equals(to2))){
                        add=false;
                        if (c1.getSendtime().getTime()>c2.getSendtime().getTime()){
                            l.set(i,c1);
                        }
                    }
                }
                if(add){
                    l.add(l1.get(k));
                }
            }
            ListSort(l);

            List all=new LinkedList();
            for(int j=0;j<l.size();j++){
                User u=new User();
                if (l.get(j).getTouserid().equals(touserid)){
                    u.setId(l.get(j).getFromuserid());
                }else{
                    u.setId(l.get(j).getTouserid());
                }
                u=session.selectOne("findUser",u);

                String registerJson = mapper.writeValueAsString(l.get(j));
                registerJson = registerJson.substring(0, registerJson.length() - 1);
                registerJson += ",\"avatar\":\"" + u.getAvatar() + "\",\"username\":\"" + u.getUsername() + "\"}";
                all.add(registerJson);
            }
            out.write(all.toString());
        }
        session.close();
        out.close();
    }

    private static void ListSort(List<Chatroom> list) {
        Collections.sort(list, new Comparator<Chatroom>() {
            @Override
            public int compare(Chatroom o1, Chatroom o2) {
                try {
                    Date dt1 = o1.getSendtime();
                    Date dt2 = o2.getSendtime();
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
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
