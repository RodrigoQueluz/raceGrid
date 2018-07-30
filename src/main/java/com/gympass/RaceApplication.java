package com.gympass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gympass.model.Piloto;
import com.gympass.model.ResultadoPiloto;
import com.gympass.model.Volta;

public class RaceApplication {

	static List<ResultadoPiloto> grid = new ArrayList<ResultadoPiloto>();
	static Date dataInicioCorrida = null;
	static Date dataFimCorrida = null;
	static Map<Integer, Double> temposCorrida = new HashMap<>();
	static Volta melhorVoltaCorrida = new Volta();

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("race.txt"))) {
			list = br.lines().collect(Collectors.toList());
			list = list.subList(1, list.size());

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String voltaNaoFormatada : list) {
			Volta volta = formataLinha(voltaNaoFormatada);
			if (primeiraVolta()) {
				dataInicioCorrida = volta.getHoraVolta();
				melhorVoltaCorrida = volta;
			}

			if (voltaAtualMelhorCorrida(volta)) {
				melhorVoltaCorrida = volta;
			}

			if (primeiraVoltaPiloto(volta)) {
				temposCorrida.put(volta.getPiloto().getId(), 0D);
			}

			calculaVelocidadeTotal(volta);
			formataResultadoFinalPiloto(volta);
			dataFimCorrida = volta.getHoraVolta();

		}

		ordenaGridPorVelocidadeMedia();

		mostraResultado();
		formataTempoTotalCorrida();
		mostraMelhorVolta();

	}

	private static void ordenaGridPorVelocidadeMedia() {
		grid.sort((r1, r2) -> {
			if (r1.getVelocidadeMedia().equals(r2.getVelocidadeMedia()) && r1.getNroVoltas() == r2.getNroVoltas()) {
				return 0;
			}
			return r1.getVelocidadeMedia() > r2.getVelocidadeMedia() && r1.getNroVoltas() >= r2.getNroVoltas() ? -1 : 1;
		});
	}

	private static void mostraMelhorVolta() {
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");
		System.out.println("Melhor volta corrida: " + melhorVoltaCorrida);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");

	}

	private static boolean primeiraVoltaPiloto(Volta volta) {
		return !temposCorrida.containsKey(volta.getPiloto().getId());
	}

	private static boolean voltaAtualMelhorCorrida(Volta volta) {
		return melhorVoltaCorrida.getTempoVolte().after(volta.getTempoVolte());
	}

	private static void mostraResultado() {
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");

		Date diferencaResultado = null;
		for (ResultadoPiloto resultadoPiloto : grid) {
			DateFormat formatoVolta = new SimpleDateFormat("mm:ss.SSS");

			if(diferencaResultado == null){
				diferencaResultado = resultadoPiloto.getHoraUltimaVolta();
			}
			
			Long diff = resultadoPiloto.getHoraUltimaVolta().getTime() - diferencaResultado.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			double diffMiliseconds = BigDecimal.valueOf(diff / 1e6)
					.setScale(3, RoundingMode.HALF_UP).doubleValue();

			System.out.println("Posicao Final: " + (grid.indexOf(resultadoPiloto) + 1) + " -> "
					+ padLeft(resultadoPiloto.getPiloto().toString()) + " " + resultadoPiloto.getNroVoltas()
					+ " voltas completadas. Velocidade Media: " + resultadoPiloto.getVelocidadeMedia()
					+ " . Melhor volta: " + resultadoPiloto.getMelhorVolta().getNroVolta() + " -> "
					+ formatoVolta.format(resultadoPiloto.getMelhorVolta().getTempoVolte())
					+ ". Diferenca: " + diffMinutes + " Minutos e " + diffSeconds + " Segundos e " + diffMiliseconds + " Milissegundos.") ;
			System.out.println("---");
		}

	}

	private static void formataResultadoFinalPiloto(Volta volta) {
		Double velocidadeMedia = BigDecimal.valueOf(temposCorrida.get(volta.getPiloto().getId()) / volta.getNroVolta())
				.setScale(3, RoundingMode.HALF_UP).doubleValue();

		Double velocidadeTotal = BigDecimal
				.valueOf(temposCorrida.get(volta.getPiloto().getId()) + volta.getVelocidadeVolta())
				.setScale(3, RoundingMode.HALF_UP).doubleValue();

		ResultadoPiloto resultado = new ResultadoPiloto();
		resultado.setPiloto(volta.getPiloto());
		if (grid.contains(resultado)) {
			resultado = grid.get(grid.indexOf(resultado));
		} else {
			grid.add(resultado);
		}
		resultado.setPiloto(volta.getPiloto());
		resultado.setNroVoltas(volta.getNroVolta());
		resultado.setVelocidadeMedia(velocidadeMedia);
		resultado.setVelocidadeTotal(velocidadeTotal);

		if (resultado.getMelhorVolta() == null) {
			resultado.setMelhorVolta(volta);
		} else {
			if (resultado.getMelhorVolta().getTempoVolte().after(volta.getTempoVolte())) {
				resultado.setMelhorVolta(volta);
			}
		}
		
		resultado.setHoraUltimaVolta(volta.getHoraVolta());

	}

	private static void calculaVelocidadeTotal(Volta volta) {
		Double velocidadeTotal = BigDecimal
				.valueOf(temposCorrida.get(volta.getPiloto().getId()) + volta.getVelocidadeVolta())
				.setScale(3, RoundingMode.HALF_UP).doubleValue();
		temposCorrida.put(volta.getPiloto().getId(), velocidadeTotal);
	}

	private static boolean primeiraVolta() {
		return dataInicioCorrida == null;
	}

	private static void formataTempoTotalCorrida() {
		Long diff = dataFimCorrida.getTime() - dataInicioCorrida.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");
		System.out.println("Tempo Total Corrida: " + diffHours + " Horas, " + diffMinutes + " Minutos, " + diffSeconds
				+ " Segundos.");
	}

	public static Volta formataLinha(String v) {
		Volta volta = new Volta();
		String[] dados = v.split("\\s+");

		try {
			volta = parseVolta(dados);
		} catch (ParseException e) {
			System.out.println("Problema ao realizar o Parse da volta");
			e.printStackTrace();
		}
		volta.setPiloto(parsePiloto(dados));

		return volta;
	}

	private static Piloto parsePiloto(String[] dados) {
		Piloto piloto = new Piloto();
		piloto.setId(Integer.parseInt(dados[1]));
		piloto.setName(dados[3]);
		piloto.setVelocidadeTotal(Double.parseDouble(dados[6].replace(",", ".")));
		return piloto;
	}

	private static Volta parseVolta(String[] dados) throws ParseException {
		Volta volta = new Volta();

		DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss.SSS");
		DateFormat formatoVolta = new SimpleDateFormat("mm:ss.SSS");

		volta.setHoraVolta(formatoHora.parse(dados[0]));
		volta.setNroVolta(Integer.parseInt(dados[4]));
		volta.setTempoVolte(formatoVolta.parse(dados[5]));
		volta.setVelocidadeVolta(Double.parseDouble(dados[6].replace(",", ".")));

		return volta;
	}

	public static String padLeft(String s) {
		return String.format("%-30s", s);
	}
}
