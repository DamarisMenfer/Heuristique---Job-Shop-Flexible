import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Operation implements Comparable<Operation>{

    private int id;
    private int idJob;

    private int dateDeDebut;                            //Plus tôt (dependant du choix des machines).

    private LinkedHashMap<Machine,Integer> machines;

    private Machine chosedMachine;
    private int duration;

    public Operation(int id, int idJob) {
        this.id = id;
        this.idJob = idJob;
        machines = new LinkedHashMap<Machine, Integer>();
    }

    public void addMachines(Integer temps, Machine machine) {
        this.machines.put(machine, temps);
    }

    public LinkedHashMap<Machine, Integer> getMachines() {
        return machines;
    }

    public int getDurationByMachine(Machine machine){
        return machines.get(machine);
    }

    public int getIdJob() {
        return idJob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDateDeDebut() {
        return dateDeDebut;
    }

    public void setDateDeDebut(int dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public void setChosedMachine(Machine chosedMachine) {
        this.chosedMachine = chosedMachine;
        this.duration = machines.get(chosedMachine);
    }

    public int getDuration() {
        return duration;
    }

    public Machine getChosedMachine() {
        return chosedMachine;
    }

    @Override
    public int compareTo(Operation operationCompare) {
        int result;
        if (this.getDateDeDebut() < operationCompare.getDateDeDebut()) {
            result = -1;
        }
        else{
            result = 0;
        }
        return result;
    }
}
