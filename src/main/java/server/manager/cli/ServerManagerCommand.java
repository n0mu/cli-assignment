package server.manager.cli;


import static picocli.CommandLine.Command;
import static picocli.CommandLine.HelpCommand;
import static server.manager.Constants.DB_NAME;

@Command(name = "", mixinStandardHelpOptions = true,
        description = "Manages Server Registry. Use '" + DB_NAME + "' H2 database to store state.",
        commandListHeading = "%nCommands:%n%nThe available commands are:%n",
        subcommands = {
                HelpCommand.class,
                AddServer.class,
                LoadServer.class,
                EditServer.class,
                DeleteServer.class,
                ListServers.class,
                CountServers.class
        }
)
/**
 * Entry point command. This is the main command, all the other options are subcommands of this.
 */
public class ServerManagerCommand implements Runnable {

    @Override
    public void run() {

    }
}
