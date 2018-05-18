import java.util.ArrayList;

public class Node {
    private Integer id;
    private Operation op;
    private ArrayList<Edge> neighbour;
    private Integer time;

    public Node (Integer id, Job job, Operation op){
        this.id = id;
        this.op = op;
        neighbour = new ArrayList<Edge>();
    }

    public Integer getId() {
        return id;
    }

    public Operation getOp() {
        return op;
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String toString (){
        String str = this.id+",";
        if (op == null){
            str += null;
        }
        else{
            str += op.toString() + "*" + op.getDateDeDebut();
        }
        return str;
    }
}
