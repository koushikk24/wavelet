import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

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
    ArrayList<String> strings = new ArrayList<>();

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
            String add = url.getPath().substring(7);

            // check if string exists
            if (!add.isEmpty()) {

                // add to list
                strings.add(add);
            }

            // notify user that the item was added to the list
            return "\"" + add + "\"" + " was added to the list!";
        }

        // query
        else if (url.getPath().equalsIgnoreCase("/search")) {

            // The query
            String query = url.getPath().substring(10);

            // search through the list and find matches
            ArrayList<String> matches = new ArrayList<>();
            for (String s : strings) {

                // match
                if (s.contains(query)) {

                    // add to matches
                    matches.add(s);
                }
            }

            // create string output
            StringBuilder stringBuilder = new StringBuilder();
            for (String m : matches) {

                // build
                stringBuilder.append(m)
                    .append(", ");
            }

            // remove last comma
            return stringBuilder.substring(stringBuilder.length() - 2, stringBuilder.length() - 1);
        }

        // invalid path
        else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
    }
}
