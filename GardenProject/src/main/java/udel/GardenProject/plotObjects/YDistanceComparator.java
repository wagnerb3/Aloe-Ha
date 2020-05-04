package udel.GardenProject.plotObjects;

import java.util.Comparator;

public class YDistanceComparator implements Comparator<PlotObject>{
	
	private boolean ascending;

	/**
	 * By default, assume sorting a collection of PlotObjects from smallest to largest Y-coordinate
	 */
	public YDistanceComparator() {
		this.ascending = true;
	}
	
	/**
	 * Sort a collection of PlotObjects by Y-coordinate and specify if it is ascending(true) or descending(false)
	 * @param ascend true for smallest to largest Y-coordinate; false for largest to smallest Y-coordinate
	 */
	public YDistanceComparator(boolean ascend) {
		this.ascending = ascend;
	}
	
	@Override
	public int compare(PlotObject o1, PlotObject o2) {
		if (o1.getPlotY() == o2.getPlotY()) {
			return 0;
		}
		else if (this.ascending) {
			if (o1.getPlotY() < o2.getPlotY()) {
				return -1;
			}
			else {
				return 1;
			}
		}
		else {
			if (o1.getPlotY() < o2.getPlotY()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}

}