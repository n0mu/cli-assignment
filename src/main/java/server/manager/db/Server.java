package server.manager.db;


import java.util.Objects;

public class Server {


    private String id;

    private String name;

    private String description;

    public Server() {
    }

    public Server(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return id.equals(server.id) &&
                name.equals(server.name) &&
                description.equals(server.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "[id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ']';
    }
}
