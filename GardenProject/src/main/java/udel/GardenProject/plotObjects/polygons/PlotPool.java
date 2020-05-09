package udel.GardenProject.plotObjects.polygons;

import java.io.Serializable;

/**
 * Chlorine-and-water filled of water.
 * 
 * @author Team 0
 */
public class PlotPool extends GenericPolygon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Path of an image of a pool.
	 */
	private static String pool = "/viewImages/pool.png";
	
	/**
	 * Constructor.
	 * 
	 * @param x	Horizontal position determined by MouseRelease event handler.
	 * @param y	Vertical position determined by MouseRelease event handler.
	 * @param i Plot object's image.
	 */
	public PlotPool(double x, double y) {
		// TODO: A forest is always 100.0 feet tall?
		super(x, y, 100.0, new AdjustablePolygon(null, null, 0, 0), pool);
		// TODO Auto-generated constructor stub
	}

}