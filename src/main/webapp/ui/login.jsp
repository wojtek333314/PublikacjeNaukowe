<%@ page import ="java.sql.*" %>
<%
    String userid = request.getParameter("uname");
    String pwd = request.getParameter("pass");
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://sql.rdl.pl:3306/wojtek14_publikacjenaukowe",
            "wojtek14_admin", "5555555a");
    Statement st = con.createStatement();
    ResultSet rs;
    rs = st.executeQuery("select * from user");
    while (rs.next()) {
         out.println(rs.getString("login"));
        out.println("/");
    }
%>