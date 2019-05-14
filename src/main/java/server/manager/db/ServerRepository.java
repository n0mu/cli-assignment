package server.manager.db;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import server.manager.error.ServerAlreadyExists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static server.manager.Constants.DB_URL;

public class ServerRepository implements ServerDao {


    private final Jdbi db;
    private final Handle handle;


    /**
     * Repository for servers. It's optimized for a H2 database.
     *
     * @param connectionUrl the connection url of the database used to store the servers
     */
    public ServerRepository(String connectionUrl) {
        db = Jdbi.create(connectionUrl);
        db.installPlugin(new SqlObjectPlugin());
        handle = db.open();
    }

    /**
     * Repository for servers. It's optimized for a H2 database.
     **/
    public ServerRepository() {
        this(DB_URL);
    }


    /**
     * Count the number of servers stored in the repository.
     *
     * @return
     */
    @Override
    public Integer count() {
        return handle.createQuery("select COUNT(*) from SERVERS")
                .mapTo(Integer.class)
                .one();
    }


    /**
     * Retrieve Optional<Server> that matches with {id} if not found returns Optional.empty()
     *
     * @param id of the server
     * @return
     */
    @Override
    public Optional<Server> findById(String id) {
        return handle.createQuery("select * from SERVERS where id= :id")
                .bind("id", id)
                .map(new ServerMapper())
                .findOne();
    }

    /**
     * Retrieve Optional<Server> that matches with {name} if not found returns Optional.empty()
     *
     * @param name of the server
     * @return
     */
    @Override
    public Optional<Server> findByName(String name) {
        return handle.createQuery("select * from SERVERS where name= :name")
                .bind("name", name)
                .map(new ServerMapper())
                .findOne();
    }


    /**
     * Saves server into repository. If the server exists throws ServerAlreadyExists exception
     *
     * @param server the server to be saved
     * @throws ServerAlreadyExists
     */
    @Override
    public void save(Server server) throws ServerAlreadyExists {
        try {
            handle.createUpdate("insert into SERVERS (id, name, description) values (?, ?, ?)")
                    .bind(0, server.getId())
                    .bind(1, server.getName())
                    .bind(2, server.getDescription())
                    .execute();
        } catch (Exception ex) {
            throw new ServerAlreadyExists(server, ex);
        }
    }


    /**
     * Save multiple servers into repository.
     * If the input contains duplicated servers or a server is already stored throws ServerAlreadyExists exception
     *
     * @param servers List of servers to be saved
     * @throws ServerAlreadyExists
     */
    public void save(List<Server> servers) throws ServerAlreadyExists {
        try {
            PreparedBatch batch = handle.prepareBatch(
                    "insert into SERVERS (id, name, description) values (?, ?, ?)");
            for (Server server : servers) {
                batch.bind(0, server.getId())
                        .bind(1, server.getName())
                        .bind(2, server.getDescription())
                        .add();
            }
            batch.execute();
        } catch (Exception ex) {
            throw new ServerAlreadyExists(servers, ex);
        }
    }


    /**
     * Set the {description} of an existent server with {name}
     *
     * @param name
     * @param description
     */
    public void edit(String name, String description) {
        handle.createUpdate("update SERVERS set description= :description where name= :name")
                .bind("name", name)
                .bind("description", description)
                .execute();
    }


    /**
     * Delete server from system with {id}. If the server is not present does nothing.
     *
     * @param id
     */
    public void delete(String id) {
        handle.createUpdate("delete SERVERS where id= ?")
                .bind(0, id)
                .execute();
    }

    /**
     * Delete all the servers stored in the repository.
     */
    public void deleteAll() throws ServerAlreadyExists {
        handle.createUpdate("delete SERVERS")
                .execute();
    }


    /**
     * Retrieves all the servers present int he repository.
     *
     * @return a List of Servers
     */
    public List<Server> findAll() {
        return handle.createQuery("select * from SERVERS")
                .map(new ServerMapper())
                .list();
    }


    private static class ServerMapper implements RowMapper<Server> {
        @Override
        public Server map(ResultSet rs, StatementContext ctx) throws SQLException {
            return new Server(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("description"));
        }
    }
}
