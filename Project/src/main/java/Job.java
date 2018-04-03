import java.util.ArrayList;

public class Job {

    private int id;

    private ArrayList<Operation> operations;

    public Job(int id) {
        this.id = id;
    }

    public void addOperations(Operation operations) {
        this.operations.add(operations);
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
