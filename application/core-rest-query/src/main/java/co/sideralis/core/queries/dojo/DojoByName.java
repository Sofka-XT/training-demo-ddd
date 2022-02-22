package co.sideralis.core.queries.dojo;

import co.com.sofka.business.annotation.QueryHandles;
import co.com.sofka.business.annotation.QueryPath;
import co.com.sofka.business.sync.ViewModelExecutor;
import co.sideralis.core.dojo.queries.GetDojoByName;
import co.sideralis.core.queries.dojo.model.DojoViewModel;

import java.util.Map;

@QueryHandles
@QueryPath(name = "dojo-by-name")
public class DojoByName extends ViewModelExecutor<DojoViewModel> {
    @Override
    public DojoViewModel apply(Map<String, String> params) {
        var query = new GetDojoByName();
        query.setName(params.get("name"));
        return getDataMapped("dojos", DojoViewModel.class)
                .applyAsElement(query);
    }
}
