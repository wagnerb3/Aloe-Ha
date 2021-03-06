package udel.GardenProject.windows;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udel.GardenProject.enums.Colors;
import udel.GardenProject.enums.Moisture;
import udel.GardenProject.enums.PlotObjects;
import udel.GardenProject.enums.Seasons;
import udel.GardenProject.enums.SoilTypes;
import udel.GardenProject.enums.Sunlight;
import udel.GardenProject.enums.Windows;
import udel.GardenProject.garden.Model;
import udel.GardenProject.garden.View;
import udel.GardenProject.plotObjects.PlotObject;

/**
 * Basic questions about a user's plots that informs what plants are selected.
 *
 * @version 1.0
 * @author Team 0
 */
public class Questionnaire extends Window {

	private Group root;
	private Scene scene;

	/**
	 * Background layout
	 */
	private BorderPane borderPane;

	/**
	 * VBox creating for text for questions
	 */
	private VBox vbox;

	/**
	 * Vbox for holding the title
	 */
	private VBox topBox;

	/**
	 * TilePane created for buttons at the bottom
	 */
	private TilePane tilePane;

	/**
	 * For saving all of the user's questionnaire answers And moving between screens
	 */
	private Button backToExistingPlants, mainMenu, toPlotDesign;

	/**
	 * Used for the text in the VBox Info text
	 */
	private Text text;

	/**
	 * Allows your to scroll down the screen
	 */
	private ScrollPane scroll;
	
	/**
	 * Standard String to check if user wants any moisture values.
	 */
	private String moistureAny = "My plot has different moisture";
	
	/**
	 * Alert to clarify users mistake
	 */
	private Alert warning = new Alert(AlertType.WARNING);
	
	/**
	 * Used for single answers
	 */
	public ChoiceBox<String> q4ChoiceBox, q5ChoiceBox, q6ChoiceBox, choiceBox;

	/**
	 * Used to combine check boxes
	 */
	public ListView<CheckBox> q2ListView, q3ListView, q7ListView, q8ListView;

	/**
	 * Used to iterate through and see which selections are checked by the user
	 */
	public ArrayList<CheckBox> nearPlot = new ArrayList<CheckBox>();
	public ArrayList<CheckBox> inPlot = new ArrayList<CheckBox>();
	public ArrayList<CheckBox> seasonWant = new ArrayList<CheckBox>();
	public ArrayList<CheckBox> colorWant = new ArrayList<CheckBox>();

	/**
	 * PlotObject arrays for what the user has in or near their garden
	 */
	public ArrayList<PlotObject> plotNearArr, plotInArr;

	/**
	 * The seasons the user wants to see their plants bloom
	 */
	public ArrayList<Seasons> seasonArr;

	/**
	 * Colors the user wants to see in their garden
	 */
	public ArrayList<Color> colorArr;

	/**
	 * A list of all the checkboxes for plot objects in Q3
	 */
	public ObservableList<CheckBox> q2items;

	/**
	 * A list of all the checkboxes for seasons in Q7
	 */
	public ObservableList<CheckBox> q7items;

	/**
	 * A list of all the checkboxes for colors in Q8
	 */
	public ObservableList<CheckBox> q8items;

	/**
	 * Used for user input
	 */
	private Label gardenLabel, gardenWidth, gardenLength;
	private TextField textField, q1textField1, q1textField2;
	private HBox hbname, hbWidth, hbLength;

	/**
	 * Adjustments to size for margins, text, buttons, and scrollPane for the main
	 * Questionnaire screen
	 */
	private int inset5 = 5;
	private int buttonGap = 100;
	private int buttonPrefWidth = 100;
	private int borderSideMargins = 230;
	private int questionWrapWidth = 800;
	private int scrollWidthAdjustment = 175;
	private int scrollHeightAdjustment = 115;
	private int borderTopAndBottonMargin = 40;
	private int backgroundWidthAndHeight = 100;
	private int textWrapWidth = View.getCanvasWidth() / 2;
	private static int inset20 = 20;

