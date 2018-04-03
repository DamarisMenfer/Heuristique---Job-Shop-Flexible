import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Operation {

    private int id;

    private int dateDeDebut;                            //Plus tôt.

    private HashMap<Machine,Integer> machines;

    public Operation(int id, int dateDeDebut) {
        this.id = id;
        this.dateDeDebut = dateDeDebut;
    }

    public void addMachines(Integer temps, Machine machine) {
        this.machines.put(machine, temps);
    }

    public HashMap<Machine, Integer> getMachines() {
        return machines;
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
