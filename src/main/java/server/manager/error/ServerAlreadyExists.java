package server.manager.error;

import server.manager.db.Server;

import java.util.List;

public class ServerAlreadyExists extends RuntimeException {

    public Server server;
    public List<Server> servers;

    public ServerAlreadyExists(Server server, Throwable cause) {
        super(cause);
        this.server = server;
    }

    public ServerAlreadyExists(List<Server> servers, Throwable cause) {
        super(cause);
        this.servers = servers;
    }
}
