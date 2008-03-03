package org.mule.providers.legstar.local.components;

import com.legstar.test.coxb.lsfileal.Filler65Type;
import com.legstar.test.coxb.lsfileal.ReplyDataType;
import com.legstar.test.coxb.lsfileal.ReplyItemType;
import com.legstar.test.coxb.lsfileal.ReplyPersonalType;
import com.legstar.test.coxb.lsfileal.ReplySuccessHeaderType;
import com.legstar.test.coxb.lsfileal.RequestParmsType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LsfilealComponent implements LsfilealService {

    /** Logger. */
    private final Logger LOG = LoggerFactory.getLogger(LsfilealComponent.class);

    @Override
    public ReplyDataType lsfileal(RequestParmsType request) {
        
        LOG.debug("RequestName={}", request.getRequestName());
        
        ReplySuccessHeaderType replySuccessHeader = new ReplySuccessHeaderType();
        replySuccessHeader.setTotalItemsRead(45);
        replySuccessHeader.setSearchDuration("00:00:01");
        Filler65Type filler65 = new Filler65Type();
        filler65.setReplyItemscount(5);
        for(int i = 0; i < 5; i++) {
            ReplyItemType replyItem = new ReplyItemType();
            ReplyPersonalType replyPersonal = new ReplyPersonalType();
            replyPersonal.setReplyAddress("SURREY, ENGLAND");
            replyPersonal.setReplyName("S. D. BORMAN");
            replyPersonal.setReplyPhone("32156778");
            replyItem.setReplyPersonal(replyPersonal);
            replyItem.setReplyAmount("$0100.11");
            replyItem.setReplyComment("*********");
            replyItem.setReplyDate("26 11 81");
            replyItem.setReplyNumber(100);
            
            filler65.getReplyItem().add(replyItem);
        }
        
        ReplyDataType reply = new ReplyDataType();
        reply.setReplyErrorHeader(null);
        reply.setFiller65(filler65);
        reply.setReplySuccessHeader(replySuccessHeader);
        reply.setReplyType(0);
        return reply;
    }

}
