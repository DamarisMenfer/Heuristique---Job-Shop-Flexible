import java.util.*;

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
        choseMachinesRandom();
        //Actualise datedeDebout plus tot Operation.
        actualiseDateDeDebout();
        //put choices in machine op list (ordered by date de debout plut tot).
        generateMachinesOperationsList();

        //See total time.

        //Take care: solution not possible. Gerer les conflits dans le ordenance des operations sur le machine.
    }

    private void choseMachinesRandom(){
        for(int i = 0; i < jobs.size(); i++){
            for (int j = 0; j < jobs.get(i).getOperations().size(); j++){
                HashMap<Machine,Integer> machinesOp = jobs.get(i).getOperations().get(j).getMachines();
                Random rand = new Random();
                int pos = rand.nextInt(machinesOp.size());
                Machine machineChosed = (new ArrayList<Machine>(machinesOp.keySet())).get(pos);
                jobs.get(i).getOperations().get(j).setChosedMachine(machineChosed);
            }
        }
    }

    private void actualiseDateDeDebout(){
        for(int i = 0; i < jobs.size(); i++){
            jobs.get(i).actualiseOperationsTime();
        }
    }

    private void generateMachinesOperationsList(){
        for(int i = 0; i < jobs.size(); i++){
            for (int j = 0; j < jobs.get(i).getOperations().size(); j++){
                jobs.get(i).getOperations().get(j).getChosedMachine().addOperations(jobs.get(i).getOperations().get(j));
            }
        }
        for(int i = 0; i < machines.size(); i++){
            machines.get(i).orderListOperations();
        }
    }
}
