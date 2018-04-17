import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Operation {

    private int id;
    private int idJob;

    private int dateDeDebut;                            //Plus tôt (dependant du choix des machines).

    private HashMap<Machine,Integer> machines;

    public Operation(int id, int idJob) {
        this.id = id;
        this.idJob = idJob;
        machines = new HashMap<Machine, Integer>();
    }

    public void addMachines(Integer temps, Machine machine) {
        this.machines.put(machine, temps);
    }

    public HashMap<Machine, Integer> getMachines() {
        return machines;
    }

    public int getDurationByMachine(Machine machine){
        return machines.get(machine);
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
}
