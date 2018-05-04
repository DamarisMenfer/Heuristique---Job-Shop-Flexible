import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collection.*;

public class Machine {

    private int id;

    private ArrayList<Operation> operations;

    public Machine(int id) {
        this.id = id;
        operations = new ArrayList<Operation>();
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

    public void orderListOperations(){
        Collections.sort(operations);

    }
}
