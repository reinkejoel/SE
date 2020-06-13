package Park;

import java.io.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Parkhaus")
public class ParkServlet extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet( HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 String[] requestParamString = request.getQueryString().split("=");
		 String command = requestParamString[0];
		 String param = requestParamString[1];
		 
		 if ( "cmd".equals( command ) && "Summe".equals( param ) ){ 
			 Car[] Auto = getPersistentAuto();
			 List<Car> AutoList = Arrays.asList(Auto);
			 Float Summe = AutoList.stream().map(Car -> Car.getPreis()).reduce((float)0.0, Float::sum);
			 
			 response.setContentType("text/html"); 
			 PrintWriter out = response.getWriter(); 
			 out.println( Summe );
			 
			 System.out.println( "Summe = " + Summe );
			 
		 } else if ("cmd".equals( command ) && "Average".equals( param )) {
			 Car[] Auto = getPersistentAuto();
			 List<Car> AutoList = Arrays.asList(Auto);
			 Float Summe = AutoList.stream().map(Car -> Car.getPreis()).reduce((float)0.0, Float::sum);
			 Float Average = Summe/getPersistentAnzahl();
			 
			 response.setContentType("text/html"); 
			 PrintWriter out = response.getWriter(); 
			 out.println( Average );
			 
			 System.out.println( "Average = " + Average );
		 } else if ("cmd".equals( command ) && "Ausgeparkt".equals( param )) {
			 Float Anzahl = getPersistentAnzahl();
			 
			 response.setContentType("text/html"); 
			 PrintWriter out = response.getWriter(); 
			 out.println( Anzahl );
			 
			 System.out.println( "Ausgeparkt = " + Anzahl );
		 }
		 else if ("cmd".equals( command ) && "chart".equals( param )) {
			 response.setContentType("text/plain");
			 Car[] Auto = getPersistentAuto();
			 Float[]a;
			 Float[]b;
			 if (Auto == null) {
				 a=new Float[1];
				 b=new Float[1];}
			 else {
			 int x=Auto.length;
			 if (x>0) {
			 a = new Float[x];
			 b = new Float[x];
			 for(int i=0; i < x;i++) {
				 Float Id = Auto[i].getId();
				 Float Dauer = Auto[i].getDauer();
				 a[i] = Id;
				 b[i] = Dauer;
			 }}
			 else{
				 a=new Float[1];
				 b=new Float[1];
				 }
			 }
			 String s = "{\n" + " \"data\": [\n" + " {\n" + "\"x\": [\n";
             for(int i = 0; i< a.length; i++) {
                 s+="\"Car_" + a[i] + "\",\n";
             }
             s = s.substring(0, s.length()-2) + "\n" + "],\n" + "\"y\": [\n";
             for(int i = 0; i< b.length; i++) {
                 s+=b[i] + ",\n";
             }
             s = s.substring(0, s.length()-2) + "\n" + "],\n" + " \"type\": \"bar\"\n" + " }\n" + " ]\n" + "}";
             PrintWriter out = response.getWriter();
             out.println(s);
		 }
		 else if ("cmd".equals( command ) && "langzeit".equals( param )) {
			 response.setContentType("text/plain");
			 Car[] Auto = getPersistentAuto();
			 if(Auto!=null) {
				 List<Car> AutoList = Arrays.asList(Auto);
				 List<Float> a = AutoList.stream().filter(Car -> Car.getDauer() >= 10000).map(Car -> Car.getDauer()).collect(Collectors.toList());
				 List<Float> b = AutoList.stream().filter(Car -> Car.getDauer() >= 10000).map(Car -> Car.getId()).collect(Collectors.toList());
				 if(b!=null) {
					 Float[] c = new Float[b.size()];
					 b.toArray(c);
					 Float[] d = new Float[a.size()];
					 a.toArray(d);
					 String s = "{\n" + " \"data\": [\n" + " {\n" + "\"x\": [\n";
		             for(int i = 0; i< c.length; i++) {
		                 s+="\"Car_" + c[i] + "\",\n";
		             }
		             s = s.substring(0, s.length()-2) + "\n" + "],\n" + "\"y\": [\n";
		             for(int i = 0; i< d.length; i++) {
		                 s+=d[i] + ",\n";
		             }
		             s = s.substring(0, s.length()-2) + "\n" + "],\n" + " \"type\": \"bar\"\n" + " }\n" + " ]\n" + "}";
		             PrintWriter out = response.getWriter();
		             out.println(s);
				 }
			 }
		 }
		 else {
			 System.out.println( "Invalid Command: " + request.getQueryString() ); 
		 }
	 }

	 protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		 String body = getBody( request ); System.out.println( body );
		 String[] params = body.split(",");   String event = params[0];
		 if( "leave".equals( event ) ){ 
			 //Float Summe = getPersistentSumme();
			 Float Anzahl = getPersistentAnzahl();
			 Float Dauer = getPersistentDauer();
			 Float Nr = getPersistentNr();
			 Car[] Auto = getPersistentAuto();
			 String priceString = params[4]; 
			 String DauerString = params[3]; 
			 String IDString = params[1]; 
			 if ( ! "_".equals( priceString ) ){ 
				 Anzahl = Anzahl + 1;
				 getApplication().setAttribute("Anzahl", Anzahl ); 
			 
			 if ( ! "_".equals( DauerString ) && ! "_".equals( IDString )  ){ 
				 float duration = Float.parseFloat(DauerString);
				 float ID = Float.parseFloat(IDString);
				 float preis = Float.parseFloat(priceString);
				 Nr = ID;
				 Dauer = duration;
				 getApplication().setAttribute("Nr", Nr );
				 getApplication().setAttribute("Dauer", Dauer );
				 Car c = new Car(Nr, Dauer, preis);
				 if(Auto == null)
				 {
					 Car[] Auto3= new Car[1];
					 Auto3[0]=c;
					 getApplication().setAttribute("Auto", Auto3 );
				 }
				 else
				 {
				 int x = Auto.length+1;
				 Car[] Auto3= new Car[x];
				 for (int i=0;i<Auto.length;i++)
				 {
					 Auto3[i]=Auto[i];
				 }
				 Auto3[Auto3.length-1]=c;
				 
				 getApplication().setAttribute("Auto", Auto3 );
				 }
				 
				 
			 }
			 }
			 //response.setContentType("text/html"); 
			 //PrintWriter out = response.getWriter(); 
			 //out.println( Summe ); 
			 //out.println( Average ); 
		 }
	 }
	 
	 private ServletContext getApplication(){ 
		 return getServletConfig().getServletContext(); 
	 }
	 
	 private Car[] getPersistentAuto() {
		 Car[] Auto;
		 ServletContext application = getApplication(); 
		 Auto = (Car[])application.getAttribute("Auto"); 
		 return Auto; 
	 }
	 
	 
	 private Float getPersistentDauer() {
		 Float Dauer;
		 ServletContext application = getApplication();
		 Dauer =(Float)application.getAttribute("Dauer");
		 if (Dauer == null) Dauer = 0.0f;
		 return Dauer;
	 }
	 
	 private Float getPersistentNr() {
		 Float Nr;
		 ServletContext application = getApplication();
		 Nr =(Float)application.getAttribute("Nr");
		 if (Nr == null) Nr = 0.0f;
		 return Nr;
	 }
	 
	 private Float getPersistentAnzahl(){ 
		 Float Anzahl;
		 ServletContext application = getApplication(); 
		 Anzahl = (Float)application.getAttribute("Anzahl"); 
		 if ( Anzahl == null ) Anzahl = 0.0f;
		 return Anzahl; 
	 }
	 
	 private static String getBody(HttpServletRequest request) throws IOException {

		 StringBuilder stringBuilder = new StringBuilder();
		 BufferedReader bufferedReader = null;
		 try {
		   InputStream inputStream = request.getInputStream();
		   if (inputStream != null) {
		     bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		     char[] charBuffer = new char[128];
		     int bytesRead = -1;
		     while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
		       stringBuilder.append(charBuffer, 0, bytesRead);
		     }
		   } else {
		     stringBuilder.append("");
		   }
		 } finally {
		   if (bufferedReader != null) {
		     bufferedReader.close();
		   }
		}
		 return stringBuilder.toString();
		 } 
}
