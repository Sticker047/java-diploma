import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Suggest {
    private final String keyWord;
    private final String title;
    private final String url;

    public String toString(){
        return "k = " + keyWord + "; t = " + title + "; u = " + url;
    }

}
