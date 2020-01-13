
import Model.Expressions.*;
import Model.Operations.ComparisonOperation;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainWindow = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Toy Language");
        primaryStage.setScene(new Scene(mainWindow));
        primaryStage.show();

        System.out.println("start");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
