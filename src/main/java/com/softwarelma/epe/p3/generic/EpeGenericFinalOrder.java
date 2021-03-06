package com.softwarelma.epe.p3.generic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeGenericFinalOrder extends EpeGenericAbstract {

    @Override
    public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
        String postMessage = "order, expected list.";
        List<String> list = getListStringAt(listExecResult, 0, postMessage);

        if (listExecResult.size() == 1) {
            list = this.order(list);
        } else {
            String orderMode = getStringAt(listExecResult, 1, postMessage);
            EpeAppUtils.checkEquals("order mode", "version", orderMode, "version");

            try {
                list = this.orderVersion(list);
            } catch (NumberFormatException e) {
                throw new EpeAppException(e.getMessage(), e);
            }
        }

        log(execParams, list);
        return createResult(list);

    }

    private List<String> order(List<String> list) {
        String[] array = list.toArray(new String[] {});
        Arrays.sort(array);
        return EpeAppUtils.asList(array);
    }

    private List<String> orderVersion(List<String> list) throws NumberFormatException {
        Comparator<String> stringVersionComparator = new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                try {
                    int ret = EpeGenericFinalOrder.compareVersion(o1, o2);
                    return ret;
                } catch (EpeAppException e) {
                    throw new NumberFormatException(e.getMessage());
                }
            }

        };

        String[] array = list.toArray(new String[] {});
        Arrays.sort(array, stringVersionComparator);
        return EpeAppUtils.asList(array);
    }

    private static int compareVersion(String str1, String str2) throws EpeAppException {
        str1 = EpeGenericFinalFind_version.retrieveVersion(str1);
        str2 = EpeGenericFinalFind_version.retrieveVersion(str2);
        String[] array1 = str1.split("\\.");
        String[] array2 = str2.split("\\.");

        for (int i = 0; i < Math.min(array1.length, array2.length); i++) {
            int i1 = EpeAppUtils.parseInt(array1[i]);
            int i2 = EpeAppUtils.parseInt(array2[i]);

            if (i1 < i2) {
                return -1;
            }

            if (i1 > i2) {
                return 1;
            }
        }

        if (array1.length < array2.length) {
            return -1;
        } else if (array1.length > array2.length) {
            return 1;
        } else {
            return 0;
        }
    }

}
