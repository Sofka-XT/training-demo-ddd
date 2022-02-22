package co.sideralis.core.queries.clan;

import co.com.sofka.business.annotation.QueryHandles;
import co.com.sofka.business.annotation.QueryPath;
import co.com.sofka.business.sync.ViewModelExecutor;
import co.sideralis.core.clan.queries.GetClanById;
import co.sideralis.core.queries.clan.model.ClanViewModel;

import java.util.Map;

@QueryHandles
@QueryPath(name = "clan")
public class ClanById extends ViewModelExecutor<ClanViewModel> {
    @Override
    public ClanViewModel apply(Map<String, String> params) {
        var query = new GetClanById();
        query.setId(params.get("clanId"));
        return getDataMapped("clans", ClanViewModel.class)
                .applyAsElement(query);
    }
}
