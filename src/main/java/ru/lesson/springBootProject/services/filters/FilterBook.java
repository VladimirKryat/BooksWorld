package ru.lesson.springBootProject.services.filters;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.GenreName;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class FilterBook {
    private GenreName genreName;
    private SortedByLikes sortedByLikes = SortedByLikes.NONE;

    public String toStringURLParams() {
        StringBuilder resultGenre = null;
        StringBuilder resultSorted = null;

        if (genreName!=null){
            resultGenre=new StringBuilder("genreName="+genreName.name());
        }
        if (sortedByLikes!=SortedByLikes.NONE){
            resultSorted=new StringBuilder("sortedByLikes="+sortedByLikes.name());
        }
        if (resultGenre!=null&&resultSorted!=null){
            return resultGenre.append("&amp;").append(resultSorted).toString();
        }else{
            if (resultGenre!=null){
                return resultGenre.toString();
            }
            if (resultSorted!=null){
                return resultSorted.toString();
            }
        }
        return null;
    }

    public GenreName getGenreName() {
        return genreName;
    }

    public void setGenreName(GenreName genreName) {
        this.genreName = genreName;
    }

    public SortedByLikes getSortedByLikes() {
        return sortedByLikes;
    }

    public void setSortedByLikes(SortedByLikes sortedByLikes) {
        this.sortedByLikes = sortedByLikes;
    }

    public enum SortedByLikes{
        ASC,DESC,NONE
    };
}
