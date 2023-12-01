package me.uwu.wiki.rush.browser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public enum Browser {
    CHROME(ChromeDriver.class, false, false),
    FIREFOX(FirefoxDriver.class, false, false),
    IE(InternetExplorerDriver.class, true, false),
    EDGE(EdgeDriver.class, true, false),
    SAFARI(SafariDriver.class, false, true);


    private final Class<? extends WebDriver> driver;
    private final boolean windowsOnly;
    private final boolean macOnly;

    public static List<Browser> detectBrowsers() {
        List<Browser> browsers = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(values().length);

        boolean isOnWindows = isOnWindows();
        boolean isOnMac = isOnMac();

        for (Browser value : values()) {
            executorService.submit(() -> {
                try {
                    if (value.isWindowsOnly() && !isOnWindows) {
                        System.err.println("Browser " + value.name() + " is only supported on Windows.");
                        return;
                    }

                    if (value.isMacOnly() && !isOnMac) {
                        System.err.println("Browser " + value.name() + " is only supported on Mac.");
                        return;
                    }

                    WebDriver driver = value.getDriver().getConstructor().newInstance();
                    driver.quit();
                    browsers.add(value);
                    System.out.println("Browser " + value.name() + " has been detected.");
                } catch (Exception e) {
                    System.out.println("Browser " + value.name() + " is not present or supported on your device.");
                }
            });
        }

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Le pool n'a pas terminé !");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Done detecting browsers.");
        browsers.forEach(System.out::println);

        return browsers;
    }

    private static boolean isOnWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static boolean isOnMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    @SneakyThrows
    public static void savePrefBrowser(Browser browser) {
        FileWriter writer = new FileWriter("browser");
        writer.write(browser.name());
        writer.close();
    }

    public static Browser getPrefBrowser() {
        try {
            return Browser.valueOf(new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("browser"))));
        } catch (Exception e) {
            return null;
        }
    }

}
