package com.artgallery;

import java.util.Collection;
import java.util.OptionalInt;

/**
 * A Gallery service for storing and querying {@link Art} objects
 **/
public interface Gallery {
    /**
     * Adds a piece of art to the gallery. Adding the same (or equal) Art second time has no effect.
     *
     * @param art Art to be added
     **/
    void addArt(Art art);

    /**
     * Removes a piece of art from the gallery.
     *
     * @param art to be removed.
     **/
    void deleteArt(Art art);

    /**
     * Returns all art currently in the gallery. The returned collection should not be modified.
     **/
    Collection<Art> getAllArt();

    /**
     * Returns the names of all of the artists with art currently in the gallery.
     * The names are sorted alphabetical order in case-insensitive manner.
     **/
    Collection<String> getArtists();

    /**
     * Returns all art by a specific artist.
     *
     * @param artist name of artist
     **/
    Collection<Art> getArtByArtist(String artist);

    /**
     * Returns all art with creation date in the past year.
     **/
    Collection<Art> getRecentArt();

    /**
     * Returns all art between an upper and lower price limit (inclusive).
     * Both limits are optional and art with no asking price is ignored.
     * If no limits are given then all art is returned.
     *
     * @param fromPrice lower price limit in pence
     * @param toPrice upper price limit in pence
     **/
    Collection<Art> getArtByPrice(OptionalInt fromPrice, OptionalInt toPrice);
}
