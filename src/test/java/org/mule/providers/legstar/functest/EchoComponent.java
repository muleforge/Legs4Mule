package org.mule.providers.legstar.functest;

import com.legstar.messaging.LegStarMessage;

public class EchoComponent implements EchoService {

    @Override
    public LegStarMessage echo(LegStarMessage echo) {
        return echo;
    }

}
