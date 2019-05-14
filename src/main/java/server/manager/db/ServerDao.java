package server.manager.db;

import java.util.List;
import java.util.Optional;


public interface ServerDao {


    Optional<Server> findById(String id);

    Optional<Server> findByName(String name);

    Integer count();

    void save(Server server);

    void save(List<Server> servers);

    void edit(String name, String description);

    void delete(String id);

    void deleteAll();

    List<Server> findAll();

}
