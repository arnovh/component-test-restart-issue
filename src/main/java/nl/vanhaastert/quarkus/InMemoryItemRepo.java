package nl.vanhaastert.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;


@ApplicationScoped
public class InMemoryRepo {

  private final AtomicLong ids = new AtomicLong(0);
  private final List<String> store = new CopyOnWriteArrayList<>();


  @Override
  public String save(Reservation reservation) {
    Objects.requireNonNull(reservation);
    if (reservation.getId() != null) {
      throw new IllegalArgumentException("Reservation with an id cannot be saved");
    }

    reservation.setId(ids.incrementAndGet());
    store.add(reservation);
    return reservation;
  }

  public static class Item {

    private long id;

    private String name;

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
