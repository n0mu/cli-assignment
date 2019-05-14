package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.ServerRepository;

import static server.manager.Constants.DELETE_COMMAND;

@CommandLine.Command(name = DELETE_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        header = "Delete a server",
        description = "Removes the server from the repository. If the server is not present on the repository" +
                " does nothing.")
public class DeleteServer implements Runnable {

    @CommandLine.Parameters(index = "0", description = "The id of the server")
    private String id;


    @Override
    public void run() {
        new ServerRepository().delete(id);
        System.out.println(String.format("Server Deleted With id=%s", id));
    }
}
