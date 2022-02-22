package co.sideralis.core.dojo.commands;

import co.com.sofka.domain.generic.Command;
import co.sideralis.core.dojo.values.DataInfo;

public class UpdateDataInfo implements Command {
    private final DataInfo dataInfo;

    public UpdateDataInfo(DataInfo dataInfo) {

        this.dataInfo = dataInfo;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }
}
