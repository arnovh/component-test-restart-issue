package nl.vanhaastert.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@ApplicationScoped
public class InMemoryItemRepo {

  private final static String NOT_FOUND_TMPL = "Item with name %s not found";
  private final static String ALREADY_EXISTS_TMPL =
      "Item with name %s already exists";

  private final ConcurrentHashMap<String, Item> store = new ConcurrentHashMap<>();


  public Item findByName(String name) {
    Item item = (name == null ? null : store.get(name));
    if (item == null) {
      final String msg = String.format(NOT_FOUND_TMPL, name);
      throw new IllegalArgumentException(msg);
    }
    return item;
  }

  public void save(final Item item) {
    Objects.requireNonNull(item);
    if (store.containsKey(item.getName())) {
      final String msg = String.format(ALREADY_EXISTS_TMPL, item.getName());
      throw new IllegalArgumentException(msg);
    }

    store.put(item.getName(), item);
  }

  public void remove(final Item item) {
    Objects.requireNonNull(item);
    final Item itemFound = store.remove(item.getName());
    if (itemFound == null) {
      final String msg = String.format(NOT_FOUND_TMPL, item.getName());
      throw new IllegalArgumentException(msg);
    }
  }
}

