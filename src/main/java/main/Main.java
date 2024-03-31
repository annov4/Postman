package main;

import Service.AccountService;
import Servlet.SignInServlet;
import Servlet.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/");

        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        server.setHandler(handlers);

        server.start();
        System.out.println("Server started!");
        server.join();
    }
}