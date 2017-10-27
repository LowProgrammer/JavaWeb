<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.henu.feifei.Page" %>
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":/"+request.getServerPort()+path+"/";
	
%>
<%
String currentPage = request.getParameter("currPage");
String action = request.getParameter("action");
int currPage = 1;
if(currentPage !=null){
	currPage = Integer.parseInt(currentPage);
}
Page p = new Page();
if(action!=null){
	if(action.equals("before")){
		p.beforePage();
		
	}
	if(action.equals("next")){
		p.nextPage();
	}
}
out.println(p.getCurrentPage());


%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="linkpage" class="com.henu.feifei.Page"  scope="page"></jsp:useBean>
	<%
		linkpage.setHasBefore(true);
		linkpage.setHasNext(true);
		linkpage.setTotalRows(100);
		linkpage.setPageSize(10);
		linkpage.setPageURL("page.jsp");
	%>
	<jsp:getProperty property="linkHTML" name="linkpage"/>
	<jsp:getProperty property="currentPage" name="linkpage"/>
	<jsp:getProperty property="totalPage" name="linkpage"/>
</body>
</html>