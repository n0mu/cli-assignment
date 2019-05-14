package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.Server;
import server.manager.db.ServerRepository;
import server.manager.error.ServerAlreadyExists;

import static server.manager.Constants.ADD_COMMAND;

@CommandLine.Command(name = ADD_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        header = "Add a new server",
        description = "Stores the server in the repository. If a server exists with the same name does nothing.")
public class AddServer implements Runnable {

    @CommandLine.Parameters(index = "0", description = "The id of the server")
    private String id;

    @CommandLine.Parameters(index = "1", description = "The name of the server")
    private String name;

    @CommandLine.Parameters(index = "2", description = "The description of the server (default: '${DEFAULT-VALUE}')", defaultValue = "")
    private String description;


    @Override
    public void run() {
        try {
            new ServerRepository().save(new Server(id, name, description));
            System.out.println(String.format("Server Added With id=%s name=%s description=%s", id, name, description));

        } catch (ServerAlreadyExists ex) {
            System.out.println(String.format("Server Already Exists With id=% or name=%s",
                    ex.server.getId(), ex.server.getName()));
        }

    }
}
