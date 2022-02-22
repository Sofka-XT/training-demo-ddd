package co.sideralis.core.queries.clan.model;

import co.com.sofka.domain.generic.ViewModel;

import java.util.Map;

public class MemberViewModel implements ViewModel {
    private String id;

    private Map<String, Member> members;

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }

    @Override
    public String getIdentity() {
        return id;
    }

}
