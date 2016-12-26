package com.ynyes.fitment.core.entity.client;

import com.ynyes.fitment.core.entity.ApplicationEntity;
import com.ynyes.fitment.core.entity.Translate;

public abstract class ClientEntity extends ApplicationEntity implements Translate {

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public String toXml() {
		return null;
	}
}
