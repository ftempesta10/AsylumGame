public class ParserOutput {

    private Command command;
    private Item object;
    private Item target;

    public ParserOutput(Command command, Item object) {
        this.command = command;
        this.object = object;
    }

    public ParserOutput(Command command, Item object, Item target) {
        this.command = command;
        this.object = object;
        this.target= target;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Item getObject() {
        return object;
    }

    public void setObject(Item object) {
        this.object = object;
    }

    public Item getTarget() {
        return target;
    }

    public void setTarget(Item target) {
        this.target = target;
    }

}

