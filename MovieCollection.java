import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> castList = new ArrayList<String>();
    private ArrayList<String> genreList = new ArrayList<String>();
    private ArrayList<Double> ratingsList = new ArrayList<Double>();
    private ArrayList<Integer> revenueList = new ArrayList<Integer>();


    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        initializeCast();
        intiializeGenres();
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
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

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResultsTitle(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
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
    }

    private void sortResultsTitle(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsRevenue(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            int tempRevenue = temp.getRevenue();

            int possibleIndex = j;
            while (possibleIndex > 0 && listToSort.get(possibleIndex - 1).getRevenue() < tempRevenue)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsRatings(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            double tempRating = temp.getUserRating();

            int possibleIndex = j;
            while (possibleIndex > 0 && listToSort.get(possibleIndex - 1).getUserRating() < tempRating)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
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

    private void searchCast()
    {
        for(int c = 0; c < castList.size(); c++)
        {
            System.out.println("(" + (c+1) + ") " + castList.get(c));
        }
        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String actorChoice = castList.get(choice - 1);
        ArrayList<Movie> actorsMovies = new ArrayList<Movie>();
        for(Movie m : movies)
        {
            if(m.getCast().indexOf(actorChoice) != -1)
            {
                actorsMovies.add(m);
            }
        }
        sortResultsTitle(actorsMovies);
        for(int d = 0; d < actorsMovies.size(); d++)
        {
            System.out.println("(" + (d + 1) + ") " + actorsMovies.get(d).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(actorsMovies.get(choice - 1));
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResultsTitle(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
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
    }

    private void listGenres()
    {
        for(int i = 0; i < genreList.size(); i++)
        {
            System.out.println("(" + (i + 1) + ") " + genreList.get(i));
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String genreChoice = genreList.get(choice - 1);
        ArrayList<Movie> moviesOfGenre = new ArrayList<Movie>();
        for(Movie m : movies)
        {
            if(m.getGenres().indexOf(genreChoice) != -1)
            {
                moviesOfGenre.add(m);
            }
        }
        sortResultsTitle(moviesOfGenre);
        for(int x = 0; x < moviesOfGenre.size(); x++)
        {
            System.out.println("(" + (x + 1) + ") " + moviesOfGenre.get(x));
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(moviesOfGenre.get(choice - 1));
        }

    public static void insertionSortWordList(ArrayList<String> words)
    {
        int count = 0;
        for (int j = 1; j < words.size(); j++)
        {
            String temp = words.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(words.get(possibleIndex - 1)) < 0)
            {
                words.set(possibleIndex, words.get(possibleIndex - 1));
                possibleIndex--;
                count++;
            }
            words.set(possibleIndex, temp);
        }
    }



    private void listHighestRated()
    {
        sortResultsRatings(movies);
        for(int i = 0; i < 50; i++)
        {
            System.out.println("(" + (i + 1) + ") Title: " + movies.get(i).getTitle() + " | Rating: " + movies.get(i).getUserRating());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(movies.get(choice - 1));
    }

    private void listHighestRevenue()
    {
        sortResultsRevenue(movies);
        for(int i = 0; i < 50; i++)
        {
            System.out.println("(" + (i + 1) + ") Title: " + movies.get(i).getTitle() + " | Revenue: " + movies.get(i).getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(movies.get(choice - 1));
    }

    private void importMovieList(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        } catch (IOException exception) {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
    private void initializeCast()
    {
        String cast;
        String castMember;
        int delim1;
        int delim2;
        int delimCount;
        for(Movie m : movies)
        {
            ratingsList.add(m.getUserRating());
            revenueList.add(m.getRevenue());
            cast = m.getCast();
            delimCount = 0;
            for(int a = 0; a < cast.length(); a++)
            {
                if(cast.substring(a, a+1).equals("|"))
                {
                    delimCount++;
                }
            }
            for(int b = 0; b < delimCount; b++)
            {
                delim1 = 0;
                delim2 = cast.indexOf("|");
                if(delim2 == -1)
                {
                    delim2 = cast.length() - 1;
                }
                castMember = cast.substring(0, delim2);
                cast = cast.substring(delim2 + 1);
                boolean exist = false;
                for(String s : castList)
                {
                    if(s.equals(castMember))
                    {
                        exist = true;
                    }
                }
                if(!exist)
                {
                    castList.add(castMember);
                }
            }
        }
        insertionSortWordList(castList);
    }
    private void intiializeGenres()
    {
        String genres;
        String genre;
        int delim1;
        int delim2;
        int delimCount;
        for(Movie m : movies)
        {
            genres = m.getGenres();
            delimCount = 0;
            for(int a = 0; a < genres.length(); a++)
            {
                if(genres.substring(a, a+1).equals("|"))
                {
                    delimCount++;
                }
            }
            for(int b = 0; b < delimCount; b++)
            {
                delim1 = 0;
                delim2 = genres.indexOf("|");
                if(delim2 == -1)
                {
                    delim2 = genres.length() - 1;
                }
                genre = genres.substring(0, delim2);
                genres = genres.substring(delim2 + 1);
                boolean exist = false;
                for(String s : genreList)
                {
                    if(s.equals(genre))
                    {
                        exist = true;
                    }
                }
                if(!exist)
                {
                    genreList.add(genre);
                }
            }
        }
        insertionSortWordList(genreList);
    }

}
