package View.Commands;

import java.io.PrintWriter;

public class ExitCommand extends Command {
    PrintWriter logFile;
    public ExitCommand(String key, String desc, PrintWriter logFile){
        super(key, desc);
        this.logFile = logFile;
    }
    @Override
    public void execute() {
        if(logFile!= null)
            logFile.close();
        System.exit(0);
    }
}