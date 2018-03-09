import java.util.Scanner;

public class Main {
    Scanner scanner;
    Graph graph;
    SearchGraph searchGraph;


    public static void main(String[] args) {
        Main main = new Main(args);
    }

    public Main(String[] args){
        graph = new Graph(args);
        scanner = new Scanner(System.in);



        String[] lists;

        boolean debug = false;

        if(debug) {
        String maleActors = "/Users/juan/Downloads/mini.list";
        lists = new String[1];
        lists[0] = maleActors;
        }
        else {
            String maleActors = "/Users/juan/Downloads/actors.list";
            String femaleActors = "/Users/juan/Downloads/actresses.list";
            lists = new String[2];
            lists[0] = maleActors;
            lists[1] = femaleActors;
        }

        graph = new Graph(lists);
        searchGraph = new SearchGraph(graph);
        String firstAct = "Bacon, Kevin (I)";


        while (true) {
            System.out.println("------------------------------------------------");
            System.out.println("Type \"1\" to change Bacon-person");
            System.out.println("Search an actor or title, should be in format \"Surname, Name\" || \"Title : year\":");
            String input = scanner.nextLine();

            if(input != null && !input.isEmpty() && input.equals("1")){
                System.out.println("Change Bacon to:");
                firstAct = scanner.nextLine();
            }
            else {
                if (input != null && !input.isEmpty()) {
                    long time = System.currentTimeMillis();
                    String answer = searchGraph.searchActor(graph.getByActor(firstAct), graph.getByActor(input));
                    System.out.println(answer);
                    System.out.println("Search took: " + (System.currentTimeMillis() - time) + "ms");

                }
            }
        }
    }
}

