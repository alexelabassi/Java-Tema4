package Ex3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        int poolSize = 5;
        int numThreads = 10;

        SimpleConnectionPool pool = new SimpleConnectionPool(poolSize);
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            String msg = "Mesaj de la thread " + (i + 1);
            Thread t = new Thread(new WorkerThread(pool, msg));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM Log");
            rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Numar total de inregistrari in Log: " + count);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) pool.releaseConnection(conn);
        }
    }
}
