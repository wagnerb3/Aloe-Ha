package udel.GardenProject.plotObjects.polygons;

import java.io.Serializable;

/**
 * Playground.
 * 
 * @author Team 0
 */
public class PlotPlayground extends GenericPolygon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Path to an image of a playground.
	 */
	private static String playground = "/viewImages/playground.png";
	
	public PlotPlayground(double x, double y) {
		// TODO: A forest is always 100.0 feet tall?
		super(x, y, 100.0, new AdjustablePolygon(null, null, 0, 0), playground);
		// TODO Auto-generated constructor stub
	}
	
}