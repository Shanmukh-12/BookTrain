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
 * Servlet implementation class CoachData
 */
@WebServlet("/CoachData")
public class CoachData extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter pw = response.getWriter();
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:postgresql://192.168.110.48:5432/plf_training?user=plf_training_admin&password=pff123");
			int tid = Integer.parseInt(request.getParameter("trIndex"));
			PreparedStatement ps = con
					.prepareStatement("select trn_travelclass from traintravelclassesinfo where trn_no=?");
			ps.setInt(1, tid);
			ResultSet rs = ps.executeQuery();
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append("<option value=" + rs.getString(1) + ">" + rs.getString(1) + "</option>");
			}
			con.close();
			pw.write(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
