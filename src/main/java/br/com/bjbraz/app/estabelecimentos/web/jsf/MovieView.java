package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
 
@ManagedBean
public class MovieView {
     
    private List<Movie> movieList;
    
    private Movie movie;
     
    public List<Movie> getMovieList() {
        return movieList;
    }
     
    @PostConstruct
    public void init() {
        movieList = new ArrayList<Movie>();
         
        movieList.add(new Movie("The Lord of the Rings: The Two Towers", "Peter Jackson", "Fantasy, Epic", 179));
        movieList.add(new Movie("The Amazing Spider-Man 2", "Marc Webb", "Action", 142));
        movieList.add(new Movie("Iron Man 3", "Shane Black", "Action", 109));
        movieList.add(new Movie("Thor: The Dark World", "Alan Taylor", "Action, Fantasy", 112));
        movieList.add(new Movie("Avatar", "James Cameron", "Science Fiction", 160));
        movieList.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", "Fantasy, Epic", 165));
        movieList.add(new Movie("Divergent", "Neil Burger", "Action", 140));
        movieList.add(new Movie("The Hobbit: The Desolation of Smaug", "Peter Jackson", "Fantasy", 161));
        movieList.add(new Movie("Rio 2", "Carlos Saldanha", "Comedy", 101));
        movieList.add(new Movie("Captain America: The Winter Soldier", "Joe Russo", "Action", 136));
        movieList.add(new Movie("Fast Five", "Justin Lin", "Action", 132));
        movieList.add(new Movie("The Lord of the Rings: The Return of the King", "Peter Jackson", "Fantasy, Epic", 200));
         
    }

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
   
}