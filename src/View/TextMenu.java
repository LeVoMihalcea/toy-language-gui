package View;

import View.Commands.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){ commands.put(c.getKey(),c);}
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Please provide a path for the log: ");
//        String logFilePath = scanner.nextLine();
//        PrintWriter logFile = null;
//        try {
//            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
//        }
//        catch(IOException ioe){
//            System.out.println(ioe.getMessage());
//        }


        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue; }

            com.execute();
        }
    }
}