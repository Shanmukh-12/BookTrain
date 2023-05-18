<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Preview</title>
 
<style>
table, th, td {
  border:1px solid black;
}
</style>
</head>

<body>

<body align="center">

<h1>Preview</h1>
<Table align="center">
 <tr>
      <th>Train No</th>
      <td id="train"><% String s=request.getParameter("train"); out.println(s); %></td>
 </tr>
<tr>
	<th>Train Name</th>
	<td id="trains"></td>
</tr>
<tr>
	<th>From</th>
	<td id="from"><% String sss=request.getParameter("from"); out.println(sss); %></td>
</tr>
<tr>
	<th>To</th>
	<td id="to"><% String ssss=request.getParameter("to"); out.println(ssss); %></td>	
</tr>
<tr>
	<th>Train Date</th>
	<td ><% String q=request.getParameter("date"); out.println(q); %></td>	
</tr>
<tr>
	<th>Total Amount</th>
	<td id="tcost"></td>
</tr>
<tr>
	<th>Total Coach</th>
	<td id="coach"></td> 
</tr>

</Table>

<h2>Passenger Details:</h2>

<Table align="center">
 <tr>
      <th>Passenger 1</th>
      <td><% String name1=request.getParameter("n1"); out.println(name1); %></td>
      <td><% String gen1=request.getParameter("g1"); out.println(gen1); %></td>
      <td><% String age1=request.getParameter("a1"); out.println(age1); %></td>
      <td id="pcost1"><% String p1cost=request.getParameter("pcost1"); out.println(p1cost); %></td>
 </tr>
 <tr>
      <th>Passenger 2</th>
      <td><% String name2=request.getParameter("n2"); out.println(name2); %></td>
      <td><% String gen2=request.getParameter("g2"); out.println(gen2); %></td>
      <td><% String age2=request.getParameter("a2"); out.println(age2); %></td>
      <td id="pcost2"></td>
 </tr>
 <tr>
      <th>Passenger 3</th>
      <td><% String name3=request.getParameter("n3"); out.println(name3); %></td>
      <td><% String gen3=request.getParameter("g3"); out.println(gen3); %></td>
      <td><% String age3=request.getParameter("a3"); out.println(age3); %></td>
      <td id="pcost3"></td>
 </tr>
</Table>

	
	<p id="pcost1"></p>
	<p id="pcost2"></p>
	<p id="pcost3"></p>
	<button onclick="history.back()">Go Back</button>
	<button id="submit">Submit</button>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

	<script>
	var from,to,train ,coach,tdate,pname1,pgender1, page1,pname2,pgender2,page2 ,pname3 ,pgender3 ,page3 ;
	var cost1, cost2, cost3;
	
	window.onload = function(){	
		console.log(sessionStorage.getItem("from"));
		from = sessionStorage.getItem("from");
		to = sessionStorage.getItem("to");
		train = sessionStorage.getItem("train");
		coach = sessionStorage.getItem("class");
		tdate = sessionStorage.getItem("tdate");
		pname1 = sessionStorage.getItem("pn1");
		pgender1 = sessionStorage.getItem("pg1");
		page1 = sessionStorage.getItem("pa1");
		pname2 = sessionStorage.getItem("pn2");
		pgender2 = sessionStorage.getItem("pg2");
		page2 = sessionStorage.getItem("pa2");
		pname3 = sessionStorage.getItem("pn3");
		pgender3 = sessionStorage.getItem("pg3");
		page3 = sessionStorage.getItem("pa3");
		
		document.getElementById("from").innerHTML=from;
		document.getElementById("to").innerHTML=to;
		document.getElementById("train").innerHTML=train;
		document.getElementById("coach").innerHTML=coach;
		
		
		$.ajax({
			url:"FareCalculation",
			method:"get",
			data:{
				from:from,
				to:to,
				train:train,
				coach:coach,
				tdate:tdate,
				pname1:pname1,
				page1:page1,
				pgender1:pgender1,
				pname2:pname2,
				page2:page2,
				pgender2:pgender2,
				pname3:pname3,
				page3:page3,
				pgender3:pgender3,
			},
			success:function(data)
			{
				console.log(data);
				var arr = data.split(",");
				cost1 = arr[0];
				cost2 = arr[1];
				cost3 = arr[2];
				tcost = arr[3];
				document.getElementById("pcost1").innerHTML=cost1;				
				document.getElementById("pcost2").innerHTML=cost2;				
				document.getElementById("pcost3").innerHTML=cost3;
				document.getElementById("tcost").innerHTML=tcost;
			},
			error:function(data)
			{
				$("#error").html(data);
			}
		});
	}
	$(document).ready(function(){
		$("#submit").click(function(){
			console.log(from);
			console.log(train);
			$.ajax({
				url:"BookTickets2",
				method:"get",
				data:{
					from:from,
					to:to,
					train:train,
					coach:coach,
					tdate:tdate,
					tcost:tcost,
					pname1:pname1,
					page1:page1,
					pgender1:pgender1,
					pcost1:cost1,
					pname2:pname2,
					page2:page2,
					pgender2:pgender2,
					pcost2:cost2,
					pname3:pname3,
					page3:page3,
					pgender3:pgender3,
					pcost3:cost3,
				},
				success:function()
				{
					console.log("Success");
				},
				error:function()
				{
					$("#error").html("Error");
				}
			});
		});
	});

	</script>


</html>