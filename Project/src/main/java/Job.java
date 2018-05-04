import java.util.ArrayList;

public class Job {

    private int id;

    private ArrayList<Operation> operations;

    public Job(int id) {
        this.id = id;
        operations = new ArrayList<Operation>();
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

    public void actualiseOperationsTime(){
        int deboutProchaineOperation = 0;
        for(int i = 0; i < operations.size(); i++){
            operations.get(i).setDateDeDebut(deboutProchaineOperation);
            deboutProchaineOperation += operations.get(i).getDuration();
        }
    }

}
