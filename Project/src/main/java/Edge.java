public class Edge {
    private Node nodeStart;
    private Node nodeEnd;
    private int processingTime;

    public Edge (Node nodeStart, Node nodeEnd, int processingTime){
        this.nodeStart = nodeStart;
        this.nodeEnd = nodeEnd;
        this.processingTime = processingTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Node getNodeEnd() {
        return nodeEnd;
    }

    public String toString(){
        return nodeStart.toString() + "--" + processingTime + "-->" + nodeEnd.toString();
    }
}
