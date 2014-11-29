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
@WebServlet("/MaterialesMasPedido")

public class MaterialesMasPedido extends HttpServlet
{
	private Connection con;
	JDBCXYDataset xyDataset;
	JFreeChart chart;
	int largocambio = 50;
	
    public JFreeChart crearMaterialesMasPedidos()
    {
    	try{
    			Conexion c=new Conexion();
    			con = c.getConexion();

    			PreparedStatement preparedStatement = null;
    			String titulo=null;
    			
    			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    			preparedStatement = con.prepareStatement("SELECT nombre, COUNT(*) AS num FROM inventario, pedido WHERE(inventario.codigo_material = pedido.tipo_material) GROUP BY nombre ORDER BY num DESC");
    			//titulo="Tipo de material más pedido y menos pedido en los empastes";
    			ResultSet rs = preparedStatement.executeQuery();
    			
    			while (rs.next()) {
    				dataset.addValue(rs.getInt("num"),"Pedidos", rs.getString("nombre"));
    				largocambio+=50;
    				
    			}
    			
    			preparedStatement = con.prepareStatement("SELECT nombre, COUNT(*) as num, codigo_material FROM inventario, pedido WHERE(inventario.codigo_material = pedido.color) GROUP BY nombre ORDER BY num DESC");
    			//titulo="Tipo de material más pedido y menos pedido en los empastes";
    			ResultSet rs1 = preparedStatement.executeQuery();
    			
    			while (rs1.next()) {
    				if(rs1.getString("codigo_material").equalsIgnoreCase("1") ){
    				dataset.addValue(rs1.getInt("num"),"Pedidos", "Tinta negra");
    				largocambio+=50;
    			}
    	
    				if(rs1.getString("codigo_material").equalsIgnoreCase("2") ){
        				dataset.addValue(rs1.getInt("num"),"Pedidos", "Tinta verde");
        				largocambio+=50;
        			}
    				if(rs1.getString("codigo_material").equalsIgnoreCase("3") ){
        				dataset.addValue(rs1.getInt("num"),"Pedidos", "Tinta azul");
        				largocambio+=50;
        			}
    			}
    			
    			JFreeChart chart=ChartFactory.createBarChart("Materiales más pedidos por los clientes",
    	                "Nombre Material","Numero de pedidos",dataset,PlotOrientation.HORIZONTAL,
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
    	largocambio=50;
        response.setContentType("image/jpeg");
      
        OutputStream salida = response.getOutputStream();
        JFreeChart grafica = crearMaterialesMasPedidos();
        
        int ancho = getParamEntero(request,"ancho",500);
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