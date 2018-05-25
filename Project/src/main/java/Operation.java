import java.util.LinkedHashMap;

public class Operation implements Comparable<Operation>{

    private int id;
    private int idJob;

    private int startingDate;                            //Plus tôt (dependant du choix des machines).

    private LinkedHashMap<Machine,Integer> machines;

    private Machine chosenMachine;
    private int processingTime;

    public Operation(int id, int idJob) {
        this.id = id;
        this.idJob = idJob;
        this.startingDate = 0;
        machines = new LinkedHashMap<Machine, Integer>();
    }

    public void addMachines(Integer temps, Machine machine) {
        this.machines.put(machine, temps);
    }

    public LinkedHashMap<Machine, Integer> getMachines() {
        return machines;
    }

    public int getIdJob() {
        return idJob;
    }

    public int getId() {
        return id;
    }

    public int getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(int startingDate) {
        this.startingDate = startingDate;
    }

    public void setChosenMachine(Machine chosenMachine) {
        this.chosenMachine = chosenMachine;
        this.processingTime = machines.get(chosenMachine);
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Machine getChosenMachine() {
        return chosenMachine;
    }

    @Override
    public int compareTo(Operation operationCompare) {
        if (this.idJob == operationCompare.getIdJob()){
            if (this.id < operationCompare.getId()){
                return -1;
            }
            else{
                return 0;
            }
        }
        if (this.getStartingDate() < operationCompare.getStartingDate()) {
            return -1;
        }
        else if (this.getStartingDate() == operationCompare.getStartingDate()) {
            if (this.processingTime < operationCompare.processingTime) {
                return -1;
            }
            else{
                return 0;
            }
        }
        else{
            return 0;
        }
    }

    public String toString(){
        return this.idJob+","+this.id;
    }
}
