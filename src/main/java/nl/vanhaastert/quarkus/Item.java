package nl.vanhaastert.quarkus;

import java.util.Objects;

public class Item {

  private final String name;

  private final String description;


  private Item(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.description = Objects.requireNonNull(builder.description);
  }

  public static Builder builder() {
    return new Builder();
  }


  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }


  public static final class Builder {
    private String name;
    private String description;

    private Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Item build() {
      return new Item(this);
    }
  }
}
