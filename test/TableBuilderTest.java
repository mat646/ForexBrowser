import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TableBuilderTest {

    @Test
    void buildGoldTest() throws InvalidArgumentsException, DataNotFoundException, IOException {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/cenyzlota/2017-01-01/2017-04-01/?format=json"};

        tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Gold");

        assertEquals(64, tableBuilder.serializable.get(0).length);
    }

    @Test
    void buildGoldExceptionTest() {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/cenyzlota/2018-01-01/2018-04-01/?format=json"};

        assertThrows(InvalidArgumentsException.class,
                () -> tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Gold"));
    }

    @Test
    void buildTableTest() throws InvalidArgumentsException, DataNotFoundException, IOException {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/USD/2017-11-06/?format=json"};

        tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Table");

        assertEquals(1, tableBuilder.serializable.get(0).length);
    }

    @Test
    void buildTableExceptionTest() {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/USD/2017-11-05/?format=json"};

        assertThrows(DataNotFoundException.class,
                () -> tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Table"));
    }

    @Test
    void buildTablesTest() throws InvalidArgumentsException, DataNotFoundException, IOException {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/exchangerates/tables/a/2017-11-06/2017-12-28/?format=json"};

        tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Tables");

        assertEquals(36, tableBuilder.serializable.get(0).length);
    }

    @Test
    void buildTablesExceptionTest() {
        TableBuilder tableBuilder = new TableBuilder();

        String[] mockURLs = new String[]{"http://api.nbp.pl/api/exchangerates/tables/a/2018-11-06/2017-12-28/?format=json"};

        assertThrows(InvalidArgumentsException.class,
                () -> tableBuilder.setURL(mockURLs).sendRequest().buildSerializable("Tables"));
    }

}
