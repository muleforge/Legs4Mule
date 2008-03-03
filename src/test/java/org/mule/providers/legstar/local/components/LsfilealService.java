package org.mule.providers.legstar.local.components;

import com.legstar.test.coxb.lsfileal.ReplyDataType;
import com.legstar.test.coxb.lsfileal.RequestParmsType;

public interface LsfilealService {
    public ReplyDataType lsfileal(RequestParmsType request);

}
