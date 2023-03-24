package tema8Ejercicio1_BD_pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author FERNANDO ROMERO DE ÁVILA - 1º DAW 2022-23
 */
public class Tema8Ejercicio1_BD_pruebas {

    static void mostrarMenu() {

	System.out.println();
	System.out.println("-------MENÚ--------");
	System.out.println("Seleccione una opción");
	System.out.println("1. Mostrar todo el contenido de la tabla");
	System.out.println("2. Mostrar los nombres de las personas mayores de edad");
	System.out.println("3. Mostrar la edad de una persona con un id tecleado por el usuario");
	System.out.println("4. Insertar una nueva persona, pidiéndole id, nombre y edad al usuario");
	System.out.println("5. Borrar la persona con un id tecleado por el usuario");
	System.out.println("6. Actualizar la edad de una persona con un id tecleado por el usuario");
	System.out.println("0. Salir");

    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {

	int opcion;
	Scanner s = new Scanner(System.in);

	do {
	    mostrarMenu();
	    opcion = s.nextInt();

	    String url = "jdbc:mysql://127.0.0.1/prueba";
	    String user = "root";
	    String password = "Solana12023";

	    try {

		int idp;
		String nomp;
		int edadp;

		Connection con = DriverManager.getConnection(url, user, password);

		switch (opcion) {
		case 1:
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM persona");

		    while (rs.next()) {
			idp = rs.getInt("id");
			nomp = rs.getString("nombre");
			edadp = rs.getInt("edad");
			System.out.println("ID: " + idp + ", Nombre: " + nomp + ", Edad: " + edadp);
		    }
		    stmt.close();
		    rs.close();
		    break;
		case 2:
		    PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM persona WHERE edad>?");
		    sel_pstmt.setInt(1, 18);
		    ResultSet rs_sel = sel_pstmt.executeQuery();

		    while (rs_sel.next()) {
			idp = rs_sel.getInt("id");
			nomp = rs_sel.getString("nombre");
			edadp = rs_sel.getInt("edad");
			System.out.println("Nombre: " + nomp);
		    }
		    sel_pstmt.close();
		    rs_sel.close();
		    break;
		case 3:
		    System.out.println("Selecciona un ID");
		    idp = s.nextInt();

		    PreparedStatement sel_pstmt2 = con.prepareStatement("SELECT * FROM persona WHERE id=?");
		    sel_pstmt2.setInt(1, idp);
		    ResultSet rs_sel2 = sel_pstmt2.executeQuery();

		    while (rs_sel2.next()) {
			idp = rs_sel2.getInt("id");
			nomp = rs_sel2.getString("nombre");
			edadp = rs_sel2.getInt("edad");
			System.out.println("Edad: " + edadp + " años");
		    }
		    sel_pstmt2.close();
		    rs_sel2.close();
		    break;
		case 4:
		    System.out.println("Inserta ID");
		    idp = s.nextInt();
		    System.out.println("Inserta nombre");
		    nomp = s.next();
		    System.out.println("Inserta edad");
		    edadp = s.nextInt();
		    
		    PreparedStatement ins_pstmt = con
			    .prepareStatement("INSERT INTO persona (id, nombre, edad) VALUES (?, ?, ?)");
		    ins_pstmt.setInt(1, idp);
		    ins_pstmt.setString(2, nomp);
		    ins_pstmt.setInt(3, edadp);
		    int rowsInserted = ins_pstmt.executeUpdate();
		    ins_pstmt.close();
		    break;
		case 5:
		    System.out.println("Inserta ID");
		    idp = s.nextInt();
		    
		    PreparedStatement dele_pstmt = con.prepareStatement("DELETE FROM persona WHERE id = ?");
		    dele_pstmt.setInt(1, idp);
		    int rowsDeleted = dele_pstmt.executeUpdate();
		    dele_pstmt.close();
		    break;
		case 6:
		    System.out.println("Inserta ID");
		    idp = s.nextInt();
		    System.out.println("Inserta edad");
		    edadp = s.nextInt();
		    
		    PreparedStatement upd_pstmt = con.prepareStatement("UPDATE persona SET edad = ? WHERE id = ?");
		    upd_pstmt.setInt(2, idp);
		    upd_pstmt.setInt(1, edadp);
		    int rowsUpdated = upd_pstmt.executeUpdate();
		    upd_pstmt.close();
		    break;
		case 0:
		    System.out.println("Fin del programa");
		    break;
		default:
		    System.out.println("Opción no válida");
		}
		con.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	} while (opcion != 0);

    }

}
