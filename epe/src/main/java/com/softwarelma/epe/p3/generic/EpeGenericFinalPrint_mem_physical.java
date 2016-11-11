package com.softwarelma.epe.p3.generic;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeGenericFinalPrint_mem_physical extends EpeGenericAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String str = retrievePhysicalRam() + "";
        this.log(execParams, str);
        return this.createResult(str);
    }

    /**
     * in Bytes
     */
    public static long retrievePhysicalRam() throws EpeAppException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Object attribute;
        long physicalRam = -1;

        try {
            attribute = mBeanServer.getAttribute(new ObjectName("java.lang", "type", "OperatingSystem"),
                    "TotalPhysicalMemorySize");
            physicalRam = Long.parseLong(attribute.toString());
        } catch (Exception e) {
            throw new EpeAppException("retrievePhysicalRam(): " + physicalRam, e);
        }

        return physicalRam;
    }

}
