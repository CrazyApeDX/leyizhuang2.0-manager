package com.ynyes.fitment.core.entity.client.result;

import com.ynyes.fitment.core.entity.client.ClientEntity;

public class ClientResult extends ClientEntity {

	public enum ActionCode {
		SUCCESS, FAILURE
	}
	
	public ClientResult(ActionCode actionCode, Object content) {
		super();
		this.actionCode = actionCode;
		this.content = content;
	}

	private ActionCode actionCode;
	
	private Object content;

	public ActionCode getActionCode() {
		return actionCode;
	}

	public void setActionCode(ActionCode actionCode) {
		this.actionCode = actionCode;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
