package udel.GardenProject.windows;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udel.GardenProject.enums.Windows;
import udel.GardenProject.garden.Model;
import udel.GardenProject.garden.View;

/**
 * Presently: Basic text description run down of all the features of the program
 * and how to use them.
 *
 * @author Team 0
 */
public class Tutorial extends Window {

	private Group root;
	private Scene scene;

	/**
	 * Allows scrolling for user when they want to access info
	 */
	private ScrollPane scroll;

	/**
	 * Center Box that holds the accordion and the top box for the instruction
	 */
	private VBox centerBox;
	/**
	 * Used for the overall layout
	 */
	private BorderPane borderPane;

	/**
	 * Button goes back to main
	 */
	private Button back;

	/**
	 * text in vbox at the top of the screen
	 */
	private Text welcomeTxt;

	/**
	 * Holds the back button at the bottom
	 */
	private TilePane backPane;

	/**
	 * Holds all the How Tos and Abouts
	 */
	private Accordion accordion;

	/**
	 * Holds the welcomeText at the top of the screen
	 */
	private VBox topBox;

	private int tutorialCanvasWidth = View.getCanvasWidth() - 320;
	private int scrollWidthAdjustment = 150;
	private int scrollHeightAdjustment = 115;
	private int backPaneTranslateX = (tutorialCanvasWidth / 2) - (View.getButtonPrefWidth() / 2);
	private int backPaneTranslateY = -30;
	private int infoTextSize = 15;
	private int messageWrapWidth = View.getCanvasWidth() / 5 * 3;
	private int borderRight = 400;
	private int borderBottom = 50;
	private int borderLeft = 50;

