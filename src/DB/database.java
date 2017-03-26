package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class database{

    // 定义连接所需的字符串
    // 192.168.0.X是本机地址(要改成自己的IP地址)，1521端口号，XE是精简版Oracle的默认数据库名
    private static String USERNAMR = "SCOTT";
    private static String PASSWORD = "12345";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";

    // 创建一个数据库连接
    protected Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    // 创建一个结果集对象
    ResultSet rs = null;

    public Connection getConnection() {     //建立连接
        try {
            Class.forName(DRVIER);
            connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("成功连接数据库");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return connection;
    }

    public void adduser(String username){
        connection = getConnection();

        String adduser="insert into USER_INFO (user_name) values (?)";

        try{
            pstm = connection.prepareStatement(adduser);
            pstm.setString(1,username);
            pstm.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public boolean check(String name){  //检查用户是否曾经登录
        connection =getConnection();
        String checkauser="select * from USER_INFO where user_name=?";

        try{
            pstm = connection.prepareStatement(checkauser);
            pstm.setString(1,name);
            ResultSet result = pstm.executeQuery();
            if(result.next()) {
                return true;
            }
            else
                return false;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public void CLose() {     //释放资源
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}