	public Questionnaire(Model m) {
		super(m, "Questions About Your Garden...", Windows.Questionnaire);

		borderPane = new BorderPane();
		topBox = new VBox();
		vbox = new VBox();
		tilePane = new TilePane();

		text = new Text(
				"Welcome to the Aloe-ha questionnaire! Please fill out the questions " + 
				"below for better suggestions on which plants would be best suited for your " +
				"garden and for your preferences. You can always come back to this page if " +
				"you need to make any changes. Click 'Next' to see the your plant suggestions!");
		text.setFont(getModel().getHackBold20());
		topBox.getChildren().add(text);
		topBox.setStyle(View.getPinkBackgroundStyle());
		topBox.setPadding(new Insets(inset20));

		vbox.setStyle("-fx-background-color: #F6DCDA;");
		vbox.getChildren().add(topBox);
		vbox.setPadding(new Insets(inset20));
		//vbox.setMinWidth(100);

		populateQuestionnaire();
		createButtons();

		tilePane.setAlignment(Pos.CENTER);
		tilePane.setPadding(new Insets(0, inset5, inset20, inset5));
		tilePane.setHgap(buttonGap);
		tilePane.getChildren().addAll(backToExistingPlants, mainMenu, toPlotDesign);

		scroll = new ScrollPane();

		scroll.setStyle(View.getWhiteBackgroundStyle() + "-fx-border-color: #F6AAA4;" + "-fx-border-insets: 5;"
				+ "-fx-border-width: 3;" + "-fx-border-style: solid;");
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setMaxWidth(View.getCanvasHeight() + scrollWidthAdjustment);
		scroll.setPrefSize(View.getCanvasHeight() + scrollWidthAdjustment,
				View.getCanvasHeight() - tilePane.getHeight() - scrollHeightAdjustment);

		text.setWrappingWidth(textWrapWidth);

		vbox.setMaxWidth(scroll.getWidth());
		scroll.setContent(vbox);

		Image image = new Image(getClass().getResourceAsStream("/buttonImages/splash2.png"));
		View.setBackgroundScreen(image, backgroundWidthAndHeight, backgroundWidthAndHeight);

		borderPane.setBackground(View.getBackgroundScreen());
		BorderPane.setMargin(scroll,
				new Insets(borderTopAndBottonMargin, borderSideMargins, borderTopAndBottonMargin, borderSideMargins));
		borderPane.setCenter(scroll);
		borderPane.setBottom(tilePane);

		this.root = new Group();
		root.getChildren().add(borderPane);
		this.scene = new Scene(this.root, View.getCanvasWidth(), View.getCanvasHeight());
	}

	/**
	 * Pulls together the questions and how it is handled into the vbox. Ability to
	 * change order of questions here.
	 */
	public void populateQuestionnaire() {
		createQ1();
		createQ2();
		createQ3();
		createQ4();
		createQ5();
		createQ6();
		createQ7();
		createQ8();
	}

	/**
	 * Creates the Text of each question
	 * 
	 * @param question
	 * @return The characteristics of text
	 */
	public Text createText(String question) {
		Text t = new Text(question);
		t.setWrappingWidth(questionWrapWidth);
		t.setStyle("-fx-font-size: 20px;");
		vbox.getChildren().add(t);
		return t;
	}

	/**
	 * Question that allows user to type in the name of their garden
	 */
	public void createQ1() {
		createText("1) What would you like to name your Garden?");
		gardenLabel = new Label("Garden Name:");
		textField = new TextField();
		textField.setPromptText("My Garden");
		hbname = new HBox();
		hbname.getChildren().addAll(gardenLabel, textField);
		hbname.setSpacing(inset20);
		vbox.getChildren().addAll(hbname);
	}

	/**
	 * Question that allows the user to type the width and length of their plot
	 */
	public void createQ2() {
		createText("2) How big is the plot you wish to plant your garden (in ft).");

		gardenWidth = new Label("Width:");
		q1textField1 = new TextField();
		q1textField1.setPromptText("25");
		hbWidth = new HBox();
		hbWidth.getChildren().addAll(gardenWidth, q1textField1);

		gardenLength = new Label("Length:");
		q1textField2 = new TextField();
		q1textField2.setPromptText("25");
		hbLength = new HBox();
		hbLength.getChildren().addAll(gardenLength, q1textField2);

		ArrayList<HBox> setHBoxAttributes = new ArrayList<HBox>();
		setHBoxAttributes.add(hbWidth);
		setHBoxAttributes.add(hbLength);

		for (HBox hb : setHBoxAttributes) {
			hb.setSpacing(inset20);
		}
		vbox.getChildren().addAll(hbWidth, hbLength);
	}

