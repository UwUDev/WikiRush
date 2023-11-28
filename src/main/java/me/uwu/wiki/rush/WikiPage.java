package me.uwu.wiki.rush;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public @Data class WikiPage {
    private final String url, title, description;
    private int length;

    public static WikiPage decode(String s) {
        String[] split = s.split("\t");
        return new WikiPage(split[0], split[1], split[2]);
    }

    private WikiPage setLength(int length) {
        this.length = length;
        return this;
    }

    public String encode() {
        return url + "\t" + title + "\t" + description;
    }

    public static WikiPage fromDriver(WebDriver driver) {
        try {
            String url = driver.getCurrentUrl();
            String title = driver.getTitle().split(" — Wikipédia")[0];
            StringBuilder description = new StringBuilder();
            for (int i = 1; i <= 2; i++) {
                WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/p[" + i + "]"));
                // find all elements inside
                List<WebElement> elements = element.findElements(By.xpath(".//*"));
                for (WebElement e : elements) {
                    description.append(e.getText()).append(" ");
                }
                description.append("\n");
            }
            return new WikiPage(url, title, description.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public static WikiPage fromRawPage(String htmlPage, String url) {
        try {
            String title = htmlPage.split("<title>")[1].split(" — Wikipédia</title>")[0];
            Document doc = Jsoup.parse(htmlPage);
            StringBuilder description = new StringBuilder();
            for (int i = 1; i <= 2; i++) {
                String xpath = "/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/p[" + i + "]";
                Elements elems = doc.selectXpath(xpath);
                for (Element e : elems) {
                    description.append(e.text()).append(" ");
                }
                description.append("\n");
            }
            return new WikiPage(url, title, description.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static WikiPage random() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Page_au_hasard")
                    .build();

            String pageContent = null;
            String url = null;
            Response response = client.newCall(request).execute();
            pageContent = response.body().string();
            url = response.request().url().toString();


            return WikiPage.fromRawPage(pageContent, url).setLength(pageContent.length());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
