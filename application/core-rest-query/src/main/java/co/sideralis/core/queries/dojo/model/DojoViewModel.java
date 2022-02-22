package co.sideralis.core.queries.dojo.model;

import co.com.sofka.domain.generic.ViewModel;

import java.util.Map;

public class DojoViewModel implements ViewModel {
    private String name;
    private String status;
    private String legend;
    private String id;
    private Map<String, Coach> coach;
    private Map<String, String> rules;
    private Location location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    @Override
    public String getIdentity() {
        return id;
    }

    public Map<String, Coach> getCoach() {
        return coach;
    }

    public void setCoach(Map<String, Coach> coach) {
        this.coach = coach;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }
}
