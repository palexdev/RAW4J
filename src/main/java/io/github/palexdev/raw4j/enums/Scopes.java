package io.github.palexdev.raw4j.enums;

import io.github.palexdev.raw4j.utils.StringUtils;

import java.util.List;

public enum Scopes {
    IDENTITY("identity"),
    EDIT("edit"),
    FLAIR("flair"),
    HISTORY("history"),
    MODCONFIG("modconfig"),
    MODFLAIR("modflair"),
    MODLOG("modlog"),
    MODPOSTS("modposts"),
    MODWIKI("modwiki"),
    MYSUBREDDITS("mysubreddits"),
    PRIVATEMESSAGES("privatemessages"),
    READ("read"),
    REPORT("report"),
    SAVE("save"),
    SUBMIT("submit"),
    SUBSCRIBE("subscribe"),
    VOTE("vote"),
    WIKIEDIT("wikiedit"),
    WIKIREAD("wikiread")
    ;

    private final String scope;

    Scopes(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return scope;
    }

    public static List<Scopes> all() {
        return List.of(values());
    }

    public static String from(List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        for (Scopes scope : scopes) {
            sb.append(scope.toString()).append(",");
        }
        return StringUtils.replaceLast(sb.toString(), ",", "");
    }
}
