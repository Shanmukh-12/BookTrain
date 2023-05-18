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
 * Servlet implementation class FareCalculation
 */
@WebServlet("/FareCalculation")
public class FareCalculation extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String train = request.getParameter("train");
		String coach = request.getParameter("coach");
		String pname1 = request.getParameter("pname1");
		String pgender1 = request.getParameter("pgender1");
		String page1 = request.getParameter("page1");
		String pname2 = request.getParameter("pname2");
		String pgender2 = request.getParameter("pgender2");
		String page2 = request.getParameter("page2");
		String pname3 = request.getParameter("pname3");
		String pgender3 = request.getParameter("pgender3");
		String page3 = request.getParameter("page3");
		System.out.println(request.getParameter("from"));
		System.out.println(request.getParameter("to"));
		System.out.println(request.getParameter("train"));
		System.out.println(request.getParameter("coach"));

		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:postgresql://192.168.110.48:5432/plf_training?user=plf_training_admin&password=pff123");

			// Distance calculation
			PreparedStatement ps1 = con
					.prepareStatement("select std_distance from stationsdistancesinfo where fst_code=? and tst_code=?");
			ps1.setString(1, from);
			ps1.setString(2, to);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int distance = rs1.getInt(1);

			// basefare
			ps1 = con.prepareStatement(
					"select far_basefare from trainfaresinfo where far_distance =( select min(far_distance) from trainfaresinfo where far_distance>=?)");
			ps1.setInt(1, distance);
			rs1 = ps1.executeQuery();
			rs1.next();
			double baseFare = rs1.getDouble(1);

			// farefactor
			ps1 = con.prepareStatement(
					"select far_farefactor from traintravelclassesinfo where trn_no=? and trn_travelclass=?");
			ps1.setInt(1, Integer.parseInt(train));
			ps1.setString(2, coach);
			rs1 = ps1.executeQuery();
			rs1.next();
			double fareFactor = rs1.getDouble(1);

			// surcharges
			ps1 = con.prepareStatement("select trn_surcharge from trainsinfo where trn_no = ?");
			ps1.setInt(1, Integer.parseInt(train));
			rs1 = ps1.executeQuery();
			rs1.next();
			double surCharge = rs1.getDouble(1);

			double ticketCost = (baseFare * fareFactor) + surCharge;

			// discountCalculation
			double ticketCost1 = ticketCost - calcDiscount(Integer.parseInt(page1), pgender1, ticketCost);
			double ticketCost2 = ticketCost - calcDiscount(Integer.parseInt(page2), pgender2, ticketCost);
			double ticketCost3 = ticketCost - calcDiscount(Integer.parseInt(page3), pgender3, ticketCost);

			String res = ticketCost1 + "," + ticketCost2 + "," + ticketCost3 + "," + ticketCost;
			System.out.println("Fare calc Completed");
			PrintWriter pw = response.getWriter();
			pw.write(res);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public double calcDiscount(int age, String gender, double cost) {
		if (age <= 5)
			return cost;
		else if (age <= 12)
			return cost * 0.5;
		else if ((age >= 65 && gender.equals("male")) || (age >= 58 && gender.equals("female")))
			return cost * 0.25;
		return 0;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
