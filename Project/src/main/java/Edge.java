public class Edge {
    private Node nodeStart;
    private Node nodeEnd;
    private int duration;

    public Edge (Node nodeStart, Node nodeEnd, int duration){
        this.nodeStart = nodeStart;
        this.nodeEnd = nodeEnd;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Node getNodeStart() {

        return nodeStart;
    }

    public void setNodeStart(Node nodeStart) {
        this.nodeStart = nodeStart;
    }

    public Node getNodeEnd() {
        return nodeEnd;
    }

    public void setNodeEnd(Node nodeEnd) {
        this.nodeEnd = nodeEnd;
    }

    public String toString(){
        return nodeStart.toString() + "--" + duration + "-->" + nodeEnd.toString();
    }
}
