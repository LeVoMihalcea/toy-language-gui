package View.Commands;

import Controller.Controller;

public class RunCommand extends Command {
    private Controller ctrl;
    public RunCommand(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctrl =ctr;
    }
    @Override
    public void execute() {
        try{
            ctrl.allStep();
        }
        catch (RuntimeException re) {
            System.out.println(re.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}