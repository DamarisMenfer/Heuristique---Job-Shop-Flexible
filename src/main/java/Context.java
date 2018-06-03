import java.util.*;

public class Context implements Cloneable {

    private ArrayList<Job> jobs;
    private ArrayList<Machine> machines;
    private Graph graph;
    private Integer totalTime;

    private boolean possibleSolution;

    /***************************************************
     * Construction
     **************************************************/

    public Context() {
        this.jobs = new ArrayList<Job>();
        this.machines = new ArrayList<Machine>();
        this.graph = new Graph();
        totalTime = 10000000;
    }

    /***************************************************
     * getter and setter
     **************************************************/

    public Job getJobById(int id){
        return jobs.get(id-1);
    }

    public void addJobs(Job job) {
        this.jobs.add(job);
    }

    public Machine getMachineById(int id){
        return machines.get(id-1);
    }

    public void addMachines(Machine machine) {
        this.machines.add(machine);
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public boolean isPossibleSolution() {
        return possibleSolution;
    }

    /***************************************************
     * initial solution
     **************************************************/

    public boolean initialSolution(){

        chooseMachinesRandomly();
        generateMachinesOperationsList();
        process();

        return true;
    }

    /*
     * erase operation list executed by machine
     * in case of fail to find initial solution
     *
     */

    public void reinitialize(){
        for(Machine machine: machines){
            machine.removeOperations();
        }
    }

    /*
     * randomly choose machine for each operation
     *
     */

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

    /*
     * add operation into list of its executed machine
     * sort this list by its starting date
     *
     */

    private void generateMachinesOperationsList(){
        for(Job job:jobs){
            for (Operation operation:job.getOperations()){
                operation.getChosenMachine().addOperations(operation);
            }
        }
        for(Machine machine:machines){
            machine.sortOperationList();
        }
    }

    /*
     * create graph based on operation and machine list
     * check the feasibility of solution
     * calculate total time of this solution
     *
     */

    private boolean process() {
        createGraph();
        //printSolution();

        if (checkSolution()){
            calculateTotalTime();
            possibleSolution = true;
            //System.out.println("----------------------total time "+this.getTotalTime()+"\n");
            return true;
        }
        possibleSolution = false;
        //System.out.println("---------solution isn't feasible\n\n");
        return false;
    }

    /***************************************************
     * generate Neighbour
     **************************************************/

    public boolean generateNeighbour(){
        Random rand = new Random();
        int choice = rand.nextInt(2);
        switch (choice){
            case 0:

                Operation opChanged = changeOneMachine();
                if(opChanged == null){
                    return false;
                }
                else {
                    //System.out.print("------generate Neighbour ");
                    //System.out.println("using change machine------------------");
                    updateMachineOperationList(opChanged);
                    return process();
                }
            case 1:
                //System.out.print("------generate Neighbour ");
                //System.out.println("using swap operation in machine list--");
                Machine machineChoosed = chooseMachineToChange();
                swapElementsInOperationList(machineChoosed);
                return process();
        }
        return false;
    }

    /*
     * change chosen machine of a random operation
     *
     */

    private Operation changeOneMachine() {
        Random rand = new Random();

        int randomJob = rand.nextInt(jobs.size());
        Job job = jobs.get(randomJob);

        int randomOp = rand.nextInt(job.getOperations().size());
        Operation operation =  job.getOperations().get(randomOp);
        HashMap<Machine,Integer> machinesOp = operation.getMachines();

        int pos = rand.nextInt(machinesOp.size());
        Machine machineChosed = (new ArrayList<Machine>(machinesOp.keySet())).get(pos);
        if (operation.getChosenMachine() != machineChosed){
            operation.setChosenMachine(machineChosed);
            return operation;
        }
        return null;
    }

    /*
     * update operation in list of its executed machine
     * after randomly choose this machine
     *
     */

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
    }

    private Machine chooseMachineToChange(){
        Random rand = new Random();
        int posMachine;
        Machine machine;
        do {
            posMachine = rand.nextInt(machines.size());
            machine = machines.get(posMachine);
        }
        while (machine.getOperations().size() <= 1);
        return machine;
    }

    private void swapElementsInOperationList(Machine machineSwap){

        Random rand = new Random();
        ArrayList<Operation> listOperations = machineSwap.getOperations();
        int posOperation = rand.nextInt(listOperations.size());
        Operation temp = listOperations.get(posOperation);
        if (posOperation == 0) {
            listOperations.set(posOperation, listOperations.get(posOperation+1));
            listOperations.set(posOperation+1, temp);
        }
        else{
            listOperations.set(posOperation, listOperations.get(posOperation-1));
            listOperations.set(posOperation-1, temp);
        }
    }

    /***************************************************
     * graph structure
     **************************************************/

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

    /*
     * check the feasibility of the whole graph
     */

    private boolean checkSolution (){

        return feasibleSolution(this.graph.findNodeById(-1));
    }

    /*
     * check the feasibility of a node in graph
     */

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

    /*
     * calculate starting date of a node in graph
     */
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

    /*
     * calculate the total time of whole graph
     */
    private void calculateTotalTime() {
        this.totalTime = calculateTime(graph.findNodeById(-1));
        //System.out.println ("total time: " + this.totalTime);
    }

    /***************************************************
     * print function
     **************************************************/

    /*
     * debugger
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

    /*
     * display solution as the form mentioned in the subject
     */

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

}
