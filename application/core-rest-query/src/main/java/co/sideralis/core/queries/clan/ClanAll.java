package co.sideralis.core.queries.clan;

import co.com.sofka.business.annotation.QueryHandles;
import co.com.sofka.business.annotation.QueryPath;
import co.com.sofka.business.sync.ViewModelExecutor;
import co.sideralis.core.clan.queries.GetAll;
import co.sideralis.core.queries.clan.model.ClanViewModel;

import java.util.List;
import java.util.Map;

@QueryHandles
@QueryPath(name = "clan-all")
public class ClanAll extends ViewModelExecutor<List<ClanViewModel>> {

    @Override
    public List<ClanViewModel> apply(Map<String, String> args) {
        return getDataMapped("clans", ClanViewModel.class)
                .applyAsList(new GetAll());
    }
}
