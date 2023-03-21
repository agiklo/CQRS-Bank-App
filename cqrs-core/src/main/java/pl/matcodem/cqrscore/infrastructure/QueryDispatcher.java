package pl.matcodem.cqrscore.infrastructure;

import pl.matcodem.cqrscore.domain.BaseEntity;
import pl.matcodem.cqrscore.queries.BaseQuery;
import pl.matcodem.cqrscore.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);
}
