package udel.GardenProject.garden;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.image.ImageView;
import udel.GardenProject.enums.Colors;
import udel.GardenProject.enums.GardenView;
import udel.GardenProject.enums.Moisture;
import udel.GardenProject.enums.PlotObjects;
import udel.GardenProject.enums.Seasons;
import udel.GardenProject.enums.SoilTypes;
import udel.GardenProject.enums.Sunlight;
import udel.GardenProject.enums.Year;
import udel.GardenProject.plants.Plant;
import udel.GardenProject.plotObjects.PlotObject;

/**
 * Holds all the critical user-state information so that a
 * <code>.gardenproject</code> file can be easily loaded in and out without much
 * delay.<br>
 * <br>
 * 
 * Isolated so that it can be saved and loaded easily.<br>
 * <br>
 * 
 * All attributes should be adequately javadoc'd for future reference.<br>
 * <br>
 * 
 * <b>NOTE:</b> All attributes should be initialized to a default value, either
 * in their attribute or the Session constructor.
 * 
 * @version 1.0
 * @author Team 0
 */
public class Session implements Serializable {

	/**
	 * Prevent newer version of GardenProject being loaded by older versions of 
	 * save files to prevent errors.
	 * 
	 * Should only update this if absolutely necessary.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * When any new session is created, it has a unique ID to it as so it can be
	 * distinguished from other saved sessions.
	 */
	private int id;

	/**
	 * Does this current Session need to be saved? By default, if any setter is
	 * called, this is automatically set to true.
	 * 
	 * When Controller's <code>stop()</code> method is invoked, Model will check
	 * to see if it can save this Session to lastSavedFilepath. If not, it 
	 * simply continues.
	 */
	private boolean unsaved = false;

	/**
	 * Location of where this Session was last saved to. Makes it easier to load
	 * another Session by assuming the user wants to save this Session to where it
	 * was last saved.<br>
	 * <br>
	 * 
	 * If the user saved a Session S to "C:/whatever.gardenproject", and the user
	 * wants to open a new Session G, assume that the user wants to save S where it
	 * was saved at "C:/whatever.gardenproject", and start new Session G.
	 */
	private String lastSavedFilepath = "";

	/**
	 * Name of the user's plot so that they can customize them.
	 */
	private String plotName = "My Garden Plot";

	/**
	 * Width of the user's plot in feet.
	 */
	private int widthOfUserPlot = 25;
	
	/**
	 * Length of the user's plot in feet.
	 */
	private int lengthOfUserPlot = 25;

	/**
	 * User's moisture level of their plot
	 */
	private Moisture moistureOfPlot = Moisture.DRY;

	/**
	 * User's soil type of their plot
	 */
	private SoilTypes soilTypeOfPlot = SoilTypes.CLAY;

	/**
	 * Users sunlight of their plot. Decimal percentage (from 0.0 to 1.0).
	 */
	private Sunlight sunlightOfPlot = Sunlight.ANY;

	/**
	 * Existing plants already in users plot.<br>
	 * <br>
	 * 
	 * <b>NOTE</b>: Key should plant latin name, value the plant itself.
	 */
	private HashSet<Plant> existingPlants = new HashSet<Plant>();

	/**
	 * Selected Plants user wishes to see in Plot from SelectedPlant and 
	 * AllPlants.<br><br>
	 * 
	 * <b>NOTE</b>: Key should plant latin name, value the plant itself.
	 */
	private HashSet<Plant> selectedPlants = new HashSet<Plant>();
	
	/**
	 * User's selected seasons for blooms
	 */
	private ArrayList<Seasons> seasonsUserSelected = new ArrayList<Seasons>();
	
	/**
	 * User's selected colors for flowers
	 */
	private HashSet<Colors> colorsUserSelected = new HashSet<Colors>();

	/**
	 * Objects actually in the plot, to be displayed in PlotDesign.
	 */
	private ArrayList<PlotObject> plot = new ArrayList<PlotObject>();
	
	/**
	 * Objects in the plot from blueprint, to be displayed in PlotDesign.
	 */
	private ArrayList<PlotObject> bluePrintPlot = new ArrayList<PlotObject>();
	
	
	/**
	 * Season selected by user from SeasonView
	 */
	private Seasons seasonInput = Seasons.SPRING;

	/**
	 * Year selected by user from SeasonView
	 */
	private Year yearInput = Year.YEAR0;

	/**
	 * Type of view selected by user from SeasonView
	 */
	private GardenView viewInput = GardenView.WINDOWVIEW;

	/**
	 * The user's selection to how they want to save on the download screen
	 */
	private String saveOption;

