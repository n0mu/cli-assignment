package server.manager.db

import server.manager.error.ServerAlreadyExists
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title


@Title("Server Repository Test")
class ServerRepositoryTest extends Specification {

    final String connectionUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'"

    @Subject(ServerRepository)
            repository = new ServerRepository(connectionUrl)

    def setup() {
        repository.deleteAll()
    }


    def "Save server into repository"() {
        given:
        String serverId = "1save"
        Server server = new Server(serverId, "tomcat1", "backend")

        when:
        repository.save(server)
        def result = repository.findById(serverId)

        then:
        result
        result.get() == server
    }

    def "Save same server 2 times into repository produces error"() {
        given:
        String serverId = "1save"
        Server server = new Server(serverId, "tomcat1", "backend")

        when:
        repository.save(server)
        repository.save(server)
        repository.findById(serverId)

        then:
        def exception = thrown(ServerAlreadyExists)
        exception.server.id == serverId
    }


    def "Save multiple servers into repository"() {
        given:
        List<Server> servers = Arrays.asList(
                new Server("server1", "tomcat1", "backend"),
                new Server("server2", "tomcat2", "frontend"))

        when:
        repository.save(servers)

        then:
        repository.count() == 2
    }

    def "Save duplicated multiple servers into repository produces error"() {
        given:
        List<Server> servers = Arrays.asList(
                new Server("server1", "tomcat1", "backend"),
                new Server("server2", "tomcat2", "frontend"),
                new Server("server1", "tomcat1", "backend"))

        when:
        repository.save(servers)

        then:
        def exception = thrown(ServerAlreadyExists)
        exception.servers.size() == 3
    }

    def "Find server with id in the repository"() {
        given:
        String serverId = "1found"
        Server expected = new Server(serverId, "tomcat1", "backend")
        repository.save(expected)

        when:
        Optional<Server> found = repository.findById(serverId)

        then:
        found
        found.get() == expected
    }

    def "Delete server with id from the repository"() {
        given:
        String serverId = "1deleted"
        Server expected = new Server(serverId, "tomcat1", "backend")
        repository.save(expected)

        when:
        repository.delete(serverId)

        then:
        !repository.findById(serverId).isPresent()
    }

    def "Delete server with a non saved id does not produce error"() {

        when:
        repository.delete("empty")

        then:
        !repository.findById("empty").isPresent()
    }

    def "Count servers in repository"() {
        given:
        repository.save(new Server("a", "a", "a"))
        repository.save(new Server("b", "b", "b"))
        repository.save(new Server("c", "c", "c"))
        repository.save(new Server("d", "d", "d"))
        repository.save(new Server("e", "e", "e"))

        when:
        def result = repository.count()

        then:
        result == 5
    }

    def "Edit server works"() {
        given:
        String id = "one"
        String name = "tomcat"
        repository.save(new Server(id, name, "backend"))

        when:
        String newDescription = "frontend"
        repository.edit(name, newDescription)

        then:
        repository.findById(id).get().description == newDescription
    }

    def "Edit non existent server does not fail"() {

        when:
        repository.edit("@@@@", "frontend")

        then:
        true
    }


}
