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
        for (Job job:jobs){
            System.out.println("Job: "+job.getId());
            for(Operation operation:job.getOperations()){
                System.out.println("Operation: " + operation.getId() +
                                    " DateDeDebout: " + operation.getDateDeDebut() +
                                    " Duration: " + operation.getDuration() +
                                    " Machine: " + operation.getChosedMachine().getId());
            }
        }
        for (Machine machine:machines){
            System.out.println("Machine: "+ machine.getId());
            for(Operation operation:machine.getOperations()){
                System.out.println("Operation: " + operation.getId() +
                                    " Job: " + operation.getIdJob());
            }
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
        generateSolution();

        //Take care: solution not possible. Gerer les conflits dans le ordenance des operations sur le machine.
    }

    private void choseMachinesRandom(){
        for(Job job:jobs){
            for (Operation operation:job.getOperations()){
                HashMap<Machine,Integer> machinesOp = operation.getMachines();
                Random rand = new Random();
                int pos = rand.nextInt(machinesOp.size());
                Machine machineChosed = (new ArrayList<Machine>(machinesOp.keySet())).get(pos);
                operation.setChosedMachine(machineChosed);
            }
        }
    }

    private void actualiseDateDeDebout(){
        for(Job job:jobs){
            job.actualiseOperationsTime();
        }
    }

    private void generateMachinesOperationsList(){
        for(Job job:jobs){
            for (Operation operation:job.getOperations()){
                operation.getChosedMachine().addOperations(operation);
            }
        }
        for(Machine machine:machines){
            machine.orderListOperations();
        }
        for(Job job:jobs){
            job.actualiseOperationsTime();
        }
    }

    private void generateSolution(){
        printContext();
    }
}
