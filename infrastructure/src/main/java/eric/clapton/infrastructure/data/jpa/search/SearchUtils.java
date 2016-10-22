package eric.clapton.infrastructure.data.jpa.search;

import org.apache.commons.lang3.StringUtils;

import eric.clapton.infrastructure.data.jpa.search.exception.InvlidSearchOperatorException;
import eric.clapton.infrastructure.data.jpa.search.exception.SearchException;
import eric.clapton.infrastructure.data.jpa.search.filter.Condition;

public class SearchUtils {
    private SearchUtils() {
    };

    public static SearchOperator getOperatorByKey(String key) {

        String[] searchs = StringUtils.split(key, Condition.separator);

        if (searchs.length == 0) {
            throw new SearchException("Condition key format must be : property or property_op");// test
        }

        String searchProperty = searchs[0];

        SearchOperator operator = null;
        if (searchs.length == 1) {
            operator = SearchOperator.custom;
        } else {
            try {
                operator = SearchOperator.valueOf(searchs[1]);
            } catch (IllegalArgumentException e) {
                throw new InvlidSearchOperatorException(searchProperty, searchs[1]);
            }
        }
        return operator;
    }
}
