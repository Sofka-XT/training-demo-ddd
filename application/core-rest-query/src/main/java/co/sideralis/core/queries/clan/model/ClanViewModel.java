package co.sideralis.core.queries.clan.model;

import co.com.sofka.domain.generic.ViewModel;

import java.util.Map;


public class ClanViewModel implements ViewModel {
    private String name;
    private String id;
    private String gitlabGroupId;
    private String gitlabName;
    private String gitlabPath;
    private Map<String, Member> members;

    public String getGitlabGroupId() {
        return gitlabGroupId;
    }

    public void setGitlabGroupId(String gitlabGroupId) {
        this.gitlabGroupId = gitlabGroupId;
    }

    public String getGitlabName() {
        return gitlabName;
    }

    public void setGitlabName(String gitlabName) {
        this.gitlabName = gitlabName;
    }

    public String getGitlabPath() {
        return gitlabPath;
    }

    public void setGitlabPath(String gitlabPath) {
        this.gitlabPath = gitlabPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
