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
@WebServlet("/PedidosPorComun")

public class PedidosPorComun extends HttpServlet
{
	private Connection con;
	JDBCXYDataset xyDataset;
	JFreeChart chart;
	int largocambio = 50;
	static java.awt.Color DARK_RED;
	
    public JFreeChart crearVentasporEmpleado(String condicionreporte)
    {
    	try{
    			Conexion c=new Conexion();
    			con = c.getConexion();
    			PreparedStatement preparedStatement = null;

    			
    			String titulo=null;
    			
    			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    			preparedStatement = con.prepareStatement("SELECT comuna.nombre, COUNT(pedido.nombre_cliente) AS num FROM cliente, comuna, pedido where comuna.id=cliente.comuna AND pedido.nombre_cliente=cliente.rut GROUP BY comuna.nombre ORDER BY num DESC");
    			titulo="Comunas donde se solicitan gran cantidad de pedidos";
    			ResultSet rs = preparedStatement.executeQuery();
    			
    			while (rs.next()) {
    		        dataset.addValue(rs.getInt("num"),"Pedidos", rs.getString("comuna.nombre"));
    		       largocambio+=50;
    		       
    				}
    			
    			JFreeChart chart = ChartFactory.createBarChart("Comunas donde hay mayor cantidad de pedidos",
    					null, null, dataset, PlotOrientation.HORIZONTAL, true, true, false); 

    			CategoryPlot cplot = (CategoryPlot)chart.getPlot();
    			cplot.setBackgroundPaint(SystemColor.inactiveCaption);
    			((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
    			BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
    			Color color = new Color(236, 104, 3);
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
		System.out.println(reporte);
		System.out.println(condicionreporte);

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