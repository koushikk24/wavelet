import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Koushik K.
 * @since 01/19/2023
 */
public class SearchEngine {

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
        Server.start(port, new SearchHandler());
    }
}

/**
 * @author Koushik K.
 * @since 01/19/2023
 */
class SearchHandler implements URLHandler {

    // list of strings
    Set<String> strings = new HashSet<>();

    /**
     * Handles a URL request
     * @param url The requested URL
     * @return The response
     */
    @Override
    public String handleRequest(URI url) {

        // add to list URL
        if (url.getPath().equalsIgnoreCase("/add")) {

            // String to add to list
            String add = url.getQuery().substring(2);

            // check if string exists
            if (!add.isEmpty()) {

                // add to list
                strings.add(add);
            }
            
            // no arg
            else {

                // notify user the correct args
                return "Please enter an argument!";
            }

            // notify user that the item was added to the list
            return "\"" + add + "\"" + " was added to the list!";
        }

        // query
        else if (url.getPath().equalsIgnoreCase("/search")) {

            // The query
            String query = url.getQuery().substring(2);

            // check if query exists
            if (!query.isEmpty()) {

                // search through the list and find matches
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : strings) {

                    // match
                    if (s.contains(query)) {

                        // add to matches
                        // build
                        stringBuilder.append(s)
                                .append(", ");
                    }
                }

                // create string output
                // remove last comma
                if (stringBuilder.length() > 2) {
                    return stringBuilder.substring(0, stringBuilder.length() - 2);
                } 
                
                else {
                    return "No result found!";
                }
            }

            // no arg
            else {

                // notify user the correct args
                return "Please enter an argument!";
            }
        }

        // invalid path
        else {
            System.out.println("Path: " + url.getPath() + ", Query: " + url.getQuery());
            return "404 Not Found!";
        }
    }
}
