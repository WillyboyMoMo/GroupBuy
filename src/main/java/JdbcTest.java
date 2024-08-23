import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {

    public static void main(String[] args) {
        // 資料庫連接資訊
    	String url = "jdbc:sqlserver://localhost:1433;databaseName=TSQL;encrypt=false";
        String user = "sa";
        String password = "Pa55w.rd";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 加載 JDBC 驅動
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 建立連接
            connection = DriverManager.getConnection(url, user, password);

            // 建立 Statement 物件來執行 SQL 查詢
            statement = connection.createStatement();

            // 執行查詢並獲取結果
            String sql = "SELECT * FROM HR.Employees";
            resultSet = statement.executeQuery(sql);

            // 迭代結果並輸出
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("lastname"));
                System.out.println("Column2: " + resultSet.getInt("firstname"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 關閉資源
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