	/**
	 * Plot Objects the user wants to appear in the PlotDesign left column.
	 */
	public ArrayList<PlotObjects> selectedPlotObjects = new ArrayList<PlotObjects>();
	
	/**
	 * Constructor. Initializes default values for complex objects.
	 */
	public Session() {
		selectedPlotObjects.add(PlotObjects.Flamingo);
		seasonsUserSelected.add(Seasons.WINTER);
		colorsUserSelected.add(Colors.BLUE);
		this.id = generateID();
	}
	
	/**
	 * Generate a unique ID based on the system current time. If that fails,
	 * default to a pseudo-random number. This method generates fairly
	 * predictable numbers, but a user can only save so frequently. For 
	 * collisions to occur in a user's recent saves from multiple saved
	 * garden projects with the same id would be rare.
	 * @return Pseudo-random time-based ID number.
	 */
	private int generateID() {
		int newID = (int)(Math.random() * Integer.MAX_VALUE) - Integer.MIN_VALUE;
		
		try {
			newID = Math.toIntExact(System.currentTimeMillis() % 
					(Long.valueOf(Integer.MAX_VALUE - 1)));
		} catch(ArithmeticException e) {
			e.printStackTrace();
			System.out.println("Session: Impressive if this error is ever thrown. "
					+ "Is something wrong with the system clock?");
		}
		
		return newID;
	}
	
	/**
	 * Getter.
	 * @return Session's unique ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Getter.
	 * @return	PlotObjects the user selected to be in their plot.
	 */
	public ArrayList<PlotObjects> getSelectedPlotObjects() {
		return this.selectedPlotObjects;
	}

	/**
	 * Getter. If any setter has been called by any other class, this boolean
	 * automatically becomes true. It does not become true if any getter 
	 * (including for any of the complex objects like ArrayLists or otherwise
	 * are called.) Helpful for knowing if to indicate to the user that they
	 * should save their unsaved work.
	 * @return	True if this session has unsaved work, false if not.
	 */
	public boolean isUnsaved() {
		return unsaved;
	}

	/**
	 * If this Session was recently serialized (saved), use this method to 
	 * update its saved status.
	 * @param unsaved	True if the session has been saved, false if not.
	 */
	protected void setUnsaved(boolean unsaved) {
		this.unsaved = unsaved;
	}

	/**
	 * Getter.
	 * @return	Full file path to where this session was last saved to.
	 */
	public String getLastSavedFilepath() {
		return lastSavedFilepath;
	}

	/**
	 * Setter.
	 * @param lastSavedFilepath	Full file path to where this session was last
	 * 							saved to.
	 */
	public void setLastSavedFilepath(String lastSavedFilepath) {
		this.unsaved = true;
		this.lastSavedFilepath = lastSavedFilepath;
	}

	/**
	 * Gets the name of the plot from Q1 in Questionnaire
	 * @return
	 */
	public String getPlotName() {
		return plotName;
	}

	/**
	 * Sets the name of the plot from Questionnaire to send to session and be used in file choosing 
	 * @param plotName The name entered in the TextField in Q1 of Questionnaire 
	 */
	public void setPlotName(String plotName) {
		this.unsaved = true;
		this.plotName = plotName;
	}
	
	/**
	 * Get the plot arraylist which contains all the plotObject in the plot design.
	 * @return plot
	 */
	public ArrayList<PlotObject> getPlot() {
		this.unsaved = true;
		return plot;
	}
	
	/**
	 * Get the bluePrintPlot arraylist which contains all the plotObject in the blueprint.
	 * @return bluePrintPlot
	 */
	public ArrayList<PlotObject> getBluePrintPlot() {
		unsaved = true;
		return bluePrintPlot;
	}

	public void setPlot(ArrayList<PlotObject> plot) {
		this.unsaved = true;
		this.plot = plot;
	}

	/**
	 * Sets the width of the user's plot from Q2 of Questionnaire 
	 * @param w
	 */
	public void setWidthOfUserPlot(int w) {
		this.unsaved = true;
		this.widthOfUserPlot = w;
	}

	/**
	 * Gets the width of the user's plot 
	 * @return width of user's plot in feet
	 */
	public int getWidthOfUserPlot() {
		return widthOfUserPlot;
	}

	/**
	 * Sets the length of the user's plot to what the user types in Q2 of Questionnaire
	 * @param w
	 */
	public void setLengthOfUserPlot(int w) {
		this.unsaved = true;
		this.lengthOfUserPlot = w;
	}

	/**
	 * Gets the user's info of plot length from Q2 in Questionnaire 
	 * @return length of user's plot in feet 
	 */
	public int getLengthOfUserPlot() {
		return lengthOfUserPlot;
	}

