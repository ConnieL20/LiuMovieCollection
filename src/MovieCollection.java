import java.io.BufferedReader;
import java.util.Collections;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)
        String title = movieFromCSV[0];
        String cast =  movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);


        // create a Movie object with the row data:
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(nextMovie);


      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }


  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search term: ");
    String keySearch = scanner.nextLine();

    keySearch = keySearch.toLowerCase();

    ArrayList<Movie> results = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++){
      String keyWord = movies.get(i).getKeywords();
      keyWord = keyWord.toLowerCase();

      if (keyWord.indexOf(keySearch) != -1){
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() {
    //Scanner print
    System.out.print("Enter a person to search for (first or last name): ");
    String personSearch = scanner.nextLine();
    personSearch = personSearch.toLowerCase();
    ArrayList<String> castList2 = new ArrayList<String>();
    ArrayList<String> castList3 = new ArrayList<String>();
    ArrayList<Movie> listOfMovies = new ArrayList<>();



    //loop through the movies list cast and add them into a string to parse into an array
    for (Movie movie : movies){
      String cast = movie.getCast();
      String[] castList = cast.split("\\|");


      for (String a : castList){
        if (a.toLowerCase().contains(personSearch)){
          castList2.add(a);
        }
      }
    }

    //gets rid of duplicates
    for (String b : castList2) {
      if (!castList3.contains(b)) {
        castList3.add(b);
      }
    }

    //sorts the list
    Collections.sort(castList3);
    for (int i = 0; i < castList3.size(); i++){
      int index = i + 1;
      System.out.println(index + ". " + castList3.get(i));
    }

    System.out.println("Which would you like to see all movies for? ");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedActor = castList3.get(choice - 1);

    for (int i = 0 ; i < movies.size(); i++){
      if (movies.get(i).getCast().contains(selectedActor)){
        listOfMovies.add(movies.get(i));
      }
    }


    if (listOfMovies.size() > 0) {
      sortResults(listOfMovies);

      // now, display them all to the user
      for (int i = 0; i < listOfMovies.size(); i++) {
        String title = listOfMovies.get(i).getTitle();

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice2 = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = listOfMovies.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }


  }

  private void listGenres() {
    ArrayList<String> genreList2 = new ArrayList<>();
    ArrayList<Movie> genreMovieList = new ArrayList<>();

    for (Movie movie : movies) {
      String genre = movie.getGenres();
      String[] genreList = genre.split("\\|");

      for (String a : genreList){
        if (!genreList2.contains(a)){
          genreList2.add(a);
        }
      }

    }

    Collections.sort(genreList2);
    int i = 1;
    for (String genre : genreList2){
      System.out.println(i + ". " + genre);
      i++;
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedGenre = genreList2.get(choice - 1);

    for (int j = 0; j < movies.size(); j++){
      if (movies.get(j).getGenres().contains(selectedGenre)){
        genreMovieList.add(movies.get(j));
      }
    }

    System.out.println(genreMovieList);


    if (genreMovieList.size() > 0) {
      sortResults(genreMovieList);

      for (int k = 0; k < genreMovieList.size(); k++) {
        String title = genreMovieList.get(k).getTitle();

        int choiceNum = k + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice2 = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = genreMovieList.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }


  }


  private void listHighestRated() {
    // Sort movies by userRating field
    movies.sort(new Comparator<Movie>() {
      @Override
      public int compare(Movie o1, Movie o2) {
        return Double.compare(o2.getUserRating(), o1.getUserRating());
      }
    });

    List<Movie> top50List = new ArrayList<>();
    for (int i = 0; i< 50;i++) {
      top50List.add(movies.get(i));
    }

    if (top50List.size() > 0) {
      for (int i = 0; i < 50; i++) {
        Movie currentMovie = top50List.get(i);

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + currentMovie.getTitle() + ": " + currentMovie.getUserRating());
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = top50List.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }


  }

  private void listHighestRevenue() {
// Sort movies by userRating field
    movies.sort(new Comparator<Movie>() {
      @Override
      public int compare(Movie o1, Movie o2) {
        return Double.compare(o2.getRevenue(), o1.getRevenue());
      }
    });

    List<Movie> top50Revenue = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      top50Revenue.add(movies.get(i));
    }

    if (top50Revenue.size() > 0) {
      for (int i = 0; i < 50; i++) {
        Movie currentMovie = top50Revenue.get(i);

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + currentMovie.getTitle() + ": " + currentMovie.getRevenue());
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = top50Revenue.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }



  }
}
