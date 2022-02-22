package co.sideralis.core.queries.clan;

import co.com.sofka.business.annotation.QueryHandles;
import co.com.sofka.business.annotation.QueryPath;
import co.com.sofka.business.sync.ViewModelExecutor;
import co.sideralis.core.clan.queries.GetByMemberByClanId;
import co.sideralis.core.queries.clan.model.MemberViewModel;

import java.util.Map;

@QueryHandles
@QueryPath(name = "members-by")
public class MembersByClan extends ViewModelExecutor<MemberViewModel> {
    @Override
    public MemberViewModel apply(Map<String, String> params) {
        var query = new GetByMemberByClanId();
        query.setId(params.get("clanId"));

        return getDataMapped("clans", MemberViewModel.class)
                .applyAsElement(query);
    }
}