	/**
	 * Gets moisture of the users plant from Questionnaire 
	 * @return
	 */
	public Moisture getMoistureOfPlot() {
		return moistureOfPlot;
	}

	/**
	 * Sets the moisture of the user's plot according to Q4 in Questionnaire 
	 * @param m Text chosen from the drop down in Questionnaire 
	 */
	public void setMoistureOfPlot(Moisture m) {
		this.unsaved = true;
		this.moistureOfPlot = m;
	}

	/**
	 * Gets the soil type for the user's plot 
	 * @return String of the soil type
	 */
	public SoilTypes getSoilTypeOfPlot() {
		return soilTypeOfPlot;
	}

	/**
	 * Sets the soil type of the user's plot as their answer in Q5 of Questionnaire 
	 * @param st The String input from Questionnaire 
	 */
	public void setSoilTypeOfPlot(SoilTypes st) {
		this.unsaved = true;
		this.soilTypeOfPlot = st;
	}

	/**
	 * Getst the sunlight of the user's plot 
	 * @return The string input of the users plot 
	 */
	public Sunlight getSunlightOfPlot() {
		return sunlightOfPlot;
	}

	/**
	 * Sets the sunlight of the user's plot as per Q6 of the Questionnaire 
	 * @param sun String input from the Questionnaire 
	 */
	public void setSunlightOfPlot(Sunlight sun) {
		this.sunlightOfPlot = sun;
	}

	/**
	 * Sets all the seasons the user has selected from Q7 of the Questionnaire 
	 * @param season Arraylist of selected seasons from the checkboxes in Questionnaire 
	 */
	public void setSeasonsUserSelected(ArrayList<Seasons> season) {
		this.unsaved = true;
		this.seasonsUserSelected = season;
	}

	/**
	 * Gets the seasons selected from Questionnaire 
	 * @return An ArrayList of Seasons 
	 */
	public ArrayList<Seasons> getSeasonsUserSelected() {
		return seasonsUserSelected;
	}

	/**
	 * Sets the colors the user chose in Q8 of the Questionnaire 
	 * @param color An ArrayList of Colors (colors from the Colors Enum) 
	 */
	public void setColorsUserWants(HashSet<Colors> color) {
		this.unsaved = true;
		this.colorsUserSelected = color;
	}

	/**
	 * Gets the user's selected Colors from Q8 of the questionnaire 
	 * @return An ArrayList of Colors 
	 */
	public HashSet<Colors> getColorsUserSelected() {
		return colorsUserSelected;
	}

	/**
	 * Gets the Season selected from the user from Season View 
	 * @return the Season selected 
	 */
	public Seasons getSeasonInput() {
		return seasonInput;
	}

	/**
	 * Sets the season to the user's choice from the toggle in Season View 
	 * @param chooseSeason The season chosen from the Toggle Button 
	 */
	public void setSeasonInput(Seasons chooseSeason) {
		this.unsaved = true;
		this.seasonInput = chooseSeason;
	}

	/**
	 * Getter.
	 * @return The amount of time gone from the start of the user's garden 
	 */
	public Year getYearInput() {
		return yearInput;
	}

	/**
	 * Sets the amount of time gone by from the start of the user's garden in years
	 * @param y Year from the toggles in SeasonView
	 */
	public void setYearInput(Year y) {
		this.unsaved = true;
		this.yearInput = y;
	}

	/**
	 * Gets the type of view the user wants to see 
	 * @return A string for the type of view
	 */
	public GardenView getViewInput() {
		return viewInput;
	}

	/**
	 * Sets the type of view the user wants to see 
	 * @param v View type from the toggles in SeasonView 
	 */
	public void setViewInput(GardenView v) {
		this.unsaved = true;
		this.viewInput = v;
	}

	/**
	 * Getter.
	 * @return	What type of saving method does the user want to use.
	 */
	public String getSaveOption() {
		return saveOption;
	}

	/**
	 * Setter.
	 * @param so	What type of saving method does the user want to use.
	 */
	public void setSaveOption(String so) {
		this.unsaved = true;
		this.saveOption = so;
	}

	/**
	 * A place for all the plants that were selected by the user from the 
	 * Existing Plants Screen.
	 * @return HashSet of Existing Plants.
	 */
	public HashSet<Plant> getExistingPlants() {
		return existingPlants;
	}
	
	/**
	 * Getter.
	 * @return	HashSet of Selected Plants.
	 */
	public HashSet<Plant> getSelectedPlants() {
		return selectedPlants;
	}
	
}
