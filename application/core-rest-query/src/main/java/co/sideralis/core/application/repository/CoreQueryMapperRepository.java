package co.sideralis.core.application.repository;

import co.com.sofka.business.repository.QueryMapperRepository;
import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;
import com.google.gson.Gson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoreQueryMapperRepository implements QueryMapperRepository {

    private final MongoTemplate mongoTemplate;

    public CoreQueryMapperRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public <T extends ViewModel> ApplyQuery<T> getDataMapped(String category, Class<T> classViewModel) {
        return new ApplyQuery<T>() {
            @Override
            public ViewModel applyAsElement(Query query) {
                var json = new Gson().toJson(query);
                BasicQuery basicQuery = new BasicQuery(json);
                return mongoTemplate.findOne(basicQuery, classViewModel, category);
            }

            @Override
            public List<ViewModel> applyAsList(Query query) {
                var json = new Gson().toJson(query);
                BasicQuery basicQuery = new BasicQuery(json);
                return (List<ViewModel>) mongoTemplate.find(basicQuery, classViewModel, category);
            }

        };
    }


}
