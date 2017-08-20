package com.artgallery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.OptionalInt;

/**
 * Represents information about a piece of artwork, such as a painting.
 * Art objects are immutable and all fields are guaranteed to be non-null.
 * Instantiating Art with any null field will result in a NullPointerException.
 * <p>
 * Two Art objects are considered equal if they have the same "name", "type" and "artist".
 **/
@Value
@EqualsAndHashCode(of = {"name", "type", "artist"})
public final class Art {

    /** Artwork name **/
    private final @NonNull String name;
    /** Type of artwork **/
    private final @NonNull ArtType type;
    /** Artist name **/
    private final @NonNull String artist;
    /** Date when this art was created **/
    private final @NonNull LocalDate created;
    /** Asking price in pence **/
    private final @NonNull OptionalInt price;

    /**
     * Creates an instance of Art without an asking price
     **/
    public static Art of(String name, ArtType type, String artist, LocalDate created) {
        return new Art(name, type, artist, created, OptionalInt.empty());
    }

    /**
     * Creates an instance of Art with the given asking price
     **/
    public static Art of(String name, ArtType type, String artist, LocalDate created, int price) {
        return new Art(name, type, artist, created, OptionalInt.of(price));
    }
}