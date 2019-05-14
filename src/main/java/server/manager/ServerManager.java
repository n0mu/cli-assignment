package server.manager;

import picocli.CommandLine;
import server.manager.cli.ServerManagerCommand;


/**
 * Entry point of the application.
 */
public class ServerManager {

    public static void main(String[] args) {

        try {
            CommandLine.run(new ServerManagerCommand(), args);

        } catch (Exception ex) {
            System.out.println("GENERIC ERROR -- CONTACT SUPPORT:\n");
            System.out.println(ex);
        }
    }

}
