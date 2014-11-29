package vista;
import modelo.Conexion;

import java.awt.Color;
import java.awt.SystemColor;
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
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

import java.sql.*;

/**
 * Servlet implementation class GraficoServlet
 */
@WebServlet("/PedidosporEmplead")

public class PedidosporEmplead extends HttpServlet
{
	private Connection con;
	JDBCXYDataset xyDataset;
	JFreeChart chart;
	int largocambio = 50;
	
    public JFreeChart crearVentasporEmpleado(String condicionreporte)
    {
    	try{
    			Conexion c=new Conexion();
    			con = c.getConexion();

    			PreparedStatement preparedStatement = null;
    			String titulo=null;
    			
    			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    			if(condicionreporte.equals("1")){
    				preparedStatement = con.prepareStatement("select count(p.id_pedido) as pedidos, e.user from pedido as p, login as e where e.id=p.nombre_empleado  group by e.user order by pedidos desc");
    				titulo="Pedidos por todos los empleados";
    			}
    			if(condicionreporte.equals("2")){
    				preparedStatement = con.prepareStatement("select count(p.id_pedido) as pedidos, e.user from pedido as p, login as e where e.id=p.nombre_empleado  group by e.user order by pedidos desc limit 5");
    				titulo="Pedidos de los 5 empleados con más ventas";
    			}    			
    			
    			if(condicionreporte.equals("3")){
    				preparedStatement = con.prepareStatement("select count(p.id_pedido) as pedidos, e.user from pedido as p, login as e where e.id=p.nombre_empleado  group by e.user order by pedidos asc limit 5");
    				titulo="Pedidos de los 5 empleados con menos ventas";
    			}  
    			
    				ResultSet rs = preparedStatement.executeQuery();
    			while (rs.next()) {
    		        dataset.addValue(rs.getInt("pedidos"),"Pedidos", rs.getString("e.user"));
    		        largocambio+=50;
    				}
    			JFreeChart chart = ChartFactory.createBarChart("Pedidos por empleado",
    					null, null, dataset, PlotOrientation.HORIZONTAL, true, true, false);    
    			
    			CategoryPlot cplot = (CategoryPlot)chart.getPlot();
    			cplot.setBackgroundPaint(SystemColor.inactiveCaption);
    			((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
    			BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
    			Color color = new Color(56, 136, 216);
    			r.setSeriesPaint(0, color);
    	
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
    	largocambio=50;
    	String reporte = request.getParameter("reporte");
		String condicionreporte = request.getParameter("condicionreporte");

        response.setContentType("image/jpeg");
      
        OutputStream salida = response.getOutputStream();
        JFreeChart grafica = crearVentasporEmpleado(condicionreporte);
        
        int ancho = getParamEntero(request,"ancho",400);
        int alto = getParamEntero(request,"alto",largocambio);
        

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