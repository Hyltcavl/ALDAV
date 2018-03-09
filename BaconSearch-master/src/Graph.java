import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.HashBiMap; // Using Google Guava library
import gnu.trove.list.array.TIntArrayList; // Using Trove library


public class Graph {

    private long currentTime; // Used for loadingtime
    private int actorCount= 0, titleCount = 0;

    private ArrayList<TIntArrayList> connectionsByIndex;
    private HashBiMap<String, Integer> verticesAndIndex;


    public Graph(String[] filePaths){
        connectionsByIndex = new ArrayList();
        verticesAndIndex = HashBiMap.create();

        for(String file : filePaths){
            LoadDataIntoGraph(file);
        }
    }



    public TIntArrayList getConnectionsByIndex(int i){
        return connectionsByIndex.get(i);
    }

    public String getByIndex(int i){
        return verticesAndIndex.inverse().get(i);
    }

    public int getByActor(String s){
        return (verticesAndIndex.get(s) == null) ? -1 :verticesAndIndex.get(s);
    }



    private void addIndex(String title, TIntArrayList currentActorList, int currentActorIndex){
        if(verticesAndIndex.containsKey(title)){
            int currentMovieIndex = verticesAndIndex.get(title); //gets the id(titleCount) of the title since it exists
            TIntArrayList movieList = connectionsByIndex.get(currentMovieIndex); // Gets the existing MoveList since it already exists at edgesIndexArrayList(titleCount).
            currentActorList.add(currentMovieIndex);  //Adds movie to actor in question
            movieList.add(currentActorIndex); // Adds actor as participant in the already existing movie

        }else{
            TIntArrayList titleList = new TIntArrayList(); // Title doesn't exist. Creates a new array to add actors to.
            int currentTitleIndex = actorCount + (titleCount++); // Generates a new index since the title doesnt exist.
            connectionsByIndex.add(currentTitleIndex, titleList); // Add the arrayList for the movie to the new index (currentMovieIndex)
            verticesAndIndex.put(title, currentTitleIndex); // Add the title to the hashmap using the name as key and index(same as connectionsByIndex as value
        }
    }

    private void LoadDataIntoGraph(String filePath){
        System.out.println("Loading input...");
        long cTime = System.currentTimeMillis();
        String title;
        try {
            BaconReader reader = new BaconReader(filePath);
            BaconReader.Part part;
            StringBuilder stb = new StringBuilder();
            int currentActorIndex = 0;
            TIntArrayList currentActorList = null;

            while ((part = reader.getNextPart()) != null) {
                switch(part.type) {
                    case NAME:
                        if (!stb.toString().isEmpty()) {
                            title = stb.toString();
                            addIndex(title, currentActorList, currentActorIndex);
                            stb = new StringBuilder();
                        }

                        String currentActor = part.text;
                        currentActorIndex = (actorCount++) + titleCount;
                        currentActorList = new TIntArrayList();

                        connectionsByIndex.add(currentActorIndex, currentActorList);
                        verticesAndIndex.put(currentActor, currentActorIndex);
                        break;

                    case TITLE:
                        if(!(stb.toString().isEmpty())){
                            title = stb.toString();
                            addIndex(title, currentActorList, currentActorIndex);
                            stb = new StringBuilder(part.text);
                        }else
                            stb.append(part.text);
                        break;

                    case YEAR:
                        stb.append(" : ");
                        stb.append(part.text);
                        break;

                    case ID:
                        stb = new StringBuilder(); // If ID exits(TV-show e.g.), disregard that title and create a new StringBuilder for next title.
                        break;

                    case INFO:
                        break; //Ignored
                }
            }
            reader.close();
            addIndex(stb.toString(), currentActorList, currentActorIndex); // Adds the last title.

            printLoadTime((System.currentTimeMillis()-cTime));
            printDataInfo();
        } catch (FileNotFoundException e){
            System.err.println("File not found at: " + filePath);
        } catch (IOException e){
            System.err.println("IOException when reading file: " + filePath);
        }
    }

    private void printDataInfo() {
        System.out.println("Number of connections: " + verticesAndIndex.size());
        System.out.println("Actors: " + actorCount + "\nTitles: " + titleCount);
    }

    private void printLoadTime(long time){
        currentTime += time;
        System.out.println(currentTime/1000 + " seconds to graph input("+currentTime+"ms)");
    }
}