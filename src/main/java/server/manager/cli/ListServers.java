package server.manager.cli;

import picocli.CommandLine;
import server.manager.db.Server;
import server.manager.db.ServerRepository;

import java.util.List;

import static server.manager.Constants.*;

@CommandLine.Command(name = LIST_COMMAND,
        sortOptions = false,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        optionListHeading = "%n@|bold,underline Options|@:%n",
        header = "List servers",
        description = "List all the servers present in the repository")
public class ListServers implements Runnable {

    @CommandLine.Option(names = PAGE_SIZE_OPTION, description = "Servers per page (default: '${DEFAULT-VALUE}')",
            defaultValue = PAGE_SIZE_DEFAULT)
    private Integer pageSize;


    @Override
    public void run() {
        List<Server> servers = new ServerRepository().findAll();
        printServers(servers);
    }

    private void printServers(List<Server> servers) {
        int lastElementInPage = pageSize - 1;
        for (int i = 0; i < servers.size(); i++) {
            System.out.println(servers.get(i));
            if (i == lastElementInPage) {
                System.console().readLine();
                lastElementInPage += pageSize;
            }
        }
    }
}
