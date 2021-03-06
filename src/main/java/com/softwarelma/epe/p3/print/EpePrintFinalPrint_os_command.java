package com.softwarelma.epe.p3.print;

import java.util.List;

import com.softwarelma.epe.p1.app.EpeAppConstants;
import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;
import com.softwarelma.epe.p3.generic.EpeGenericFinalIs_mac;
import com.softwarelma.epe.p3.generic.EpeGenericFinalIs_solaris;
import com.softwarelma.epe.p3.generic.EpeGenericFinalIs_unix;
import com.softwarelma.epe.p3.generic.EpeGenericFinalIs_windows;

public final class EpePrintFinalPrint_os_command extends EpePrintAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "print_os_command, expected the exec file name.";
        String execFilename = getStringAt(listExecResult, 0, postMessage);
        String str = retrieveExecOSCommand(execFilename);
        log(execParams, str);
        return createResult(str);
    }

    public static String retrieveExecOSCommand(String execFilename) throws EpeAppException {
        String execCommand = null;

        if (EpeGenericFinalIs_windows.isWindows()) {
            execCommand = EpeAppConstants.EXEC_COMMAND_WIN;
        } else if (EpeGenericFinalIs_unix.isUnix()) {
            execCommand = EpeAppConstants.EXEC_COMMAND_LIN;
        } else if (EpeGenericFinalIs_mac.isMac()) {
            execCommand = EpeAppConstants.EXEC_COMMAND_LIN;
        } else if (EpeGenericFinalIs_solaris.isSolaris()) {
            execCommand = EpeAppConstants.EXEC_COMMAND_LIN;
        } else {
            throw new EpeAppException("Unknown operating system: " + EpePrintFinalPrint_os_name.retrieveOsName());
        }

        EpeAppUtils.checkNull("execCommand (OS: " + EpePrintFinalPrint_os_name.retrieveOsName() + ")", execCommand);
        return execCommand + execFilename;
    }

}
