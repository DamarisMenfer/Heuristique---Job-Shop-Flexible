import java.util.ArrayList;

public class Machine {

    private int id;

    private ArrayList<Operation> operations;

    public Machine(int id) {
        this.id = id;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void addOperations(Operation operations) {
        this.operations.add(operations);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
