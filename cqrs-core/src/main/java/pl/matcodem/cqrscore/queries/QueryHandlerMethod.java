package pl.matcodem.cqrscore.queries;

import pl.matcodem.cqrscore.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
