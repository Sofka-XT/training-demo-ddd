package co.sideralis.core.queries.dojo;

import co.com.sofka.business.annotation.QueryHandles;
import co.com.sofka.business.annotation.QueryPath;
import co.com.sofka.business.sync.ViewModelExecutor;
import co.sideralis.core.dojo.queries.GetAllDojoOpen;
import co.sideralis.core.queries.dojo.model.DojoViewModel;

import java.util.List;
import java.util.Map;

@QueryHandles
@QueryPath(name = "dojo-all")
public class DojoAllOpen extends ViewModelExecutor<List<DojoViewModel>> {
    @Override
    public List<DojoViewModel> apply(Map<String, String> args) {
        return getDataMapped("dojos", DojoViewModel.class)
                .applyAsList(new GetAllDojoOpen());
    }
}
