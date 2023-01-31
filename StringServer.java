import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Koushik K.
 * @since 01/19/2023
 */
public class StringServer {

    /**
     * Main method
     * @param args The command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // no port number
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        // given port number
        int port = Integer.parseInt(args[0]);

        // start the server
        Server.start(port, new Handler());
    }
}

/**
 * @author Koushik K.
 * @since 01/19/2023
 */
class Handler implements URLHandler {

    // list of strings
    StringBuilder message = new StringBuilder();

    /**
     * Handles a URL request
     * @param url The requested URL
     * @return The response
     */
    @Override
    public String handleRequest(URI url) {

        // add to list URL
        if (url.getPath().equalsIgnoreCase("/add-message")) {

            // String to add to list
            String[] add = url.getQuery().split("=");

            // check if string exists
            if (add[0].equalsIgnoreCase("s") && add.length == 2) {

                // add to list
                // build
                message.append(add[1])
                        .append("\n");
            }

            // no arg
            else {

                // notify user the correct args
                return "Please enter the correct arguments!";
            }

            // full message
            return message.toString();
        }

        // invalid path
        else {
            System.out.println("Path: " + url.getPath() + ", Query: " + url.getQuery());
            return "404 Not Found!";
        }
    }
}
