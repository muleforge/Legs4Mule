package org.mule.providers.legstar.functest;

import com.legstar.messaging.LegStarMessage;

public interface EchoService {
    public LegStarMessage echo(LegStarMessage echo);

}
