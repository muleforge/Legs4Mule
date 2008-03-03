package org.mule.providers.legstar.local.components;

import com.legstar.test.coxb.lsfileae.ComPersonalType;
import com.legstar.test.coxb.lsfileae.DfhcommareaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LsfileaeComponent implements LsfileaeService {

    /** Logger. */
    private final Logger LOG = LoggerFactory.getLogger(LsfileaeComponent.class);

    @Override
    public DfhcommareaType lsfileae(DfhcommareaType request) {
        
        LOG.debug("ComAmount={}", request.getComAmount());
        LOG.debug("ComComment={}", request.getComComment());
        LOG.debug("ComDate={}", request.getComDate());
        LOG.debug("ComNumber={}", request.getComNumber());
        LOG.debug("ComAddress={}", request.getComPersonal().getComAddress());
        LOG.debug("ComName={}", request.getComPersonal().getComName());
        LOG.debug("ComPhone={}", request.getComPersonal().getComPhone());
        
        DfhcommareaType reply = new DfhcommareaType();
        reply.setComNumber(299);
        reply.setComAmount("$0159.18");
        reply.setComComment("comment");
        reply.setComDate("18 04 92");
        ComPersonalType comPersonal = new ComPersonalType();
        comPersonal.setComAddress("ICI STREET");
        comPersonal.setComName("DARIUS MILO");
        comPersonal.setComPhone("125689");
        reply.setComPersonal(comPersonal);
        return reply;
    }


}
