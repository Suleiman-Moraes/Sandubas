package br.com.sandubas.primefaces.socket;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/novaManifestacao")
public class ManifestacaoResource {
	@OnMessage(encoders = { JSONEncoder.class })
	public String onMessage(String count) {
		return count;
	}
}
