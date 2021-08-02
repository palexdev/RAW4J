package io.github.palexdev.raw4j.data.base;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.json.JsonPathExpression;

public abstract class AbstractThing implements Thing {

    @JsonPathExpression("data.id")
    @SerializedName("id")
    private String id;

    @JsonPathExpression("data.name")
    @SerializedName("name")
    private String name;

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
