package com.softwarelma.epe.p3.disk;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeDiskFinalFddelete extends EpeDiskAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "fddelete, expected the file/dir name to delete.";
        String fdNameStr = this.getStringAt(listExecResult, 0, postMessage);
        fdNameStr = EpeAppUtils.cleanFilename(fdNameStr);
        File fdToDelete = new File(fdNameStr);

        if (!fdToDelete.exists()) {
            return this.createEmptyResult();
        }

        if (fdToDelete.isDirectory()) {
            try {
                FileUtils.deleteDirectory(fdToDelete);
            } catch (IOException e) {
                new EpeAppException(postMessage, e);
            }
        } else if (fdToDelete.isFile()) {
            fdToDelete.delete();
        } else {
            throw new EpeAppException("The file/dir \"" + fdNameStr + "\" is neither a directory nor a file.");
        }

        return this.createEmptyResult();
    }

}
