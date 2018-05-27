import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String [] args) {

        String dotFormat="1->2;1->3;1->4;4->5;4->6;6->7;5->7;3->8;3->6;8->7;2->8;2->5;";
        VisualizeGraph.createDotGraph(dotFormat, "DotGraph");

        Context context = new Context();
        initializeContext(context);
        context.initialSolution();

        Context neighbourContext = new Context(context.getJobs(), context.getMachines(), context.getGraph());
        Integer count = 0;

        while (count < 20) {
            count ++;
            System.out.println(count);
            neighbourContext = (Context) context.clone();
            if (!neighbourContext.generateNeighbour()){
                System.out.println("fail check neighbour ");
                neighbourContext = (Context) context.clone();
            }
            else if (neighbourContext.getTotalTime() < context.getTotalTime()){
                System.out.println("found new neighbour");
                neighbourContext.printSolution();
                System.out.println(neighbourContext.getTotalTime());
                context = (Context) neighbourContext.clone();
            }
        }
        System.out.println("*****************************************");
        neighbourContext.printSolution();
        System.out.println(context.getTotalTime());
    }

    private static void initializeContext(Context context){

            // The name of the file to open.
            String fileName = "Exemple TP.txt";

            // This will reference one line at a time
            String line = null;

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                        new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                int countLine = 0;

                while((line = bufferedReader.readLine()) != null) {
                    if(!line.equals("")){
                        if(countLine == 0){
                            List<String> items = Arrays.asList(line.split("\t"));
                            for(int i = 0; i < Integer.parseInt(items.get(0)); i++){
                                Job newJob = new Job(i+1);
                                context.addJobs(newJob);
                            }
                            for(int i = 0; i < Integer.parseInt(items.get(1)); i++){
                                Machine newMachine = new Machine(i+1);
                                context.addMachines(newMachine);
                            }
                        }
                        else {
                            int itemIndex = 0;
                            List<String> items = Arrays.asList(line.split("\t"));

                            int nbOp = Integer.parseInt(items.get(itemIndex));

                            for(int i = 0; i < nbOp; i++){
                                itemIndex++;
                                Operation op = new Operation(i+1, countLine);

                                int nbMachines = Integer.parseInt(items.get(itemIndex));
                                for(int j = 0; j < nbMachines; j++){
                                    itemIndex++;
                                    int idMachine = Integer.parseInt(items.get(itemIndex));
                                    itemIndex++;
                                    int duration = Integer.parseInt(items.get(itemIndex));
                                    Machine machine = context.getMachineById(idMachine);
                                    op.addMachines(duration,machine);
                                }
                                context.getJobById(countLine).addOperations(op);
                            }
                        }
                        countLine++;
                    }
                }

                // Always close files.
                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '" +
                                fileName + "'");
            }
            catch(IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + fileName + "'");
            }
            System.out.println("Context initialized.");
    }

}