	/**
	 * Create a Tutorial window instance.
	 *
	 * @param m Model
	 */
	public Tutorial(Model m) {
		super(m, "Tutorial Window");

		borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));
		backPane = new TilePane();
		centerBox = new VBox();
		topBox = new VBox();

		welcomeTxt = new Text(
				"Welcome to the Tutorial! Click on the drop down options below to help you get started on your plot. Happy planting!");
		welcomeTxt.setFont(
				Font.loadFont(getClass().getResourceAsStream(View.getHackBold()), View.getTextSizeForButtonsAndText()));
		welcomeTxt.setWrappingWidth(messageWrapWidth);

		topBox.setStyle(View.getPinkBackgroundStyle());
		topBox.setPadding(new Insets(10));
		topBox.getChildren().add(welcomeTxt);

		scroll = new ScrollPane();

		scroll.setStyle(View.getWhiteBackgroundStyle() + "-fx-border-color: #F6AAA4;" + "-fx-border-insets: 5;"
				+ "-fx-border-width: 3;" + "-fx-border-style: solid;");
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setMaxWidth(View.getCanvasHeight() + scrollWidthAdjustment);
		scroll.setPrefSize(View.getCanvasHeight() + scrollWidthAdjustment,
				View.getCanvasHeight() - backPane.getHeight() - scrollHeightAdjustment);

		createAccordion();

		centerBox.setStyle("-fx-background-color: #F6DCDA;");
		centerBox.setPadding(new Insets(20, 10, 10, 10));
		centerBox.setMaxWidth(scroll.getWidth());
		centerBox.getChildren().addAll(topBox, accordion);

		scroll.setContent(centerBox);

		DropShadow shadow = new DropShadow();
		back = new Button("Go Back");
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switchToWindow(Windows.Welcome);
			}
		});

		back.setFont(Font.loadFont(getClass().getResourceAsStream(View.getHackBold()), View.getButtonTextSize() + 3));
		back.setStyle(View.getLightGreenBackgroundStyle() + View.getBlackTextFill());
		back.setPrefWidth(View.getButtonPrefWidth());
		back.setPrefHeight(View.getButtonPrefWidth() / 2);

		back.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				back.setEffect(shadow);
				back.setStyle(View.getWhiteBackgroundStyle() + View.getBlackTextFill());
			}
		});

		back.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				back.setEffect(null);
				back.setStyle(View.getLightGreenBackgroundStyle() + View.getBlackTextFill());
			}
		});

		backPane.setTranslateX(backPaneTranslateX);
		backPane.setTranslateY(backPaneTranslateY);
		backPane.getChildren().add(back);

		Image image = new Image(getClass().getResourceAsStream("/buttonImages/splash2.png"));
		View.setBackgroundScreen(image, 0, 0);

		borderPane.setBackground(View.getBackgroundScreen());

		BorderPane.setMargin(scroll, new Insets(0, borderRight, borderBottom, borderLeft));
		borderPane.setCenter(scroll);
		borderPane.setBottom(backPane);

		this.root = new Group();
		root.getChildren().add(borderPane);
		this.scene = new Scene(this.root, tutorialCanvasWidth, View.getCanvasHeight());

	}

	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return this.scene;
	}

	public void createAccordion() {

		accordion = new Accordion();

		TitledPane pane1 = new TitledPane("How to Get Started", createGetStarted());
		TitledPane pane2 = new TitledPane("How to Navigate to Different Screens", createNavigation());
		TitledPane pane3 = new TitledPane("How to Add Existing Plants", createAddExistingPlants());
		TitledPane pane4 = new TitledPane("About the Questionnaire", createQuestionnaireTutorial());
		TitledPane pane5 = new TitledPane("How to Select Plants", createSelectingPlants());
		TitledPane pane6 = new TitledPane("About Your Plot Design", createPlotDesign());
		TitledPane pane7 = new TitledPane("About the Garden Previewer", createGardenPreviewer());
		TitledPane pane8 = new TitledPane("About the Load and Save Plot Screen", createDownload());
		TitledPane pane9 = new TitledPane("About the Plant Database", createPlantDatabase());
		TitledPane pane10 = new TitledPane("About the Plant Info Screen", createPlantInfo());
		TitledPane pane11 = new TitledPane("About Adding Obstacles to Your Plot", createObstacles());
		TitledPane pane12 = new TitledPane("Where the Plant Information Comes From", createAbout());

		List<TitledPane> accArr = new ArrayList<TitledPane>();
		accArr.add(pane1);
		accArr.add(pane2);
		accArr.add(pane3);
		accArr.add(pane4);
		accArr.add(pane5);
		accArr.add(pane6);
		accArr.add(pane7);
		accArr.add(pane8);
		accArr.add(pane9);
		accArr.add(pane10);
		accArr.add(pane11);
		accArr.add(pane12);

		for (TitledPane t : accArr) {
			t.setFont(Font.loadFont(getClass().getResourceAsStream(View.getHackBold()),
					View.getTextSizeForButtonsAndText()));
			accordion.getPanes().add(t);
		}
	}

	/**
	 * Creates the text for the information in each of the dropdown menus
	 * 
	 * @param s          A sentence that will be displayed on the screen
	 * @param contentBox The VBox that the Text will be put into
	 */
	public void createContentText(String s, VBox contentBox) {
		Text message = new Text(s);
		message.setFont(Font.loadFont(getClass().getResourceAsStream(View.getHackBold()), infoTextSize));
		message.setWrappingWidth(messageWrapWidth);
		contentBox.getChildren().add(message);
	}

	/**
	 * Create the corresponding image and adds it to the content box
	 * 
	 * @param img
	 * @param contentBox
	 */
	public void createContentImage(String img, VBox contentBox) {
		Image image = new Image(getClass().getResourceAsStream(img));
		ImageView iv = new ImageView(image);
		iv.setStyle("-fx-padding: 10");

		/**
		 * TODO: may have to add parameters for width and height for each image. Blocked
		 * until program is finished an we can start taking screenshots of the screens
		 */
		iv.setFitHeight(70);
		iv.setFitWidth(150);
		contentBox.getChildren().add(iv);
	}

	/**
	 * Sets up the foundation for the Getting Started drop down
	 * 
	 * @return The content box
	 */
	public VBox createGetStarted() {

		VBox contentBox = new VBox();
		createContentText("Click on the get started button on the main screen.", contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the start new plot button
		createContentText("Follow the instruction on each of the screen to complete your design garden.", contentBox);
		return contentBox;

	}

	/**
	 * Sets up how the user can navigate between screens
	 * 
	 * @return The content Box
	 */
	public VBox createNavigation() {
		VBox contentBox = new VBox();
		createContentText(
				"Click on the buttons at the bottom of the screen to either go back to the previous screen or move on to the next one.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the Go Back and Next Buttons
		return contentBox;
	}

	/**
	 * Sets up how the user can use the Existing Plants screen
	 * 
	 * @return The content box
	 */
	public VBox createAddExistingPlants() {
		VBox contentBox = new VBox();
		createContentText(
				"Search the plants you already have in your garden by typing either the plant's Latin or common name. Once selected, the name of the plant will become bold and your selected plant will appear in the box on the right.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the user selecting a plant
		createContentText(
				"If your would like to remove the plant from your selection, click the X button on the left of the corresponding plant name.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the user hovering over the X next to a
																		// plant's name
		createContentText("Select SAVE before you continue.", contentBox);
		return contentBox;
	}

	/**
	 * Gives information about the Questionnaire screen
	 * 
	 * @return The content Box
	 */
	public VBox createQuestionnaireTutorial() {
		VBox contentBox = new VBox();
		createContentText(
				"Answer all the questions to the best of your ability. Your answers will help us filter out plants that cannot be placed in your garden and help you select plants that are native to your area.",
				contentBox);
		return contentBox;
	}

	/**
	 * Information about using the Plant Selection screen
	 * 
	 * @return The Content Box
	 */
	public VBox createSelectingPlants() {
		VBox contentBox = new VBox();
		createContentText(
				"On the Plant Selection screen, you will see 4 options corresponding to different canopy levels.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the 4 canopies
		createContentText(
				"Click the 'Add Plant' button to select the plants from each canopy level for a garden that supports plants that require different levels of sunlight.",
				contentBox);
		createContentText("If you would like more infomation about the plant, click on the info button to learn more",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the info and add plant button
		createContentText(
				"If you would like to remove your plant selection, click the 'Remove' button on the corresponding plant.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the remove button with the plant
		return contentBox;
	}

	/**
	 * Tells the user how to use the Plot Design screen
	 * 
	 * @return The Content Box
	 */
	public VBox createPlotDesign() {

		VBox contentBox = new VBox();
		createContentText(
				"Your house is located at the bottom edge of the screen as if you are looking at your garden with a bird's eye view.",
				contentBox);
		createContentText("Click and drag your plants from the Existing Plants and Selected Plants drop down menus.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the drop down menu and clicking and
																		// dragging
		createContentText(
				"The Obstacles drop down menu has buttons that you can click on to make the object appear on your plot. Each of the obstacles in the drop down menu are selected from your answers in the Questionnnaire screen.",
				contentBox);
		return contentBox;
	}

	/**
	 * Tell the user about the Garden Previewer screen
	 * 
	 * @return The Content Box
	 */
	public VBox createGardenPreviewer() {

		VBox contentBox = new VBox();
		createContentText("Select the season you would like to view your garden in.", contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the season toggles
		createContentText(
				"Selecting the different years will give you a view of how your garden would look in 0, 1, or 2 years.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the year toggles
		createContentText(
				"The different views will also allow your to see different perspectives of your plot. The TOP VIEW will show you a view of your garden in a 'bird's eye view', and the WINDOW VIEW will show you a view of your garden looking out the window from your house.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the view toggles
		createContentText("Click SAVE to see the new image appear.", contentBox);
		// add image of different view examples?
		return contentBox;
	}

	/**
	 * Tells the user about how to use the Download screen
	 * 
	 * @return The Content Box
	 */
	public VBox createDownload() {

		VBox contentBox = new VBox();
		createContentText(
				"You can load your plot to access and edit it at a later time. You can also select how you would like to download the image of your plot from the selection chosen from the previous screen. ",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the how downloading works
		return contentBox;
	}

	/**
	 * Tells the user more about the Plant Database and what it should be used for
	 * 
	 * @return The Content Box
	 */
	public VBox createPlantDatabase() {

		VBox contentBox = new VBox();
		createContentText(
				"The Plant DataBase button on the Plot Design screen will take you to where you can look at ALL plants, including plants not in your native area. You can search and choose which plants you'd like to add to your garden.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the plant data base screen
		return contentBox;
	}

	/**
	 * Gives an explanation about the Plant Info screen
	 * 
	 * @return The Content Box
	 */
	public VBox createPlantInfo() {
		VBox contentBox = new VBox();
		createContentText(
				"The Plant Info button will take you to a screen that shows you all the information of the plant. This information allows your to learn more about the plants and help you decide which plants to add from the Existing Plants screen and the Plant Database",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of the Plant Info screen and how it works
		return contentBox;
	}

	/**
	 * Tells the user how the obstacles are used on the Plot Design
	 * 
	 * @return The content Box
	 */
	public VBox createObstacles() {
		VBox contentBox = new VBox();
		createContentText(
				"Add the obstacles from the buttons in the drop down menu in the Plot Design screen. You can adjust the object by clicking the corresponding Resume Edit/Complete Edit Button. The yellow dots on the object will allow you to change the size and/or shape of the object to your desire.",
				contentBox);
		createContentImage("/buttonImages/fiveleaf.png", contentBox); // image of adding an obstacle
		return contentBox;
	}

	/**
	 * Tells the user where the plant information is coming from and links to the
	 * open source information
	 * 
	 * @return
	 */
	public VBox createAbout() {
		VBox contentBox = new VBox();
		createContentText("This project was created from the following data bases.", contentBox);
		createContentText("Add link", contentBox); // hyperlink?
		createContentText("Add link", contentBox); // hyperlink?
		createContentText("Add link", contentBox); // hyperlink?
		createContentText("Add link", contentBox); // hyperlink?
		return contentBox;
	}

}
