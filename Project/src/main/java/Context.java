import java.util.*;

public class Context {

    private List<Job> jobs;
    private List<Machine> machines;

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
                System.out.println("Job: " + operation.getIdJob() +
                                    " Operation: " + operation.getId());
            }
        }
    }

    public void printSolution(){
        printMachineAssignement();
        printOperationSequence();
    }

    private void printMachineAssignement (){
        System.out.print("MA: (");
        for (Job job: jobs){
            for (Operation op: job.getOperations()) {
                System.out.print(op.getChosedMachine().getId()+", ");
            }
            System.out.print("|");
        }
        System.out.print(")\n");
    }

    private void printOperationSequence(){
        System.out.print("OS: (");
        ArrayList<Operation> os = new ArrayList<Operation>();
        for (Job job: jobs){
            for (Operation op: job.getOperations()){
                os.add(op);
            }
        }

        Collections.sort(os);
        for (Operation op: os){
            System.out.print(op.getIdJob() + ", ");
        }
        System.out.print(")\n");

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
        printSolution();
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
