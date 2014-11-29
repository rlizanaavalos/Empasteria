package vista;
import modelo.Conexion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.jfree.data.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;

import java.sql.*;

/**
 * Servlet implementation class GraficoServlet
 */
@WebServlet("/PedidosporTemporad")

public class PedidosporTemporad extends HttpServlet
{
	private Connection con;
	JDBCXYDataset xyDataset;
	JFreeChart chart;
	int largocambio = 50;
	
    public JFreeChart crearPedidosporTemporada()
    {
    	try{
    			Conexion c=new Conexion();
    			con = c.getConexion();

    			PreparedStatement preparedStatement = null;
    			String titulo=null;
    			
    			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    			preparedStatement = con.prepareStatement("SELECT COUNT(*) as pedidos, MONTH(fecha) as mes FROM pedido GROUP BY mes");
    			titulo="Pedidos por temporada";
    			ResultSet rs = preparedStatement.executeQuery();
    			
    			while (rs.next()) {
    				if(rs.getString("mes").equals("1")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Enero");
    				if(rs.getString("mes").equals("2")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Febrero");
    				if(rs.getString("mes").equals("3")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Marzo");
    				if(rs.getString("mes").equals("4")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Abril");
    				if(rs.getString("mes").equals("5")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Mayo");
    				if(rs.getString("mes").equals("6")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Junio");
    				if(rs.getString("mes").equals("7")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Julio");
    				if(rs.getString("mes").equals("8")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Agosto");
    				if(rs.getString("mes").equals("9")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Septiembre");
    				if(rs.getString("mes").equals("10")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Octubre");
    				if(rs.getString("mes").equals("11")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Noviembre");
    				if(rs.getString("mes").equals("12")) dataset.addValue(rs.getInt("pedidos"),"Pedidos", "Diciembre");
    			}
    			JFreeChart chart=ChartFactory.createLineChart("Pedidos por temporada",
    	                "Mes","Pedidos",dataset,PlotOrientation.VERTICAL,
    	                true,true,false);   			
    	
        return chart;
    	}catch (Exception e){
  	      System.out.println(e);
  	 }
    	return chart;
    }


    int getParamEntero(HttpServletRequest request,String pNombre, int pDefecto)
    {
            String param = request.getParameter(pNombre);

            if (param == null || param.compareTo("") == 0)
            {
                return pDefecto;
            }

            return Integer.parseInt(param);

    }


    /** Processes requests FOR both HTTP GET and POST methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	//String reporte = request.getParameter("reporte");
		//String condicionreporte = request.getParameter("condicionreporte");
		//System.out.println(reporte);
		//System.out.println(condicionreporte);

        response.setContentType("image/jpeg");
      
        OutputStream salida = response.getOutputStream();
        JFreeChart grafica = crearPedidosporTemporada();
        
        int ancho = getParamEntero(request,"ancho",1000);
        int alto = getParamEntero(request,"alto",400);
        

        ChartUtilities.writeChartAsJPEG(salida,grafica,ancho,alto);

        salida.close();
    }

    /** Handles the HTTP GET method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
}