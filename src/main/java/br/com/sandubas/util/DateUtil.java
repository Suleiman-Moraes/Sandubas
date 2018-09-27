package br.com.sandubas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Classe de métodos utilitários associados ao classe Date
 * </p>
 * 
 * @author thiago-amm
 * @version v1.0.1 28/11/2016
 * @since v1.0.0 23/11/2016
 * @see java.util.Date
 */
public class DateUtil {

	private static SimpleDateFormat formatador = new SimpleDateFormat();

	public static String getDiaMesAno(Date data) {
		verificarData(data);
		formatador.applyPattern("dd/MM/yyyy");
		return formatador.format(data);
	}
	
	public static String getAnoMesDia(Date data) {
		verificarData(data);
		formatador.applyPattern("yyyy-MM-dd");
		return formatador.format(data);
	}

	public static String getHorasMinutosSegundos(Date data) {
		verificarData(data);
		formatador.applyPattern("HH:mm:ss");
		return formatador.format(data);
	}

	public static String getHorasMinutos(Date data) {
		verificarData(data);
		formatador.applyPattern("HH:mm");
		return formatador.format(data);
	}

	public static String getDataPorExtenso(Date data) {
		verificarData(data);
		formatador.applyPattern("EEEEE dd 'de' MMMMM 'de' yyyy");
		return formatador.format(data);
	}

	public static String getDataAtualPorExtenso() {
		return getDataPorExtenso(new Date());
	}

	public static String getDataFormatada(Date data, String formato) {
		verificarData(data);
		verificarFormato(formato);
		formatador.applyPattern(formato);
		return formatador.format(data);

	}

	public static String getDataAtualFormatada(String formato) {
		verificarFormato(formato);
		formatador.applyPattern(formato);
		return formatador.format(new Date());

	}

	public static void verificarData(Date data) {
		if (data == null) {
			throw new IllegalArgumentException("A data não pode ser nula!");
		}
	}

	public static void verificarFormato(String formato) {
		if (Assert.isEmptyOrNull(formato)) {
			throw new IllegalArgumentException("O formato não pode ser vazio!");
		}
	}

	public static String getDataFormatoSQL(String data) {
		if (Assert.isNotEmpty(data)) {
			String[] array = data.split("/");
			return String.format("%s-%s-%s", array[2], array[1], array[0]);
		}
		return "";
	}

	public static String getDataFormatoSQLSomandoUmDia(String data) throws ParseException {
		if (Assert.isNotEmpty(data)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(data));
			c.add(Calendar.DATE, 1);
			String[] array = sdf.format(c.getTime()).split("/");
			return String.format("%s-%s-%s", array[2], array[1], array[0]);
		}
		return "";
	}

	public static Date getDataAtualSomandoUmDia() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	
	public static Date getDataSomandoUmDia(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	public static String dataFim(Date dataFim) {
		formatador.applyPattern("yyyy-MM-dd");
		return formatador.format(dataFim) + " 23:59:59";
	}

}
