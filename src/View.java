import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

public class View {

    private final Controller controller;
    private final BorderPane layout;

    public View(Model model, Controller controller) {

        this.controller = controller;

        layout = new BorderPane();

        VBox topMenu = new VBox();

        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem addToFile = new MenuItem("Add to Current List");
        // TODO: add action

        MenuItem saveOption = new MenuItem("Save");
        // TODO: add action

        MenuItem closeOption = new MenuItem("Close");
        // TODO: add action

        MenuItem openOption = new MenuItem("Open");
        // TODO: add action

        fileMenu.getItems().addAll(openOption, addToFile, saveOption, closeOption);


        // Tools Menu
        Menu toolsMenu = new Menu("Tools");
        MenuItem resetOriginalList = new MenuItem("Reset Original List");
        // TODO: add action

        toolsMenu.getIems().addAll(resetOriginalList);


        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutOption = new MenuItem("About");
        // TODO: add action

        helpMenu.getItems().addAll(aboutOption);



    }
}