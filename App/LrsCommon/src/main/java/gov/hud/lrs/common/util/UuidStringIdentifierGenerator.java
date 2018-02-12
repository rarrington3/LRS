package gov.hud.lrs.common.util;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UuidStringIdentifierGenerator implements IdentifierGenerator {

	public Serializable generate(SessionImplementor session, Object object) {
		return UUID.randomUUID().toString().toUpperCase();
	}

}
