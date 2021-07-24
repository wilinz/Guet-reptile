<%@ page import="com.jacaranda.api.mpapi.Spider" %>
<%--
请求类型参数解释：
1   获取成绩
2   获取课表
3   获取学分
4   获取考试安排
5   已选课程
--%>
<%@ page contentType="application/Json;charset=UTF-8" language="java" %>
<%String username = request.getParameter("username");%>
<%String password = request.getParameter("password");%>
<%String us = request.getParameter("us");%>
<%String pwd = request.getParameter("pwd");%>
<%String requestType = request.getParameter("type");%>
<%if (requestType.equals("1")){//获取成绩
    String json = Spider.get_grade(username,password,us,pwd);
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    out.print(json);
}else if (requestType.equals("2")){//获取课表
    String json = Spider.get_courses(username,password,us,pwd);
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    out.print(json);
}else if (requestType.equals("3")){//获取学分
    String json = Spider.get_credit(username,password,us,pwd);
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    out.print(json);
}else if (requestType.equals("4")){//获取考试安排
    String json = Spider.get_exam(username,password,us,pwd);
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    out.print(json);
}else if (requestType.equals("5")){
    String json = Spider.have_select(username,password,us,pwd);
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    out.print(json);
}%>