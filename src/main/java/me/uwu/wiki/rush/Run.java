package me.uwu.wiki.rush;

import lombok.Data;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public @Data class Run {
    private final WikiPage start, end;
    private List<Steps> steps = new ArrayList<>();

    public void addStep(WikiPage page, long baseMillis) {
        steps.add(new Steps(page, System.currentTimeMillis() - baseMillis));
    }

    public WikiPage getLatestPage() {
        return steps.get(steps.size() - 1).getPage();
    }

    public String encodeBase64() {
        return Base64.getEncoder().encodeToString((start.encode() + "\t\t" + end.encode()).getBytes());
    }

    public static Run decodeBase64(String base64) {
        String[] split = new String(Base64.getDecoder().decode(base64)).split("\t\t");
        return new Run(WikiPage.decode(split[0]), WikiPage.decode(split[1]));
    }

    public static @Data class Steps {
        private final WikiPage page;
        private final long time;
    }
}
