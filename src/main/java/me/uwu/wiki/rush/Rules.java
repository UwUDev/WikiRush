package me.uwu.wiki.rush;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;

public record Rules(boolean allowSearch, boolean allowPortals, boolean allowBack) {

    public String encode() {
        return allowSearch + "\t" + allowPortals + "\t" + allowBack;
    }

    @SneakyThrows
    public String buildRulesScript() {
        StringBuilder builder = new StringBuilder();

        {
            InputStream is = getClass().getResourceAsStream("/anticheat/security.js");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            builder.append(new String(buffer));
            is.close();
        }

        if (!allowSearch) {
            builder.append("\n");
            InputStream is = getClass().getResourceAsStream("/anticheat/antisearch.js");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            builder.append(new String(buffer));
            is.close();
        }
        if (!allowPortals) {
            builder.append("\n");
            InputStream is = getClass().getResourceAsStream("/anticheat/antiportal.js");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            builder.append(new String(buffer));
            is.close();
        }

        // todo: add anti back

        return builder.toString();
    }

    public static Rules decode(String encode) {
        String[] split = encode.split("\t");
        return new Rules(Boolean.parseBoolean(split[0]), Boolean.parseBoolean(split[1]), Boolean.parseBoolean(split[2]));
    }
}