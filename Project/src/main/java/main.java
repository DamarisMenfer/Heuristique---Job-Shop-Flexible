import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {


    public static void main(String [] args) {

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
                    countLine++;
                    if(countLine == 1){
                        List<String> items = Arrays.asList(line.split("\t"));
                        System.out.println(items);
                        //for(int i = 0; i < items.get(0); i++){

                        //}

                    }
                    else {

                    }
                }
                System.out.println(line);
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
    }
}
