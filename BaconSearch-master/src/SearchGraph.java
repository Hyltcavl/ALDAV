import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import gnu.trove.list.array.TIntArrayList;


public class SearchGraph {
    private Graph graph;

    public SearchGraph(Graph graph){
        this.graph = graph;
    }



    public String searchActor(int from, int end) {
        ArrayList result = doBreadthFirstSearch(from, end);
        if(result.isEmpty()){
            return "No connection to " + graph.getByIndex(from);
        }

        String size = result.size()/2 + "";
        return "The bacon-number is "+ size + ".\n\nConnections: "+ result.toString();
    }

    /**
     * Private method to find connection between two graph indexes using Breadth First Search algorithm.
     *
     * Adds inital graph index {@code from} to a queue.
     * Loops through the queue until empty. When element is removed from the queue, it assigns to variable {@code current}.
     *
     * If the graph index has connections, they will be added to the queue.
     *
     * As long as there are more connections added to the queue, and the {@code end} index is not found the method will keep adding connections to the queue.
     * The {@code visited} HashSet keeps track of checked connections to prevent circular reference.
     * When adding {@code current} connection to queue, the connection between current and its connections is added as a independent single key-value pairs in connectionList.
     * This until the {@code current} is equal to {@code end} index, or no more elements exists in queue.
     *
     * When current is equal to {@code end}, the method iterates through the connectionList, starting at {@code current} and adds every other connection to the
     * ArrayList {@code shortestConnection}. The loop ends when the {@code from} is reached,
     * which will result arrayList containing the {@code end} value, all connections between, and finally the {@code from} value.
     *
     * @param from is the value of initial graph index to searchGraph from.
     * @param end is the value of the graph index to find.
     * @return shortestConnection ArrayList which contains the searched index, all connections and the base index. Return empty arrayList if no connection is found.
     *
     */
    private ArrayList doBreadthFirstSearch(int from, int end) {
        LinkedList<Integer> queue = new LinkedList<>();

        HashMap<Integer, Integer> childParentConnection = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();
        TIntArrayList connectionList;
        ArrayList<String> shortestConnection = new ArrayList<>();

        queue.add(from);

        while (!queue.isEmpty()) {
            int current = queue.remove();

            if (current == end) {
                for (int i = current; i != from; i = childParentConnection.get(i)) {
                    shortestConnection.add(graph.getByIndex(i));
                }
                shortestConnection.add(graph.getByIndex(from));
                return shortestConnection;
            }
            connectionList = graph.getConnectionsByIndex(current); //Gets the connection-arraylist for the current actor/title from the big graph ArrayList.
            for (int i = 0; i < connectionList.size(); i++) {
                int connection = connectionList.get(i);
                if (!(visited.contains(connection))) {
                    queue.add(connection);
                    childParentConnection.put(connection, current);
                    visited.add(connection);
                }
            }
        }
        return shortestConnection;
    }
}
