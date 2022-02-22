package co.sideralis.core.queries.clan.model;

public class Member {
    private String name;
    private String id;
    private String personId;
    private Boolean isOwner;
    private String gender;
    private String gitlabMemberId;
    private String gitlabUsername;

    public String getGitlabUsername() {
        return gitlabUsername;
    }

    public void setGitlabUsername(String gitlabUsername) {
        this.gitlabUsername = gitlabUsername;
    }

    public String getGitlabMemberId() {
        return gitlabMemberId;
    }

    public void setGitlabMemberId(String gitlabMemberId) {
        this.gitlabMemberId = gitlabMemberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
