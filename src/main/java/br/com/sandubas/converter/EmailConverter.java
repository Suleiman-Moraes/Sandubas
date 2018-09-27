package br.com.sandubas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sandubas.dao.EmailDAO;
import br.com.sandubas.model.Email;
import br.com.sandubas.util.cdi.CDIServiceLocator;

@FacesConverter(value = "EmailConverter")
public class EmailConverter implements Converter {

	private EmailDAO emailDAO;

	public EmailConverter() {
		this.emailDAO = CDIServiceLocator.getBean(EmailDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Email retorno = null;

		if (value != null) {
			retorno = this.emailDAO.findById(Email.class, new Long(value));
		}

		return (Email) retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Email) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}