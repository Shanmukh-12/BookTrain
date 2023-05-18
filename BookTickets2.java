package ticketBooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookTickets2
 */
@WebServlet("/BookTickets2")
public class BookTickets2 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside BookTickets");
		System.out.println("Booktickets-1");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String train = request.getParameter("train");
		String coach = request.getParameter("coach");
		String tdate = request.getParameter("tdate");
		String tcost = request.getParameter("tcost");
		String pname1 = request.getParameter("pname1");
		String pgender1 = request.getParameter("pgender1");
		String page1 = request.getParameter("page1");
		String pcost1 = request.getParameter("pcost1");
		String pname2 = request.getParameter("pname2");
		String pgender2 = request.getParameter("pgender2");
		String page2 = request.getParameter("page2");
		String pcost2 = request.getParameter("pcost2");
		String pname3 = request.getParameter("pname3");
		String pgender3 = request.getParameter("pgender3");
		String page3 = request.getParameter("page3");
		String pcost3 = request.getParameter("pcost3");
		System.out.println("Booktickets-2");
		PrintWriter pw = response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:postgresql://192.168.110.48:5432/plf_training?user=plf_training_admin&password=pff123");

			// inserting into tickets info
			PreparedStatement ps1 = con.prepareStatement(
					"insert into ticketsinfo (tkt_trn_no,tkt_tr_date,tkt_from,tkt_to,tkt_tr_class,tkt_totalfare) values(?,?,?,?,?,?)");
			System.out.println("Booktickets-3 " + request.getParameter("from"));
			System.out.println("Booktickets-3 " + request.getParameter("to"));
			System.out.println("Booktickets-3 " + request.getParameter("train"));
			System.out.println("Booktickets-3 " + request.getParameter("coach"));
			System.out.println("Booktickets-3 " + request.getParameter("tdate"));
			ps1.setInt(1, Integer.parseInt(train));
			System.out.println("Booktickets-4 " + Integer.parseInt(train));
			ps1.setString(2, tdate);
			ps1.setString(3, from);
			ps1.setString(4, to);
			ps1.setString(5, coach);
			System.out.println("Booktickets-5");
			ps1.setDouble(6, Double.parseDouble(tcost));
			System.out.println("Booktickets-6");
			int n = ps1.executeUpdate();
			pw.println(n + " rows inserted into ticketsinfo");
			System.out.println(n + " rows inserted into ticketsinfo");

			// Getting ticketId and pnr number
			ps1 = con.prepareStatement("select tkt_id,pnr_no from ticketsinfo order by tkt_id desc limit 1");
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int tk_id = rs1.getInt(1);
			int pnr_no = rs1.getInt(2);
			System.out.println("tkt_id " + tk_id);
			System.out.println("pnr num " + pnr_no);
			// inserting passenger-1 details
			ps1 = con.prepareStatement("insert into passengersinfo values(?,?,?,?,?,?)");
			ps1.setInt(1, tk_id);
			ps1.setInt(2, 1);
			ps1.setString(3, pname1);
			ps1.setInt(4, Integer.parseInt(page1));
			ps1.setString(5, String.valueOf(pgender1.charAt(0)));
			ps1.setDouble(6, Double.parseDouble(pcost1));
			int r1 = ps1.executeUpdate();
			System.out.println(r1 + " rows inserted into passengers info");

			// inserting passenger-2 details
			ps1 = con.prepareStatement("insert into passengersinfo values(?,?,?,?,?,?)");
			ps1.setInt(1, tk_id);
			ps1.setInt(2, 2);
			ps1.setString(3, pname2);
			ps1.setInt(4, Integer.parseInt(page2));
			ps1.setString(5, String.valueOf(pgender2.charAt(0)));
			ps1.setDouble(6, Double.parseDouble(pcost2));
			int r2 = ps1.executeUpdate();
			System.out.println(r2 + " rows inserted into passengers info");

			// inserting passenger-3 details
			ps1 = con.prepareStatement("insert into passengersinfo values(?,?,?,?,?,?)");
			ps1.setInt(1, tk_id);
			ps1.setInt(2, 3);
			ps1.setString(3, pname3);
			ps1.setInt(4, Integer.parseInt(page3));
			ps1.setString(5, String.valueOf(pgender3.charAt(0)));
			ps1.setDouble(6, Double.parseDouble(pcost3));
			int r3 = ps1.executeUpdate();
			System.out.println(r3 + " rows inserted into passengers info");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
