package com.founder.hie.message.creator;

import com.founder.hie.message.data.AbstractMessage;
import com.founder.hie.message.data.HL7V2Message;
import com.founder.hie.message.schema.MessageSchemaDefinition;

public class HL7V2MessageCreator extends AbstractMessageCreator{

	@Override
	public AbstractMessage create(Object message, MessageSchemaDefinition msd) {
		// TODO Auto-generated method stub
		return new HL7V2Message(message, msd);
	}

	@Override
	public String getMessageType() {
		// TODO Auto-generated method stub
		return "HL7_V2_MESSAGE";
	}

}
