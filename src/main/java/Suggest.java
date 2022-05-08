import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Suggest {
    private final String keyWord;
    private final String title;
    private final String url;

    public String toString() {
        return String.format("k = %s\tt = %s\tu = %s", keyWord, title, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suggest suggest = (Suggest) o;
        return keyWord.equals(suggest.keyWord) && title.equals(suggest.title) && url.equals(suggest.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWord, title, url);
    }
}
