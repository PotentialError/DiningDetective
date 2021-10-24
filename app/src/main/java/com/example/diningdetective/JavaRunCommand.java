package com.example.diningdetective;

import java.io.*;

public class JavaRunCommand {

    public static void main(String args[]) {

        String s = null;

        try {
            //String contains the path of the python file and the py command to activate python in cmd
            String[] cmd = {
                    "py",
                    "\\dining_predictor.py"
            };
            // runs the python file specified above using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(cmd);
            //BufferedReader object to hold the input
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            //BufferedReader object to hold any errors
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // prints the output of the command that are contained in the stdInput object
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // prints the errors of the command that are contained in the stdInput object
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.exit(0);
        } //catch any errors and exits
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}