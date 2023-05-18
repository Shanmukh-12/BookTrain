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
 * Servlet implementation class TrainData
 */
@WebServlet("/TrainData")
public class TrainData extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter pw = response.getWriter();
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:postgresql://192.168.110.48:5432/plf_training?user=plf_training_admin&password=pff123");
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			PreparedStatement ps = con.prepareStatement(
					"select t1.trn_no,trn_name from trainstationsinfo t1,trainstationsinfo t2,trainsinfo t where t1.trn_no=t2.trn_no and t1.stn_code=? and t2.stn_code=? and t1.trn_no=t.trn_no");
			ps.setString(1, from);
			ps.setString(2, to);
			ResultSet rs = ps.executeQuery();
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append("<option value=" + rs.getString(1) + ">" + rs.getString(2) + "</option>");
			}
			con.close();
			pw.write(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
