import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.util.ArrayList;

public class ShortLister {

    public static ArrayList<Object> collectAll(ArrayList<Object> objects, Filter w) {
        ArrayList<Object> words = new ArrayList<Object>();

        for (Object x : objects) {
            if (w.accept(x)) {
                words.add(x);
            }
        }
        return words;
    }

    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        try
        {

            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);


            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                Scanner myReader = new Scanner(file);
                ArrayList<Object> wordss = new ArrayList<>();

                while(myReader.hasNext()) {
                    wordss.add(myReader.next());
                }
                myReader.close();

                wordss = collectAll(wordss, new ShortWordFilter());

                System.out.println("Words from the text file: ");
                for (Object var : wordss) {
                    System.out.println(var);
                }

            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}