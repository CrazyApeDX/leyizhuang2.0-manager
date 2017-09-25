package com.ynyes.fitment.core.entity.session;

import com.ynyes.fitment.core.entity.ApplicationEntity;

public class SessionContent<Content> extends ApplicationEntity {
	
	private String sessionId;
	
	private Content content;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	} 
}