	/**
	 * Question asking user if their garden is near a road or forest
	 */
	public void createQ3() {
		int numberOfItems = 0;
		createText("3) Are any of the following items near/in your plot? (Please select all that apply)");

		// list of items that appear NEAR a plot
		List<PlotObjects> objectsNearPlot = new ArrayList<PlotObjects>();
		for (PlotObjects enumPlotObjects : PlotObjects.values())
			objectsNearPlot.add(enumPlotObjects);

		q2items = FXCollections.observableArrayList(); // add checkboxes to this list
		for (PlotObjects plotObjectEnum : objectsNearPlot) {
			CheckBox c = new CheckBox(plotObjectEnum.toString());
			q2items.add(c); // added to this list to view
			nearPlot.add(c); // added to this arrayList for future checking purposes when user clicks next
			numberOfItems++;
		}
		q2ListView = new ListView<>();
		q2ListView.setItems(q2items); // add the items in the observable array to the listView
		q2ListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		q2ListView.prefHeightProperty().bind(Bindings.size(q2items).multiply(numberOfItems * 2 - 6));
		vbox.getChildren().addAll(q2ListView);

	}

	/**
	 * Question asking about the moisture of the user's plot
	 */
	public void createQ4() {
		createText(
				"4) Does your entire plot have the same level of moisture? If yes, what level of moisture does your garden have?");
		q4ChoiceBox = new ChoiceBox<>();
		for (Moisture m : Moisture.values())
			q4ChoiceBox.getItems().add(m.getFriendlyName());
		q4ChoiceBox.getItems().add(moistureAny);
		q4ChoiceBox.setValue(Moisture.DRY.getFriendlyName());
		vbox.getChildren().addAll(q4ChoiceBox);
	}

	/**
	 * Question asking about the soil type of the user's plot
	 */
	public void createQ5() {
		createText("5) Does your entire plot have the same soil type? If yes, what soil type does your garden have?");
		q5ChoiceBox = new ChoiceBox<>();
		for (SoilTypes st : SoilTypes.values())
			q5ChoiceBox.getItems().add(st.getName());
		q5ChoiceBox.getItems();
		q5ChoiceBox.setValue(SoilTypes.CLAY.getName());
		vbox.getChildren().addAll(q5ChoiceBox);
	}

	/**
	 * Question asking about the sunlight of the user's plot
	 */
	public void createQ6() {
		createText(
				"6) Does your entire plot receive the same amount of sunlight? If yes, to what degree of lighing does your garden get?");
		q6ChoiceBox = new ChoiceBox<>();
		for (Sunlight s : Sunlight.values()) {
			q6ChoiceBox.getItems().add(s.getSunlight());
		}
		q6ChoiceBox.setValue(Sunlight.FULLSUN.getSunlight());
		vbox.getChildren().addAll(q6ChoiceBox);
	}

	/**
	 * Question asking about when the user wants their flowers to bloom
	 */
	public void createQ7() {
		int numberOfItems = 0;
		createText("7) When would you like to see your garden bloom? (Please select all that apply?");

		List<Seasons> seasonsWanted = new ArrayList<Seasons>();
		for (Seasons enumSeason : Seasons.values())
			seasonsWanted.add(enumSeason);

		q7items = FXCollections.observableArrayList(); // add checkboxes to this list
		for (Seasons seasonEnum : seasonsWanted) {
			CheckBox c = new CheckBox(seasonEnum.getSeason());
			q7items.add(c); // added to this list to view
			seasonWant.add(c); // added to this arrayList for future checking purposes when user clicks next
			numberOfItems++;
		}
		q7ListView = new ListView<>();
		q7ListView.setItems(q7items); // add the items in the observable array to the listView
		q7ListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		q7ListView.prefHeightProperty().bind(Bindings.size(q7items).multiply(numberOfItems * 5));
		vbox.getChildren().addAll(q7ListView);

	}

	/**
	 * Creates the question for what colors does the user want to see in their
	 * garden
	 */
	public void createQ8() {
		int numberOfItems = 0;
		createText("8) What color blooms would you like to see in your garden? (Please select all that apply)");

		List<Colors> colorsWanted = new ArrayList<Colors>();
		for (Colors enumColor : this.getModel().getIncludedColors())
			colorsWanted.add(enumColor);

		q8items = FXCollections.observableArrayList(); // add checkboxes to this list
		for (Colors colorEnum : colorsWanted) {
			CheckBox c = new CheckBox(colorEnum.getFriendlyName());
			q8items.add(c); // added to this list to view
			colorWant.add(c); // added to this arrayList for future checking purposes when user clicks next
			numberOfItems++;
		}
		q8ListView = new ListView<>();
		q8ListView.setItems(q8items); // add the items in the observable array to the listView
		q8ListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		q8ListView.prefHeightProperty().bind(Bindings.size(q7items).multiply(numberOfItems * 5 - 5));
		vbox.getChildren().addAll(q8ListView);

	}

