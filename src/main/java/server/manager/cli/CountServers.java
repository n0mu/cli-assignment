package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.ServerRepository;

import static server.manager.Constants.COUNT_COMMAND;

@CommandLine.Command(name = COUNT_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        header = "Count servers",
        description = "Returns the number of registered servers")
public class CountServers implements Runnable {


    @Override
    public void run() {
        System.out.println(new ServerRepository().count());
    }
}
