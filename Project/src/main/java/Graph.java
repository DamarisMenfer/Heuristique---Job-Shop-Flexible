import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> graph;

    public Graph(){
        graph = new ArrayList<Node>();
    }

    public void addNode (Node node){
        graph.add(node);
    }

    public Node findNodeByOp(Operation op){
        for (Node node: graph){
            if (node.getOp().getIdJob() == op.getIdJob() && node.getOp().getId() == op.getId()){
                return node;
            }
        }
        return null;
    }

    public Node findNodeById (Integer id){
        for (Node node: graph){
            if (node.getId() == id){
                return node;
            }
        }
        return null;
    }

    public ArrayList<Node> findSource (Node node){
        ArrayList<Node> sourceNode = new ArrayList<Node>();
        for (Node checkNode: graph){
            if (checkNode.findEdge(node) != null){
                sourceNode.add(checkNode);
            }
        }
        return sourceNode;
    }

    public String toString(){
        String res = "";
        for (Node node: graph){
            res += node.toString() + " --> ";
            for (Edge neighbour: node.getNeighbour()) {
                res += neighbour.toString() + "  ";
            }
            res += "\n";
        }
        return res;
    }
}
