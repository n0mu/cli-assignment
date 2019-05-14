package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.ServerRepository;

import static server.manager.Constants.EDIT_COMMAND;

@CommandLine.Command(name = EDIT_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        header = "Edit server",
        description = "Edits the server description. If the server does not exists does nothing.")
public class EditServer implements Runnable {

    @CommandLine.Parameters(index = "0", description = "The name of the server")
    private String name;

    @CommandLine.Parameters(index = "1", description = "The description of the server (default: '${DEFAULT-VALUE}')", defaultValue = "")
    private String description;


    @Override
    public void run() {
        ServerRepository sr = new ServerRepository();
        if (sr.findByName(name).isPresent()) {
            sr.edit(name, description);
            System.out.println(String.format("Server Edited With name=%s description=%s", name, description));
        } else {
            System.out.println(String.format("Unable to edit server. There's no server with name %s", name));
        }
    }
}
