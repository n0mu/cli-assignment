package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.Server;
import server.manager.db.ServerRepository;
import server.manager.error.ServerAlreadyExists;
import server.manager.error.XMLParserError;
import server.manager.parser.XMLServerParser;

import java.io.File;
import java.util.List;

import static server.manager.Constants.LOAD_COMMAND;

@CommandLine.Command(name = LOAD_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        header = "Load a new server from xml file",
        description = "Read a server from an xml formatted file and stores it in the repository. " +
                "If a server exists with the same name or id does nothing.")
public class LoadServer implements Runnable {

    @CommandLine.Parameters(index = "0", description = "XML File containing the server to load")
    private File file;


    @Override
    public void run() {

        try {
            List<Server> servers = new XMLServerParser().parse(file);
            new ServerRepository().save(servers);
            System.out.println(String.format("%d Servers added to the repository", servers.size()));

        } catch (ServerAlreadyExists ex) {
            System.out.println("Some of the servers already exists in the repository: \n" + ex.servers);
        } catch (XMLParserError ex) {
            System.out.println("Unable to parse file. " + ex.getCause().getLocalizedMessage());
        }
    }

}
