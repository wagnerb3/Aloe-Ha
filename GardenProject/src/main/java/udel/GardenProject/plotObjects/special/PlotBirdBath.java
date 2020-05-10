package udel.GardenProject.plotObjects.special;

import java.io.Serializable;

import udel.GardenProject.plotObjects.PlotObject;

/**
 * Bath for birds.
 * 
 * @author Team 0
 */
public class PlotBirdBath extends PlotObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Path to image of a bird bath.
	 */
	private static String birdBath = "/viewImages/birdBath.png";
	
	/**
	 * TODO: ...
	 * 
	 * @param x			...
	 * @param y			...
	 */
	public PlotBirdBath(double x, double y) {
		super(x, y, 2.0, birdBath); // a bird bath is about 4 feet tall right? 
	}

}
