package com.artgallery;

import org.junit.Test;

import static com.artgallery.ArtType.PAINTING;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class ArtTest {

    private Art art = Art.of("name", PAINTING, "artist", now(), 100);

    @Test
    public void whenDateAndPriceDifferentThenArtIsStillEqual() {
        Art art2 = Art.of("name", PAINTING, "artist", now().minusDays(1), 200);

        assertThat(art).isEqualTo(art2);
    }

    @Test
    public void whenNameDifferentThenArtIsNotEqual() {
        Art art2 = Art.of("name2", PAINTING, "artist", art.getCreated(), 100);

        assertThat(art).isNotEqualTo(art2);
    }

    @Test
    public void whenTypeDifferentThenArtIsNotEqual() {
        Art art2 = Art.of("name", ArtType.VASE, "artist", art.getCreated(), 100);

        assertThat(art).isNotEqualTo(art2);
    }

    @Test
    public void whenArtistDifferentThenArtIsNotEqual() {
        Art art2 = Art.of("name", PAINTING, "artist2", art.getCreated(), 100);

        assertThat(art).isNotEqualTo(art2);
    }
}