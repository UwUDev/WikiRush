package me.uwu.wiki.rush;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args) throws IOException {
        Run run;
        System.out.println("1. Generate a new run");
        System.out.println("2. Load a run from seed");
        System.out.print("Choice: ");

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Allow search ? [N/y]");
            boolean allowSearch = scanner.next().equalsIgnoreCase("y");
            System.out.println("Allow portals ? [N/y]");
            boolean allowPortals = scanner.next().equalsIgnoreCase("y");
            System.out.println("Allow block ? [N/y]");
            boolean allowBack = scanner.next().equalsIgnoreCase("y");

            run = generateRandomRun(new Rules(allowSearch, allowPortals, allowBack));

            System.out.println("seed: " + run.encodeBase64());
        } else {
            System.out.print("seed: ");
            String base64 = scanner.next();
            run = Run.decodeBase64(base64);
            System.out.println("Imported seed");
        }
        String antiCheatScript = run.getRules().buildRulesScript();

        System.out.println("Press enter to start");
        System.in.read();


        System.out.println(run.getStart().getTitle() + " -> " + run.getEnd().getTitle());

        System.out.println("Aide :\n" + run.getEnd().getDescription());

        ChromeDriver driver = new ChromeDriver();
        driver.get(run.getStart().getUrl());

        long start = System.currentTimeMillis();
        run.addStep(run.getStart(), start);

        new Thread(() -> {
            String reason = null;
            while (true) {
                try {
                    Alert alert = driver.switchTo().alert();
                    reason = alert.getText();
                    if (reason.equals("reloaded")) {
                        driver.switchTo().alert().accept();
                        driver.navigate().refresh();
                        // reinject script
                        driver.executeScript(antiCheatScript);
                        continue;
                    }
                    break;
                } catch (NoAlertPresentException | UnhandledAlertException ignored) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.err.println("Cheat detected, game stopped for " + reason);
            driver.quit();
            System.exit(0);
        }).start();

        while (true) {
            try {
                if (!antiCheatScript.isEmpty()) {
                    driver.executeScript(antiCheatScript);
                }

                try {
                    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(15));
                    wait.until(d -> !d.getCurrentUrl().equals(run.getLatestPage().getUrl()));
                    System.out.println("Current page: " + driver.getCurrentUrl());
                } catch (Exception ignored) { // caused by antiCheatScript
                    continue;
                }

                WikiPage page = WikiPage.fromDriver(driver);
                if (page == null) {
                    page = new WikiPage(driver.getCurrentUrl(), driver.getTitle(), "Unknown");
                }
                run.addStep(page, start);
                if (driver.getCurrentUrl().split("#")[0].split("\\?")[0].replace("\n", "")
                        .equalsIgnoreCase(run.getEnd().getUrl().split("#")[0].split("\\?")[0].replace("\n", ""))) {
                    System.out.println("Found end page!");
                    break;
                }
            } catch (UnreachableBrowserException ignored) {
            }
        }

        driver.quit();

        run.getSteps().forEach(step -> System.out.println((step.time() / 1000f) + "s -> " + step.page().getTitle() + " (" + step.page().getUrl() + ")"));

        System.out.println("Run took " + (run.getSteps().get(run.getSteps().size() - 1).time() / 1000f) + " seconds");
    }

    private static Run generateRandomRun(Rules rules) {
        WikiPage[] pages = new WikiPage[2];

        while (pages[0] == null) {
            pages[0] = WikiPage.random();

            if (pages[0] == null) {
                System.err.println("Failed to get random page, retrying...");
            }
        }

        while (pages[1] == null) {
            WikiPage page = WikiPage.random();
            if (page == null) {
                System.err.println("Failed to get random page, retrying...");
                continue;
            }

            if (page.getLength() < 120_000) {
                System.err.println("Page size is too small, retrying...");
                continue;
            }

            int nubersInTitle = 0;
            for (char c : page.getTitle().toCharArray()) {
                if (Character.isDigit(c)) {
                    nubersInTitle++;
                }
            }

            if (nubersInTitle > 2) {
                System.err.println("Page title has too many numbers, retrying...");
                continue;
            }

            pages[1] = page;
        }

        return new Run(pages[0], pages[1], rules);
    }
}
