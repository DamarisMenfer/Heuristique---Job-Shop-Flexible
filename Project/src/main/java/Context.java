import java.util.*;

public class Context {

    private List<Job> jobs;
    private List<Machine> machines;
    private Graph graph;

    public Context() {
        jobs = new ArrayList<Job>();
        machines = new ArrayList<Machine>();
        graph = new Graph();
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
        generateSolution();
        //Take care: solution not possible. Gerer les conflits dans le ordenance des operations sur le machine.
        //display solution as the form mentioned in the subject
        printSolution();

        createGraph();
        //test create graph
        System.out.println(graph.toString());

        //calculate total time
        calculateTotalTime();
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

    private void createGraph(){
        int id = 0;
        Node nodeDebut = new Node (id, null, null);
        Node nodeFin = new Node (-1, null, null);

        Node nodeStart;
        Node nodeEnd;
        Edge edge;

        for (Job job: jobs){
            for (int index = 0; index < job.getOperations().size(); index++){
                Operation op = job.getOperations().get(index);
                id += 1;
                nodeEnd = new Node(id, job, op);

                if (index == 0) {
                    edge = new Edge (nodeDebut, nodeEnd, 0);
                    nodeDebut.getNeighbour().add(edge);
                }
                if (index == job.getOperations().size() - 1){
                    edge = new Edge (nodeEnd, nodeFin, nodeEnd.getOp().getDuration());
                    nodeEnd.getNeighbour().add(edge);
                    graph.addNode(nodeEnd);
                }
                else {
                    nodeStart = nodeEnd;
                    nodeEnd = new Node(id + 1, job, job.getOperations().get(index + 1));
                    edge = new Edge(nodeStart, nodeEnd, nodeStart.getOp().getDuration());
                    nodeStart.getNeighbour().add(edge);
                    graph.addNode(nodeStart);
                }
            }
        }

        graph.addNode(nodeDebut);
        graph.addNode(nodeFin);

        for (Machine machine: machines){
            ArrayList<Operation> operations = machine.getOperations();
            for (int index = 0; index < operations.size(); index++){
                if (index < operations.size()-1){
                    nodeStart = graph.findNodeByOp(operations.get(index));
                    nodeEnd = graph.findNodeByOp(operations.get(index+1));
                    edge = new Edge (nodeStart, nodeEnd, operations.get(index).getDuration());
                    nodeStart.getNeighbour().add(edge);
                }
            }
        }
    }

    private Integer calculateTime (Node node) {
        Node aux;
        Integer timeAux;
        Integer duration;
        Edge edge;

        if (node.getId() == 0){

            return 0;
        }
        else {
            ArrayList<Node> sourceNode = graph.findSource(node);
            aux = sourceNode.get(0);
            duration = sourceNode.get(0).findEdge(node).getDuration();
            timeAux = calculateTime(aux);

            for (Node source : sourceNode) {
                edge = source.findEdge(node);
                if (calculateTime(source) + edge.getDuration() >= timeAux + duration){
                    aux = source;
                    duration = source.findEdge(node).getDuration();
                    timeAux = calculateTime(aux);
                }
            }
            return timeAux + duration;
        }
    }

    private Integer calculateTotalTime() {
        Integer res = calculateTime(graph.findNodeById(-1));
        System.out.println ("total time: " + res);
        return res;
    }

    private void generateSolution(){
        printContext();
    }
}
