package View;

import Controller.Controller;
import Model.Exceptions.SuperCoolException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide a path for the log: ");
        String logFilePath = scanner.nextLine();
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }

        controller.setLogFile(logFile);
        String input = "1";
        while(!input.equals("0")){
            System.out.println("0. Exit");
            System.out.println("1. Complete execution");

            input = scanner.nextLine();
            try{
                if(input.equals("1")){
                    controller.allStep();
                }
            }
            catch(SuperCoolException sce){
                System.out.println(sce.getMessage());
                break;
            }
        }
        logFile.close();
    }
}
