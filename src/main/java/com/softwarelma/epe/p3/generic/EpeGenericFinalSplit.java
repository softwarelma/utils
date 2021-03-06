package com.softwarelma.epe.p3.generic;

import java.util.List;

import com.softwarelma.epe.p1.app.EpeAppException;
import com.softwarelma.epe.p1.app.EpeAppUtils;
import com.softwarelma.epe.p2.exec.EpeExecParams;
import com.softwarelma.epe.p2.exec.EpeExecResult;

public final class EpeGenericFinalSplit extends EpeGenericAbstract {

	@Override
	public EpeExecResult doFunc(EpeExecParams execParams, List<EpeExecResult> listExecResult) throws EpeAppException {
		String postMessage = "split, expected text.";
		String text = getStringAt(listExecResult, 0, postMessage);
		String regex = getStringAt(listExecResult, 1, postMessage);
		String[] arrayStr = text.split(regex);
		List<String> listStr = EpeAppUtils.asList(arrayStr);
		log(execParams, listStr);
		return createResult(listStr);
	}

}