	/**
	 * Checks if the input for Width and Length of the plot has invalid characters
	 * 
	 * @param s String from the text of the input for Width and Length
	 * @return True if the input is all numbers, False if there is a letter
	 */
	public boolean checkNumberValidation(String s) {
		boolean hasNumbersOnly = true;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				hasNumbersOnly = false;
				break;
			}
		}
		return hasNumbersOnly;
	}

	/**
	 * Creates the handling for the buttons at the bottom of the screen
	 */
	public void createButtons() {
		backToExistingPlants = new Button("Go Back");
		backToExistingPlants.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switchToWindow(Windows.ExistingPlants);
			}
		});

		mainMenu = new Button("Main Menu");
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switchToWindow(Windows.Welcome);
			}
		});

		toPlotDesign = new Button("Next");
		toPlotDesign.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// sends information from questionnaire to Session for later use. User MUST
				// answer all questions.

				// handles if user doesn't type anything in for the plot name
				boolean noError = true;
				
				if (textField.getText().isEmpty()) {
					getSession().setPlotName("My Garden");
				} else {
					getSession().setPlotName(textField.getText());
				}

				/**
				 * Handles if user doesn't type anything into the width and length text boxes
				 * and gives and error message if letters are typed
				 */
				String widthUserInput = q1textField1.getText();
				String lengthUserInput = q1textField2.getText();

				if (widthUserInput.isEmpty()) {
					getSession().setWidthOfUserPlot(25);
				} else if (checkNumberValidation(widthUserInput)) {
					getSession().setWidthOfUserPlot(Integer.parseInt(q1textField1.getText()));
				} else {
					noError = false;
					warning.setContentText("Please enter a valid width for Question 2");
					warning.show();
				}
				if (lengthUserInput.isEmpty()) {
					getSession().setLengthOfUserPlot(25);
				} else if (checkNumberValidation(lengthUserInput)) {
					getSession().setLengthOfUserPlot(Integer.parseInt(q1textField2.getText()));
				} else {
					noError = false;
					warning.setContentText("Please enter a valid length for Question 2");
					warning.show();
				}

				checkSelectedPlot(nearPlot);

				try {
					getSession().setMoistureOfPlot(Moisture.valueOf(getChoice(q4ChoiceBox).replace(" ", "").toUpperCase()));
				} catch (IllegalArgumentException e) {
					getSession().setMoistureOfPlot(null);
				}

				try {
					getSession().setSoilTypeOfPlot(SoilTypes.valueOf(getChoice(q5ChoiceBox).replace(" ", "").toUpperCase()));
				} catch (IllegalArgumentException e) {
					getSession().setSoilTypeOfPlot(SoilTypes.ANY);
				}
				try {
					getSession().setSunlightOfPlot(Sunlight.valueOf(getChoice(q6ChoiceBox).replace(" ", "").toUpperCase()));
				} catch (IllegalArgumentException e) {
					getSession().setSunlightOfPlot(Sunlight.ANY);
				}
				checkSelectedSeasons(seasonWant);
				checkSelectedColor(colorWant);
				
				if(noError) {
					switchToWindow(Windows.PlantSelection);
				}
				
			}
		});

		/*
		 * Sets up the Style and Effects for the buttons
		 */
		List<Button> bottomButtons = new ArrayList<Button>();
		bottomButtons.add(backToExistingPlants);
		bottomButtons.add(mainMenu);
		bottomButtons.add(toPlotDesign);

		for (Button b : bottomButtons) {
			b.setFont(Font.loadFont(getClass().getResourceAsStream(View.getHackBold()), View.getButtonTextSize()));
			b.setStyle(View.getLightGreenBackgroundStyle() + View.getBlackTextFill());
			b.setPrefWidth(buttonPrefWidth);

			DropShadow shadow = new DropShadow();
			b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					b.setEffect(shadow);
					b.setStyle(View.getWhiteBackgroundStyle() + View.getBlackTextFill());
				}
			});

			b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					b.setEffect(null);
					b.setStyle(View.getLightGreenBackgroundStyle() + View.getBlackTextFill());
				}
			});
		}
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

	/**
	 * Extracts String value from choice box. Used on button call next
	 * 
	 * @param q4ChoiceBox2
	 * @return
	 */
	public String getChoice(ChoiceBox<String> choice) {
		String x = choice.getValue().toString();
		return x;
	}

	/**
	 * Checks which options are selected by the user and sets the ArrayList of
	 * <code>selectedPlotObjects</code> in Session.
	 * 
	 * @param cb checkboxes that are in each listview for objects IN the user's
	 *           plot.
	 */
	public void checkSelectedPlot(ArrayList<CheckBox> cb) {

		// Removes everything in list first so no doubles are added
		getSession().getSelectedPlotObjects().clear();

		// for every selected plot object in the cb list, convert it back to a
		// PlotObjects enum, and add it to the Session ArrayList.
		for (int counter = 0; counter < cb.size(); counter++) {
			if (cb.get(counter).isSelected()) {
				getSession().getSelectedPlotObjects().add(PlotObjects.valueOf(cb.get(counter).getText()));
			}
		}
	}

	/**
	 * Checks which options are selected by the user and returns an arraylist of
	 * seasons
	 * 
	 * @param cb Checkboxes selected for seasons
	 */
	public void checkSelectedSeasons(ArrayList<CheckBox> cb) {

		// Removes everything in list first so no doubles are added
		getSession().getSeasonsUserSelected().clear();

		// for every selected plot object in the cb list, convert it back to a
		// Seasons enum, and add it to the Session ArrayList.
		for (int counter = 0; counter < cb.size(); counter++) {
			if (cb.get(counter).isSelected()) {
				try {
					getSession().getSeasonsUserSelected().add(
							Seasons.valueOf(cb.get(counter).getText().replace(" ", "").toUpperCase()));
				}catch(IllegalArgumentException e) {
					System.out.println("No Season exits for this");
				}
			}
		}

	}

	/**
	 * Checks which options are selected by the user and returns an arraylist of
	 * colors
	 * 
	 * @param cb Checkboxes checked for the colors
	 */
	public void checkSelectedColor(ArrayList<CheckBox> cb) {

		// Removes everything in list first so no doubles are added
		getSession().getColorsUserSelected().clear();

		// for every selected plot object in the cb list, convert it back to a
		// Colors from Colors Enum and add it to the Session ArrayList.
		for (int counter = 0; counter < cb.size(); counter++) {
			if (cb.get(counter).isSelected()) {
				try {
					getSession().getColorsUserSelected().add(
							Colors.valueOf(cb.get(counter).getText().replace(" ", "").toUpperCase()));
				}catch (IllegalArgumentException e) {
					System.out.println("No color exits for this");
				}
				
				
			}
		}
	}

	/**
	 * Every question answer is cleared and added back again to the session it
	 * corresponds to
	 */
	public void refresh() {
		// Name of plot
		textField.clear();
		textField.setText(getSession().getPlotName());

		// Width of Plot
		q1textField1.clear();
		q1textField1.setText(Integer.toString(getSession().getWidthOfUserPlot()));

		// Length of Plot
		q1textField2.clear();
		q1textField2.setText(Integer.toString(getSession().getLengthOfUserPlot()));

		// Plot Objects
		clearCheckBoxes(q2items);
		for (PlotObjects po : getSession().getSelectedPlotObjects()) {
			for (CheckBox c : q2items) {
				if (c.getText().toString().equals(po.toString())) {
					c.setSelected(true);
				}
			}
		}

		// Moisture
		if(getSession().getMoistureOfPlot() == null)
			q4ChoiceBox.setValue(moistureAny);
		else
			q4ChoiceBox.setValue(getSession().getMoistureOfPlot().getFriendlyName());

		// Soil type
		q5ChoiceBox.setValue(getSession().getSoilTypeOfPlot().getName());

		// Sunlight
		q6ChoiceBox.setValue(getSession().getSunlightOfPlot().getSunlight());

		// Seasons Selected
		clearCheckBoxes(q7items);
		for (Seasons s : getSession().getSeasonsUserSelected()) {
			for (CheckBox c : q7items) {
				if (c.getText().toString().equals(s.getSeason())) {
					c.setSelected(true);
				}
			}
		}

		// Colors Selected
		clearCheckBoxes(q8items);
		for (Colors color : getSession().getColorsUserSelected()) {
			for (CheckBox c : q8items) {
				if (c.getText().toString().equals(color.getFriendlyName())) {
					c.setSelected(true);
				}
			}
		}

	}

	/**
	 * Clears all the checkboxes in the ListView
	 * 
	 * @param list
	 */
	public void clearCheckBoxes(ObservableList<CheckBox> list) {
		for (CheckBox c : list) {
			if (c.isSelected()) {
				c.setSelected(false);
			}
		}
	}

}
