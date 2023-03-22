package appium.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceHelper {

    /**
     * Executes bash script through terminal with handling exception
     * Does not guarantee complete receival of the result of the command execution, since there is no callback
     * Suitable for executing a script without returning a result
     * @param command bash command
     * @return result scripts
     */

    public static String executeBash(String command) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(command); //get a terminal instance and send a script
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        final String[] message = {""}; //array with 1 element to write strings from terminal

        new Thread(()->{//we start a new thread so that there is no message "Process is not responding" in case the command will be executed indefinitely
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));//получаем поток информации
            String line = null;
            while (true){
                try {
                    if ((line = input.readLine()) == null) {//read lines while they exist
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message[0] += line + "\n"; //write strings to the first element of the array
            }
            System.out.println(message[0]);//output to console for debugging
        }).start();//starting thread
        try {
            p.waitFor();//waiting thread execution
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return message[0];
    }
}
