package co.sideralis.core.dojo.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.sideralis.core.dojo.values.DataInfo;

public class UpdatedDateInfo extends DomainEvent implements Incremental {
    private final DataInfo dataInfo;

    public UpdatedDateInfo(DataInfo dataInfo) {
        super("core.dojo.updateddateinfo");
        this.dataInfo = dataInfo;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }
}
