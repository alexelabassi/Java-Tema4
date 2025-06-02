package Ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleConnectionPool {
    private final List<Connection> pool = new ArrayList<Connection>();

    public SimpleConnectionPool(int size) {
        for (int i = 0; i < size; i++) {
            try {
                String url = "jdbc:postgresql://localhost:5432/postgres";
                String user = "postgres";
                String password = "parola";
                Connection conn = DriverManager.getConnection(url, user, password);
                pool.add(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() throws InterruptedException {
        while (pool.isEmpty()) {
            wait();
        }
        Connection conn = pool.remove(pool.size() - 1);
        return conn;
    }
    public synchronized void releaseConnection(Connection conn) {
        pool.add(conn);
        notifyAll();
    }
    public synchronized void closeAll() {
        for (Connection conn : pool) {
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        pool.clear();
    }
}
