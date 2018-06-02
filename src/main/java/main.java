import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String [] args) {

        final int numberOfNeighbours = 15;
        final int numberOfTrials = 4;
        int notABetterSolution = 0;
        int neighbourTime;

        HashMap<Integer, Context> neighbours;
        List timeKeys;
        Context neighbourContext;

        if (args.length != 1) {
            System.err.println("usage: java main path_to_test_file");
            System.exit(1);
        }

        Context contextSolution = new Context();
        initializeContext(contextSolution, args[0]);
        contextSolution.initialSolution();

        while (notABetterSolution < numberOfTrials){

            neighbours = new HashMap<Integer,Context>();
            timeKeys = new ArrayList();

            for (int i = 0; i < numberOfNeighbours;){
                neighbourContext = (Context) contextSolution.clone();
                if(neighbourContext.generateNeighbour()){
                    i++;
                    neighbourTime = neighbourContext.getTotalTime();
                    neighbours.put(neighbourTime, neighbourContext);
                    timeKeys.add(neighbourTime);
                }
            }
            Collections.sort(timeKeys);
            Context betterNeighbour = neighbours.get(timeKeys.get(0));
            if(betterNeighbour.getTotalTime() < contextSolution.getTotalTime()){
                contextSolution = (Context) betterNeighbour.clone();
                System.out.println("---------found new solution");
                contextSolution.printSolution();
                System.out.println("----------------------total time "+contextSolution.getTotalTime()+"\n");
                notABetterSolution = 0;
            }else {
                notABetterSolution++;
            }
        }

        System.out.println("\n*****************************************");
        System.out.println("************ FINAL SOLUTION *************");
        System.out.println("*****************************************\n");
        contextSolution.printSolution();
        contextSolution.printContext();
        System.out.println(contextSolution.getTotalTime());
    }

    private static void initializeContext(Context context, String path){

        // This will reference one line at a time
        String line;

        int countLine = 0;
        int itemIndex;


        try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                        new FileReader(path);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {
                    if(!line.equals("")){
                        line = line.trim();
                        if(countLine == 0){
                            List<String> items = Arrays.asList(line.split(" +"));
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
                            itemIndex = 0;
                            List<String> items = Arrays.asList(line.split(" +"));
                            System.out.println(items.toString());
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
                                path + "'");
            }
            catch(IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + path + "'");
            }
            System.out.println("Context initialized.");
    }

}
