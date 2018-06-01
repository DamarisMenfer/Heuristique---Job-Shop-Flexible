import java.util.ArrayList;

public class Node {
    private Integer id;
    private Operation op;
    private ArrayList<Edge> neighbour;
    private boolean checking;

    public Node (Integer id, Operation op){
        this.id = id;
        this.op = op;
        neighbour = new ArrayList<Edge>();
        checking = false;
    }

    public Integer getId() {
        return id;
    }

    public Operation getOp() {
        return op;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    public ArrayList<Edge> getNeighbour() {
        return neighbour;
    }

    public Edge findEdge (Node node){
        for (Edge edge: neighbour ){
            if (edge.getNodeEnd().getId() == node.getId()){
                return edge;
            }
        }
        return null;
    }

    public String toString (){
        String str = this.id+",";
        if (op == null){
            str += null;
        }
        else{
            str += op.toString() + "*" + op.getStartingDate();
        }
        return str;
    }
}
