package nl.vanhaastert.quarkus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusComponentTest
@org.junit.jupiter.api.TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class InMemoryItemRepoTest {

  @Inject
  InMemoryItemRepo repo;


  @Test
  void saveShouldPersist() {
    // INITIALIZE : Create a big item and check it does not yet exist in the repo
    final String bigItemName = "big";
    final String bigItemDescription = "big item";
    final Item bigItem = Item.builder().name(bigItemName).description(bigItemDescription).build();
    assertThrows(IllegalArgumentException.class, () -> repo.findByName(bigItemName));

    // EXECUTE : Persist the big item in the repo
    repo.save(bigItem);

    // VALIDATE : find big item back in repo
    final Item retrievedItem = repo.findByName(bigItemName);
    assertThat(retrievedItem, is(bigItem));
  }

  @Test
  void persistExistingNameShouldFail() {
    // INITIALIZE : Create 2 big items and persist one in the repo
    final String bigItemName = "big";
    final Item bigItem1 = Item.builder().name(bigItemName).description("First big item").build();
    final Item bigItem2 = Item.builder().name(bigItemName).description("Second big item").build();
    repo.save(bigItem1);

    // EXECUTE : Also attempt to persist the second big item in he repo
    Exception exception = assertThrows(IllegalArgumentException.class, () -> repo.save(bigItem2));

    // VALIDATE : Expect second attempt failed.
    assertThat(exception.getMessage(), is("Item with name big already exists"));
  }
}

