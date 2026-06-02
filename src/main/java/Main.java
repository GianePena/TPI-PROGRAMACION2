import config.HikariConnection;
import utilidades.Utilidades;

import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        String sql = new String(Files.readAllBytes(
                Paths.get("src/main/java/schema.sql")
        ));

        try (Connection conn = HikariConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            for (String statement : sql.split(";")) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            System.out.println("Schema creado correctamente");
        }

        Scanner sc = new Scanner(System.in);
        Utilidades.mostrarMenu(sc);
        sc.close();

    }

}
