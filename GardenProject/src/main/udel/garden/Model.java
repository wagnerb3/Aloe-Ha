package garden;

import plants.Plant;
import windows.Window;

/**
 * Updates the stage: Contains logic and data.
 * 
 * @author Team 0
 */
public class Model {
	
	/**
	 * Width of stage.
	 */
	private int width;
	
	/**
	 * Height of stage.
	 */
	private int height;
	
	/**
	 * All the windows that can be displayed to the user.
	 */
	private Window[] windows;
	
	/**
	 * All the plants.
	 */
	private Plant[] plants;
	
	// TODO: How to handle current Window? With a Window object or int pointing
	// to index of current Window?
	
	public Model(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gets the current Window that should be displayed to the user.
	 * 
	 * @return current Window.
	 */
	public Window getWindow() {
		// TODO: Implement
		return null;
	}
	
	/**
	 * Change the current that is displayed to another window.
	 */
	public void setWindow() {
		// TODO: Implement
	}
	
	/**
	 * Search the array of plants and return an array of all Plants that match
	 * the query.
	 * 
	 * @param	query	Simple, non-regex query where the query can appear
	 * 					anywhere in the plant latin or common name.
	 * @return	null if no matching results, a Plant array of all matching 
	 * 			results.
	 */
	public Plant[] searchPlants(String query) {
		// TODO: Implement
		return null;
	}

}
