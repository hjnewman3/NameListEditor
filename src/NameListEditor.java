import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameListEditor extends Application {

    Image icon;

    public NameListEditor() {
        icon = new Image(NameListEditor.class.getResource("/img/icon.png").toExternalForm());
    }

    Stage stage;
    Scene scene;
    TextArea leftTextArea, rightTextArea, bottomBarDisplay;
    String rightContents;


    public static void main(String[] args) { launch(args);}

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Name List Editor 2.0");

        //Create the Top Menu
        VBox topMenu = new VBox();

        //Create the Menu Bar
        MenuBar menuBar = new MenuBar();

        //Create the File Menu
        Menu fileMenu = new Menu("File");
        MenuItem openOption = new MenuItem("Open");
        //Sets the action to open a new file
        openOption.setOnAction(e -> openFile());
        MenuItem saveOption = new MenuItem("Save");
        //Sets the option to save a file
        saveOption.setOnAction(e -> saveFile());
        MenuItem closeOption = new MenuItem("Close");
        //Sets the option to close the file
        closeOption.setOnAction(e -> stage.close());
        //Adds the File Menu's options
        fileMenu.getItems().addAll(openOption, saveOption, closeOption);

        //Create the Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutOption = new MenuItem("About");
        //Sets the action for the About button
        aboutOption.setOnAction(e -> aboutWindow());
        helpMenu.getItems().addAll(aboutOption);

        //Adds the Menus to the Menu Bar
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        //Create the Tab Pane
        TabPane tabPane = new TabPane();

        //Create the Space Invaders Tab
        Tab tab1 = new Tab("Space Invaders");
        tab1.setClosable(false);
        //Creates Tab 1's Tool Bar
        ToolBar tab1ToolBar = new ToolBar();
        Button analyzespacesButton = new Button("Analyze Spaces");
        //Sets the action for the Analyze Spaces button
        analyzespacesButton.setOnAction(e -> analyzeSpaces());
        Button removeSpacesButton = new Button("Remove Spaces");
        //Sets the action for the Remove Spaces button
        removeSpacesButton.setOnAction(e -> removeSpaces());
        //Adds the buttons to the Space Invaders Tool Bar
        tab1ToolBar.getItems().addAll(analyzespacesButton, removeSpacesButton);
        tab1.setContent(tab1ToolBar);

        //Create Tabby Tab Tab
        Tab tab2 = new Tab("Tabby Tab");
        tab2.setClosable(false);
        //Creates Tab 2's Tool Bar
        ToolBar tab2ToolBar = new ToolBar();
        //Creates the Tabby Tab's Buttons & assigns their action
        Button name1TitleButton = new Button("Name & 1 Title");
        name1TitleButton.setOnAction(e -> name1Title());
        Button name2TitlesButton = new Button("Name & 2 Titles");
        name2TitlesButton.setOnAction(e -> name2Titles());
        Button name3TitlesButton = new Button("Name & 3 Titles");
        name3TitlesButton.setOnAction(e -> name3Titles());
        Button name4TitlesButton = new Button("Name & 4 Titles");
        name4TitlesButton.setOnAction(e -> name4Titles());
        Button name5TitlesButton = new Button("Name & 5 Titles");
        name5TitlesButton.setOnAction(e -> name5Titles());

        //Adds the buttons to the Tabby Tab Tool Bar
        tab2ToolBar.getItems().addAll(name1TitleButton, name2TitlesButton, name3TitlesButton, name4TitlesButton, name5TitlesButton);
        tab2.setContent(tab2ToolBar);

        //Adds the Tabs to the Tab Pane
        tabPane.getTabs().addAll(tab1, tab2);

        //Create a Label over each Text Area
        GridPane labelGrid = new GridPane();
        labelGrid.getStyleClass().add("text-area-label");
        labelGrid.setPrefHeight(20);
        Label originalLabel = new Label("Original Name List");
        Label modifiedLabel = new Label("Modified Name List");
        labelGrid.setConstraints(originalLabel, 0, 1);
        labelGrid.setConstraints(modifiedLabel, 1, 1);
        labelGrid.setHgrow(originalLabel, Priority.SOMETIMES);
        labelGrid.setHgrow(modifiedLabel, Priority.SOMETIMES);
        labelGrid.setHalignment(originalLabel, HPos.CENTER);
        labelGrid.setHalignment(modifiedLabel, HPos.CENTER);
        labelGrid.getChildren().addAll(originalLabel, modifiedLabel);

        //Create the Side-by-Side Text Area
        HBox centerText = new HBox();
        leftTextArea = new TextArea();
        leftTextArea.setPromptText("Original Name List");
        rightTextArea = new TextArea();
        rightTextArea.setPromptText("Modified Name List");
        centerText.setHgrow(leftTextArea, Priority.ALWAYS);
        centerText.setHgrow(rightTextArea, Priority.ALWAYS);

        //Create bottom panel / display bar
        bottomBarDisplay = new TextArea("");
        bottomBarDisplay.getStyleClass().add("bottom-bar");
        bottomBarDisplay.setPrefHeight(10);
        bottomBarDisplay.setEditable(false);

        //Create Main Frame & Attaches the Elements
        centerText.getChildren().addAll(leftTextArea, rightTextArea);
        topMenu.getChildren().addAll(menuBar, tabPane, labelGrid);
        BorderPane layout = new BorderPane();
        layout.setTop(topMenu);
        layout.setCenter(centerText);
        layout.setBottom(bottomBarDisplay);

        //Creates the main scene
        scene = new Scene(layout, 800, 800);
        stage.setScene(scene);
        scene.getStylesheets().setAll(getClass().getResource("FBPI.css").toExternalForm());
        stage.getIcons().add(icon);
        stage.show();

    }

    public void aboutWindow() {
        Pane aboutPane = new Pane();
        WebView browser = new WebView();
        Scene aboutScene = new Scene(aboutPane, 500, 600);
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Window HTML");
        aboutStage.setScene(aboutScene);
        aboutPane.getChildren().add(browser);
        URL url = getClass().getResource("/docs/about.html");
        browser.getEngine().load(url.toExternalForm());
        aboutStage.show();
    }

    public void openFile() {
        leftTextArea.setText("");
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File");
        File openFile = fc.showOpenDialog(stage);
        if (openFile != null) {
            try {
                Scanner scan = new Scanner(new FileReader(openFile.getPath()));
                while (scan.hasNext())
                    leftTextArea.appendText(scan.nextLine() + "\r\n");
            }
            catch (IOException e) {
                errorMessage();
            }
        }
    }

    public void saveFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save File");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File saveFile = fc.showSaveDialog(stage);
        if (saveFile != null) {
            try {
                FileWriter fw = new FileWriter(saveFile);
                fw.write(rightContents);
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                bottomBarDisplay.setText("An ERROR occured while saving the file!");
                return;
            }
            bottomBarDisplay.setText("File Saved: " + saveFile.toString());
        }
        else {
            bottomBarDisplay.setText("File save cancelled.");
        }
    }

    public void actionComplete() {
        bottomBarDisplay.setStyle("-fx-text-fill: BLACK;");
        bottomBarDisplay.setText("Modification completed, don't forget to save your new list!");
    }

    public void errorMessage() {
        bottomBarDisplay.setStyle("-fx-text-fill: RED;");
        bottomBarDisplay.setText("Opps, there was a problem. Check the Original Name List for errors");
    }

    public void analyzeSpaces() {
        String contents = leftTextArea.getText();
        Integer spaceCounter = 0;
        String hemi = "Hemi";
        String it = "it";
        Pattern p = Pattern.compile("\\s+[ ]");
        Matcher m = p.matcher(contents);
        while (m.find()) {
            spaceCounter++;
        }
        if (spaceCounter != 1) {
            hemi = "Hemi's";
            it = "Them";
        }
        bottomBarDisplay.setText("Found " + spaceCounter + " " + hemi + " , Click Remove Spaces if You'd Like to Remove " + it);
    }

    public void removeSpaces() {
        //Grabs the contents of the left side textAra
        String contents = leftTextArea.getText();
        //Splits those contents and adds them to a String Array
        String[] contentsToEdit = contents.split("\\s+".trim());
        //Creates a StringBuilder to build the layout
        StringBuilder sb = new StringBuilder();
        //Iterates through the Array to concatenate "FirstName LastName"
        try {
            for (int i = 0; i < contentsToEdit.length; i++) {
                sb.append(contentsToEdit[i] + " ");
                i++;
                sb.append(contentsToEdit[i] + "\r\n");
            }
            //Create a String variable with the value of each line formed by the StringBuilder
            rightContents = sb.toString();
            //Displays that new String out to the right side textArea
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e) {
            errorMessage();
        }
    }

    public void name1Title() {
        String contents = leftTextArea.getText();
        String[] contentsToSplit = contents.split("\\s*\\r?\\n\\s*");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < contentsToSplit.length; i+=2) {
                String line1 = contentsToSplit[i];
                String line2 = contentsToSplit[i + 1];

                String[] contentsLine1 = line1.split("\\s+".trim());
                String[] contentsLine2 = line2.split("\\s+".trim());

                for (int a = 0; a < contentsLine1.length; a++) {
                    if (a > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine1[a]);
                }
                sb.append("\t");

                for (int b = 0; b < contentsLine2.length; b++) {
                    if (b > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine2[b]);
                }
                sb.append("\r\n");
            }
            rightContents = sb.toString();
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e) {
            errorMessage();
        }

    }

    public void name2Titles() {
        String contents = leftTextArea.getText();
        String[] contentsToSplit = contents.split("\\s*\\r?\\n\\s*");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < contentsToSplit.length; i += 3) {
                String line1 = contentsToSplit[i];
                String line2 = contentsToSplit[i + 1];
                String line3 = contentsToSplit[i + 2];

                String[] contentsLine1 = line1.split("\\s+".trim());
                String[] contentsLine2 = line2.split("\\s+".trim());
                String[] contentsLine3 = line3.split("\\s+".trim());

                for (int a = 0; a < contentsLine1.length; a++) {
                    if (a > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine1[a]);
                }
                sb.append("\t");

                for (int b = 0; b < contentsLine2.length; b++) {
                    if (b > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine2[b]);
                }
                sb.append("\t");

                for (int c = 0; c < contentsLine3.length; c++) {
                    if (c > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine3[c]);
                }
                sb.append("\r\n");
            }
            rightContents = sb.toString();
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e){
            errorMessage();
        }
    }

    public void name3Titles() {
        String contents = leftTextArea.getText();
        String[] contentsToSplit = contents.split("\\s*\\r?\\n\\s*");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < contentsToSplit.length; i+=4) {
                String line1 = contentsToSplit[i];
                String line2 = contentsToSplit[i + 1];
                String line3 = contentsToSplit[i + 2];
                String line4 = contentsToSplit[i + 3];

                String[] contentsLine1 = line1.split("\\s+".trim());
                String[] contentsLine2 = line2.split("\\s+".trim());
                String[] contentsLine3 = line3.split("\\s+".trim());
                String[] contentsLine4 = line4.split("\\s+".trim());

                for (int a = 0; a < contentsLine1.length; a++) {
                    if (a > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine1[a]);
                }
                sb.append("\t");

                for (int  b = 0; b < contentsLine2.length; b++) {
                    if (b > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine2[b]);
                }
                sb.append("\t");

                for (int c = 0; c < contentsLine3.length; c++) {
                    if (c > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine3[c]);
                }
                sb.append("\t");

                for (int d = 0; d < contentsLine4.length; d++) {
                    if (d > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine4[d]);
                }
                sb.append("\r\n");
            }
            rightContents = sb.toString();
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e) {
            errorMessage();
        }
    }

    public void name4Titles() {
        String contents = leftTextArea.getText();
        String[] contentsToSplit = contents.split("\\s*\\r?\\n\\s*");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < contentsToSplit.length; i += 5) {
                String line1 = contentsToSplit[i];
                String line2 = contentsToSplit[i + 1];
                String line3 = contentsToSplit[i + 2];
                String line4 = contentsToSplit[i + 3];
                String line5 = contentsToSplit[i + 4];

                String[] contentsLine1 = line1.split("\\s+".trim());
                String[] contentsLine2 = line2.split("\\s+".trim());
                String[] contentsLine3 = line3.split("\\s+".trim());
                String[] contentsLine4 = line4.split("\\s+".trim());
                String[] contentsLine5 = line5.split("\\s+".trim());

                for (int a = 0; a < contentsLine1.length; a++) {
                    if (a > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine1[a]);
                }
                sb.append("\t");

                for (int b = 0; b < contentsLine2.length; b++) {
                    if (b > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine2[b]);
                }
                sb.append("\t");

                for (int c = 0; c < contentsLine3.length; c++) {
                    if (c > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine3[c]);
                }
                sb.append("\t");

                for (int d = 0; d < contentsLine4.length; d++) {
                    if (d > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine4[d]);
                }
                sb.append("\t");

                for (int e = 0; e < contentsLine5.length; e++) {
                    if (e > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine5[e]);
                }
                sb.append("\r\n");
            }
            rightContents = sb.toString();
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e) {
            errorMessage();
        }
    }

    public void name5Titles() {
        String contents = leftTextArea.getText();
        String[] contentsToSplit = contents.split("\\s*\\r?\\n\\s*");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < contentsToSplit.length; i+=6) {
                String line1 = contentsToSplit[i];
                String line2 = contentsToSplit[i + 1];
                String line3 = contentsToSplit[i + 2];
                String line4 = contentsToSplit[i + 3];
                String line5 = contentsToSplit[i + 4];
                String line6 = contentsToSplit[i + 5];

                String[] contentsLine1 = line1.split("\\s+".trim());
                String[] contentsLine2 = line2.split("\\s+".trim());
                String[] contentsLine3 = line3.split("\\s+".trim());
                String[] contentsLine4 = line4.split("\\s+".trim());
                String[] contentsLine5 = line5.split("\\s+".trim());
                String[] contentsLine6 = line6.split("\\s+".trim());

                for (int a = 0; a < contentsLine1.length; a++) {
                    if (a > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine1[a]);
                }
                sb.append("\t");

                for (int b = 0; b < contentsLine2.length; b++) {
                    if (b > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine2[b]);
                }
                sb.append("\t");

                for (int c = 0; c < contentsLine3.length; c++) {
                    if (c > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine3[c]);
                }
                sb.append("\t");

                for (int d = 0; d < contentsLine4.length; d++) {
                    if (d > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine4[d]);
                }
                sb.append("\t");

                for (int e = 0; e < contentsLine5.length; e++) {
                    if (e > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine5[e]);
                }
                sb.append("\t");

                for (int f = 0; f < contentsLine6.length; f++) {
                    if (f > 0) {
                        sb.append(" ");
                    }
                    sb.append(contentsLine6[f]);
                }
                sb.append("\r\n");
            }
            rightContents = sb.toString();
            rightTextArea.setText(rightContents);
            actionComplete();
        }
        catch (Exception e) {
            errorMessage();
        }

    }
}

