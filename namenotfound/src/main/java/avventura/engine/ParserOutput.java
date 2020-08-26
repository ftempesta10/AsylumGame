package avventura.parser;

import avventura.type.AdventureObject;
import avventura.type.Command;


public class ParserOutput {

	private Command command;

    private AdventureObject object;
    
    private AdventureObject invObject;
    
   

	public ParserOutput(Command command, AdventureObject object) {
        this.command = command;
        this.object = object;
    }

    public ParserOutput(Command command, AdventureObject object, AdventureObject invObejct) {
        this.command = command;
        this.object = object;
        this.invObject = invObejct;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public AdventureObject getObject() {
        return object;
    }

    public void setObject(AdventureObject object) {
        this.object = object;
    }

    public AdventureObject getInvObject() {
        return invObject;
    }

    public void setInvObject(AdventureObject invObject) {
        this.invObject = invObject;
    }
    
}
