package server.manager;

public interface Constants {


    String DB_NAME = "server_manager";
    String DB_URL = "jdbc:h2:./" + DB_NAME + ";INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'";


    String ADD_COMMAND = "add";
    String LOAD_COMMAND = "load";
    String COUNT_COMMAND = "count";
    String DELETE_COMMAND = "delete";
    String EDIT_COMMAND = "edit";
    String LIST_COMMAND = "list";

    String PAGE_SIZE_OPTION = "-s";
    String PAGE_SIZE_DEFAULT = "5";

}
