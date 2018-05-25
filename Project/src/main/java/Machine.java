import java.util.ArrayList;
import java.util.Collections;

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

    public void sortOperationList(){
        Collections.sort(operations);
        updateStartingDateInMachine();
    }

    public void updateStartingDateInMachine(){
        int updatedStartingDate = 0;
        for(Operation operation:operations){
            if(updatedStartingDate < operation.getStartingDate()){
                updatedStartingDate = operation.getStartingDate();
            }
            operation.setStartingDate(updatedStartingDate);
            updatedStartingDate += operation.getProcessingTime();
        }
    }

    public void deleteOperationFromList(Operation op){
        operations.remove(op);
    }
}
