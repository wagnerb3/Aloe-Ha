package plants;

import java.util.Comparator;

/**
 * Sort a list of plants by bloom time, assuming descending default.
 * 
 * @author Team 0
 */
public class BloomTimeComparator implements Comparator<Plant> {

	private boolean descending;
	
	/**
	 * By default, assume sorting a list of plants by bloom time, descending.
	 */
	public BloomTimeComparator() {
		this.descending = true;
	}
	
	/**
	 * Sort a list of plants by bloom time and specify whether it is ascending
	 * or descending.
	 * 
	 * @param descending	true for descending, false for ascending.
	 */
	public BloomTimeComparator(boolean descending) {
		this.descending = descending;
	}
	
	public int compare(Plant a, Plant b) {
		// TODO: Implement
		return 0;
	}
	
}
