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
        actualiseDateDeDeboutOperations();
    }

    public void actualiseDateDeDeboutOperations(){
        int dateDeDeboutActualised = 0;
        for(Operation operation:operations){
            if(dateDeDeboutActualised < operation.getDateDeDebut()){
                dateDeDeboutActualised = operation.getDateDeDebut();
            }
            operation.setDateDeDebut(dateDeDeboutActualised);
            dateDeDeboutActualised += operation.getDuration();
        }
    }

    public void deleteOperationFromList(Operation op){
        operations.remove(op);
    }
}
