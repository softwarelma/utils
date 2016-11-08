package com.softwarelma.epe.p3.disk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

/**
 * absolute names
 * 
 * @author ellison
 *
 */
public final class EpeDiskFinalList_dirs_recursive extends EpeDiskAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "list_dirs_recursive, expected dir name.";
        String dirName = this.getStringAt(listExecResult, 0, postMessage);
        dirName = EpeAppUtils.cleanDirName(dirName);
        File dir = new File(dirName);
        EpeAppUtils.checkDir(dir);
        File[] arrayFile = dir.listFiles();
        List<String> listSource = new ArrayList<>();

        for (File file : arrayFile) {
            listSource.add(file.getPath());
        }

        List<String> listRet = new ArrayList<>();
        this.listDirsRecursive(listSource, listRet);
        this.log(execParams, listRet);
        return this.createResult(listRet);
    }

    private void listDirsRecursive(List<String> listSource, List<String> listRet) throws EpeAppException {
        while (true) {
            if (listSource.isEmpty()) {
                break;
            }

            String source = listSource.remove(0);
            File dir = new File(source);

            if (dir.isDirectory()) {
                listRet.add(source);
                File[] arrayFile = dir.listFiles();

                for (File file : arrayFile) {
                    listSource.add(file.getPath());
                }
            }
        }
    }

}
