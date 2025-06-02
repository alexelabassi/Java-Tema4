package Ex3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class WorkerThread implements Runnable {
    private final SimpleConnectionPool pool;
    private final String message;
    private static final Random RANDOM = new Random();

    public WorkerThread(SimpleConnectionPool pool, String message) {
        this.pool = pool;
        this.message = message;
    }

    @Override
    public void run() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = pool.getConnection();

            stmt = conn.prepareStatement("INSERT INTO Log(message) VALUES (?)");
            stmt.setString(1, message);
            stmt.executeUpdate();

            Thread.sleep(100 + RANDOM.nextInt(401));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
            }
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }
}
