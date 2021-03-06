package com.softwarelma.epe.p3.sys;

import java.util.List;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeSysFinalSys_get_prop extends EpeSysAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "sys_get_prop, expected the key.";
        String key = getStringAt(listExecResult, 0, postMessage).trim();
        String value = System.getProperty(key).trim();
        log(execParams, value);
        return createResult(value);
    }

}
