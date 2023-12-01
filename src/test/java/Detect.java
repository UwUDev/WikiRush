import me.uwu.wiki.rush.browser.Browser;

public class Detect {
    public static void main(String[] args) {
        Browser.detectBrowsers().forEach(System.out::println);
    }
}
