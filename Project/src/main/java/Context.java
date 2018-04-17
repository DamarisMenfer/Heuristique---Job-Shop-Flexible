import java.util.ArrayList;
import java.util.List;

public class Context {

    List<Job> jobs;
    List<Machine> machines;

    public Context() {
        jobs = new ArrayList<Job>();
        machines = new ArrayList<Machine>();
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public Job getJobById(int id){
        return jobs.get(id-1);
    }

    public void addJobs(Job job) {
        this.jobs.add(job);
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public Machine getMachineById(int id){
        return machines.get(id-1);
    }

    public void addMachines(Machine machine) {
        this.machines.add(machine);
    }

    public void printContext(){
        for (int i = 0; i < jobs.size(); i++){
            System.out.println("Job: "+jobs.get(i).getId());
        }
        for (int i = 0; i < jobs.size(); i++){
            System.out.println("Machine: "+machines.get(i).getId());
        }

    }


    public void findSolution(){
        //chose machines to use.
        //Actualise datedeDebout plus tot Operation.
        //put choices in machine op list (ordered by op id).

        //See total time.

        //Take care: solution not possible.
    }
}
