package com.artgallery.impl;

import com.artgallery.Art;
import com.artgallery.Gallery;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A thread-safe {@link Gallery} implementation that
 * can be modified and queried from multiple threads.
 **/
public class ThreadSafeGallery implements Gallery {

    private final Set<Art> allArt = ConcurrentHashMap.newKeySet();

    @Override
    public void addArt(Art art) {
        allArt.add(art);
    }

    @Override
    public void deleteArt(Art art) {
        allArt.remove(art);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The returned Collection is unmodifiable.
     * Changing it will result in a {@link UnsupportedOperationException}
     * <p>
     * It is safe to call addArt/deleteArt while iterating over this collection.
     */
    @Override
    public Collection<Art> getAllArt() {
        return Collections.unmodifiableCollection(allArt);
    }

    @Override
    public Collection<String> getArtists() {
        return allArt.stream()
                .map(Art::getArtist)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Art> getArtByArtist(String artist) {
        return allArt.stream()
                .filter(art -> art.getArtist().equals(artist))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Art> getRecentArt() {
        LocalDate yearAgo = LocalDate.now().minusYears(1);

        return allArt.stream()
                .filter(art -> art.getCreated().isAfter(yearAgo))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Art> getArtByPrice(OptionalInt fromPrice, OptionalInt toPrice) {
        int lowerBound = fromPrice.orElse(0);
        int upperBound = toPrice.orElse(Integer.MAX_VALUE);

        return allArt.stream()
                .filter(art -> art.getPrice().isPresent())
                .filter(art -> art.getPrice().getAsInt() >= lowerBound)
                .filter(art -> art.getPrice().getAsInt() <= upperBound)
                .collect(Collectors.toList());
    }
}