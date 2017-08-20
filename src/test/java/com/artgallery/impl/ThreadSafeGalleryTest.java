package com.artgallery.impl;

import com.artgallery.Art;
import com.artgallery.Gallery;
import org.junit.Test;

import java.util.Iterator;
import java.util.OptionalInt;

import static com.artgallery.ArtType.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.MAX_VALUE;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreadSafeGalleryTest {

    private static Art art1 = Art.of("name1", PAINTING, "artist1", now(), 100);
    private static Art art2 = Art.of("name2", VASE, "Artist2", now().minusYears(1).plusDays(1), 200);
    private static Art art3 = Art.of("name3", TAPESTRY, "Artist2", now().minusYears(1), 300);

    private Gallery gallery = new ThreadSafeGallery();

    @Test
    public void whenArtAddedThenGalleryContainsArt() {
        gallery.addArt(art1);
        gallery.addArt(art2);

        assertThat(gallery.getAllArt()).containsOnly(art1, art2);
    }

    @Test
    public void whenArtAddedMultipleTimesThenGalleryContainsSingleArt() {
        gallery.addArt(art1);
        gallery.addArt(art1);

        assertThat(gallery.getAllArt()).containsExactly(art1);
    }

    @Test
    public void whenArtDeletedThenGalleryNoLongerContainsArt() {
        gallery.addArt(art1);
        gallery.addArt(art2);

        gallery.deleteArt(art1);

        assertThat(gallery.getAllArt()).containsOnly(art2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenAddingToAllArtThenCannotBeAdded() {
        gallery.addArt(art1);

        gallery.getAllArt().add(art2);
    }

    @Test
    public void whenGettingArtistsThenGalleryReturnsAllArtistsInAlphabeticalOrder() {
        gallery.addArt(art2);
        gallery.addArt(art1);
        gallery.addArt(art3);

        assertThat(gallery.getArtists()).containsExactly("artist1", "Artist2");
    }

    @Test
    public void whenGettingArtByArtistThenGalleryReturnsArtBySpecifiedArtist() {
        gallery.addArt(art2);
        gallery.addArt(art3);
        gallery.addArt(art1);

        assertThat(gallery.getArtByArtist("artist1")).containsOnly(art1);
        assertThat(gallery.getArtByArtist("Artist2")).containsOnly(art2, art3);
    }

    @Test
    public void whenGettingRecentArtThenGalleryReturnsArtFromThePastYear() {
        addAllArt();

        assertThat(gallery.getRecentArt()).containsOnly(art1, art2);
    }

    @Test
    public void whenGettingArtByPriceThenGalleryReturnsAllArtInGivenRange() {
        addAllArt();
        gallery.addArt(Art.of("name4", VASE, "artist4", now(), 400));

        assertThat(gallery.getArtByPrice(OptionalInt.of(101), OptionalInt.of(399))).containsOnly(art2, art3);
    }

    @Test
    public void whenArtHasNoPriceThenCannotBeRetrievedByPrice() {
        addAllArt();
        gallery.addArt(Art.of("name4", SCULPTURE, "artist4", now()));

        assertThat(gallery.getArtByPrice(OptionalInt.of(0), OptionalInt.of(MAX_VALUE))).containsOnly(art1, art2, art3);
    }

    @Test
    public void whenGettingArtByLowPriceOnlyThenReturnsAllArtAbovePrice() {
        addAllArt();

        assertThat(gallery.getArtByPrice(OptionalInt.of(101), OptionalInt.empty())).containsOnly(art2, art3);
    }

    @Test
    public void whenGettingArtByHighPriceOnlyThenReturnsAllArtBelowPrice() {
        addAllArt();

        assertThat(gallery.getArtByPrice(OptionalInt.empty(), OptionalInt.of(299))).containsOnly(art1, art2);
    }

    @Test
    public void whenAddingArtToGalleryTheAllArtCanBeIteratedOver() {
        gallery.addArt(art1);
        gallery.addArt(art2);

        Iterator<Art> allArtIterator = gallery.getAllArt().iterator();
        gallery.addArt(art3);

        assertThat(newArrayList(allArtIterator)).contains(art1, art2);
    }

    @Test
    public void whenRemovingArtFromGalleryTheAllArtCanBeIteratedOver() {
        gallery.addArt(art1);
        gallery.addArt(art2);

        Iterator<Art> allArtIterator = gallery.getAllArt().iterator();
        gallery.deleteArt(art2);

        assertThat(newArrayList(allArtIterator)).contains(art1);
    }

    private void addAllArt() {
        gallery.addArt(art1);
        gallery.addArt(art2);
        gallery.addArt(art3);
    }
}