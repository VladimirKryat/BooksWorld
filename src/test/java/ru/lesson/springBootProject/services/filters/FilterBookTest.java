package ru.lesson.springBootProject.services.filters;

import org.junit.Assert;
import org.junit.Test;
import ru.lesson.springBootProject.models.GenreName;

public class FilterBookTest {

    @Test
    public void testToStringURLParams() {
        FilterBook filterBook = new FilterBook();
        Assert.assertNull(filterBook.toStringURLParams());
        filterBook.setGenreName(GenreName.AUTOBIOGRAPHY);
        Assert.assertTrue("genre=AUTOBIOGRAPHY".equals(filterBook.toStringURLParams()));
        filterBook.setSortedByLikes(FilterBook.SortedByLikes.ASC);
        Assert.assertTrue("genre=AUTOBIOGRAPHY&amp;sortedByLikes=ASC".equals(filterBook.toStringURLParams()));
        filterBook.setGenreName(null);
        Assert.assertTrue("sortedByLikes=ASC".equals(filterBook.toStringURLParams()));
    }
}