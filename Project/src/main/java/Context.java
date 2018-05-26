import java.util.*;

public class Context implements Cloneable {

    private ArrayList<Job> jobs;
    private ArrayList<Machine> machines;
    private Graph graph;
    private Integer totalTime;

    public Context() {
        this.jobs = new ArrayList<Job>();
        this.machines = new ArrayList<Machine>();
        this.graph = new Graph();
    }

    public Context(ArrayList<Job> initialJobs, ArrayList<Machine> initialMachines, Graph initialGraph) {
        this.jobs = initialJobs;
        this.machines = initialMachines;
        this.graph = initialGraph;
    }

    /*
     * setter getter
     */
    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public Job getJobById(int id){
        return jobs.get(id-1);
    }

    public void addJobs(Job job) {
        this.jobs.add(job);
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public Machine getMachineById(int id){
        return machines.get(id-1);
    }

    public void addMachines(Machine machine) {
        this.machines.add(machine);
    }

    public Graph getGraph() {
        return this.graph;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    //************************************************

    /*
     * Initial solution
     */
    public void initialSolution(){
        do{
            reinitialize();
            //chose machines to use.
            chooseMachinesRandomly();
            //Update starting time of operation.
            //updateStartingTime();
            //put choices in machine op list (ordered by date de debut plus tot).
            generateMachinesOperationsList();}
        while(!process());
    }
    public void reinitialize(){
        for(Machine machine: machines){
            machine.removeOperations();
        }
    }
    private void chooseMachinesRandomly(){
        for(Job job:jobs){
            for (Operation operation:job.getOperations()){
                HashMap<Machine,Integer> machinesOp = operation.getMachines();
                Random rand = new Random();
                int pos = rand.nextInt(machinesOp.size());
                Machine machineChosed = (new ArrayList<Machine>(machinesOp.keySet())).get(pos);
                operation.setChosenMachine(machineChosed);
            }
        }
    }

    private void updateStartingTime(){
        for(Job job:jobs){
            job.updateOperationTime();
        }
    }

    private void generateMachinesOperationsList(){
        for(Job job:jobs){
            for (Operation operation:job.getOperations()){
                operation.getChosenMachine().addOperations(operation);
            }
        }
        for(Machine machine:machines){
            machine.sortOperationList();
        }
        /*for(Job job:jobs){
            job.updateOperationTime();
        }*/
    }

    private boolean process() {

        /*
         * display solution as the form mentioned in the subject
         */
        printSolution();
        printContext();

        createGraph();
        //test create graph
        System.out.println(graph.toString());

        if (checkSolution()){
            //calculate total time
            calculateTotalTime();
            System.out.println("----------------------total time "+this.getTotalTime());
            System.out.println(graph.toString());
            return true;
        }
        System.out.println("warning !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(this.getTotalTime());
        System.out.println("warning !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return false;
    }

    //************************************************

    /*
     * generate Neighbour
     */

    public boolean generateNeighbour(){
        System.out.print("------------generate Neighbour");
        Random rand = new Random();
        //int choice = rand.nextInt(1);
        int choice = 1;
        switch (choice){
            case 1:
                System.out.println("using change machine");
                Operation opChanged = changeOneMachine();
                if(opChanged == null){
                    System.out.print("Not a neighbour");
                    return false;
                }
                else {
                    //updateStartingTime();
                    updateMachineOperationList(opChanged);
                    return process();
                }
            case 2:
                Machine machineChoosed = chooseMachineToChange();
                swapElementsInOperationList(machineChoosed);
                return process();
        }
        return true;
    }

    /*
     * change chosen machine of a random operation
     */
    private Operation changeOneMachine() {
        for(Job job:this.jobs){
            for (Operation operation:job.getOperations()){
                HashMap<Machine,Integer> machinesOp = operation.getMachines();
                Random rand = new Random();
                int pos = rand.nextInt(machinesOp.size());
                Machine machineChosed = (new ArrayList<Machine>(machinesOp.keySet())).get(pos);
                if (operation.getChosenMachine() != machineChosed){
                    operation.setChosenMachine(machineChosed);
                    return operation;
                }

            }
        }
        return null;
    }

    private void updateMachineOperationList(Operation changedOp){
        for(Machine machine:machines){
            for(Operation op:machine.getOperations()){
                if (op == changedOp){
                    machine.deleteOperationFromList(changedOp);
                    break;
                }
            }
        }
        changedOp.getChosenMachine().addOperations(changedOp);
        changedOp.getChosenMachine().sortOperationList();
        /*for(Job job:jobs){
            job.updateOperationTime();
        }*/
    }

    private Machine chooseMachineToChange(){
        Random rand = new Random();
        int posMachine = rand.nextInt(machines.size());
        return machines.get(posMachine);
    }

    //TODO error
    private void swapElementsInOperationList(Machine machineSwap){

        Random rand = new Random();
        ArrayList<Operation> listOperations = machineSwap.getOperations();
        int posOperation = rand.nextInt(listOperations.size());
        if(posOperation == 0){
            posOperation++;
        }
        else if(posOperation == listOperations.size()){
            posOperation--;
        }
        Operation temp = listOperations.get(posOperation-1);
        listOperations.set(posOperation-1, listOperations.get(posOperation));
        listOperations.set(posOperation, temp);

        for(Machine machine:machines){
            machine.updateStartingDateInMachine();
        }

        for(Job job:jobs){
            job.updateOperationTime();
        }

    }

    //************************************************

    /*
     * Graph structure
     */

    private void createGraph(){
        this.graph = new Graph();
        int id = 0;
        Node nodeDebut = new Node (id,null);
        Node nodeFin = new Node (-1,null);

        Node nodeStart;
        Node nodeEnd;
        Edge edge;

        for (Job job: jobs){
            for (int index = 0; index < job.getOperations().size(); index++){
                Operation op = job.getOperations().get(index);
                id += 1;
                nodeEnd = new Node(id, op);

                if (index == 0) {
                    edge = new Edge (nodeDebut, nodeEnd, 0);
                    nodeDebut.getNeighbour().add(edge);
                }
                if (index == job.getOperations().size() - 1){
                    edge = new Edge (nodeEnd, nodeFin, nodeEnd.getOp().getProcessingTime());
                    nodeEnd.getNeighbour().add(edge);
                    graph.addNode(nodeEnd);
                }
                else {
                    nodeStart = nodeEnd;
                    nodeEnd = new Node(id + 1, job.getOperations().get(index + 1));
                    edge = new Edge(nodeStart, nodeEnd, nodeStart.getOp().getProcessingTime());
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
                    edge = new Edge (nodeStart, nodeEnd, operations.get(index).getProcessingTime());
                    nodeStart.getNeighbour().add(edge);
                }
            }
        }
    }

    private boolean checkSolution (){
        return feasibleSolution(this.graph.findNodeById(-1));
    }

    private boolean feasibleSolution(Node node) {
        boolean res = true;
        node.setChecking(true);
        if (node.getId() == 0){
            node.setChecking(false);
            return true;
        }
        else {
            ArrayList<Node> sourceNode = this.graph.findSource(node);
            for (Node source : sourceNode) {
                if (source.isChecking() == true){
                    return false;
                }
                else {
                    res = res && feasibleSolution(source);
                }
            }
            node.setChecking(false);
        }
        return res;
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
            duration = sourceNode.get(0).findEdge(node).getProcessingTime();
            timeAux = calculateTime(aux);

            for (Node source : sourceNode) {
                edge = source.findEdge(node);
                if (calculateTime(source) + edge.getProcessingTime() >= timeAux + duration){
                    aux = source;
                    duration = source.findEdge(node).getProcessingTime();
                    timeAux = calculateTime(aux);
                }
            }
            if (node.getId() != -1) {
                node.getOp().setStartingDate(timeAux+duration);
            }
            return timeAux + duration;
        }
    }

    private void calculateTotalTime() {
        this.totalTime = calculateTime(graph.findNodeById(-1));
        //System.out.println ("total time: " + this.totalTime);
    }

    //************************************************

    /*
     * Print function
     */

    public void printContext(){
        for (Job job:jobs){
            System.out.println("Job: "+job.getId());
            for(Operation operation:job.getOperations()){
                System.out.println("Operation: " + operation.getId() +
                        " Starting time: " + operation.getStartingDate() +
                        " Processing time: " + operation.getProcessingTime() +
                        " Machine: " + operation.getChosenMachine().getId());
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
                System.out.print(op.getChosenMachine().getId()+", ");
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

    //************************************************

    @Override
    public Object clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }

    //************************************************
}
