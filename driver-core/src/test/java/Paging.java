import java.util.Iterator;

import com.datastax.driver.core.*;

public class Paging {
    private static final int RESULTS_PER_PAGE = 100;

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .withQueryOptions(new QueryOptions().setFetchSize(2000))
                .build();

            Session session = cluster.connect();

Statement st = new SimpleStatement("your query");
st.setFetchSize(RESULTS_PER_PAGE);

String pagingStateString = getPagingStateStringFromURL();
if (pagingStateString != null) {
    PagingState pagingState = PagingState.fromString(pagingStateString);
    st.setPagingState(pagingState);
}

ResultSet rs = session.execute(st);
PagingState nextPagingState = rs.getExecutionInfo().getPagingState();

int remaining = rs.getAvailableWithoutFetching();
for (Row row : rs) {
    renderInResponse(row);
    if (--remaining == 0) {
        break;
    }
}

if (nextPagingState != null) {
    renderNextPageLink(nextPagingState.toString());
}

        } finally {
            if (cluster != null)
                cluster.close();
        }
    }

    private static void renderNextPageLink(String s) {

    }

    private static void renderInResponse(Row row) {

    }

    public static String getPagingStateStringFromURL() {
        return null;
    }
}
