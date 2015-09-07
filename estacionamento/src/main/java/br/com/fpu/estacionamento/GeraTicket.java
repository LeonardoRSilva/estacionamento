package br.com.fpu.estacionamento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/geraTicket")
public class GeraTicket extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File("c:/arquivojava/Ticket.txt");
		
		if (readFile(req, resp, file)) {
			Veiculo veiculo = new Veiculo();
			veiculo.setPlaca(req.getParameter("placa"));
			veiculo.setMarca(req.getParameter("marca"));
			veiculo.setModelo(req.getParameter("modelo"));

			Ticket ticket = new Ticket(veiculo, getHora(), String.valueOf(StatusTicket.PENDENTE));

			try (FileWriter fileWriter = new FileWriter(file, true)) {

				fileWriter.write(ticket.imprimeTicket());
				gerarTicket(resp, ticket);

			}
		}

	}

	private boolean readFile(HttpServletRequest req, HttpServletResponse resp, File file)
			throws IOException, FileNotFoundException {

		int i = 0;
		String[] linha1;
		linha1 = new String[10];
		if (file.isFile()) {

			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {

					linha1[i] = scanner.nextLine();

					i++;

				}

				for (int j = 0; j < i; j++) {

					String[] tic = linha1[j].split(";");

					if (req.getParameter("placa").equalsIgnoreCase(tic[0])) {
						PrintWriter writer = resp.getWriter();
						writer.write("Erro, ja existe um veiculo com a placa informada");
						return false;
					}

				}
			}
			return true;

		} else {
			System.out.println("nao e arquivo");
			return false;
		}

	}

	private String getHora() {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
		// OU
		SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

		Date data = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		Date data_atual = cal.getTime();

		//String data_completa = dateFormat.format(data_atual);

		String hora_atual = dateFormat_hora.format(data_atual);
		return hora_atual;
	}

	private void gerarTicket(HttpServletResponse resp, Ticket ticket) throws IOException {
		PrintWriter writer = resp.getWriter();

		StringBuilder text = new StringBuilder();
		// DecimalFormat fmt = new DecimalFormat("#,##0.00");

		text.append("<html>");
		text.append("<head>");
		text.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		text.append("<meta charset=\"utf-8\">");
		text.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		text.append("<title>Exercicio com Servlet</title>");
		text.append("<link rel=\"stylesheet\"");
		text.append("href=\"resources/bootstrap-3.3.5-dist/css/bootstrap.min.css\">");
		text.append("<link rel=\"stylesheet\"");
		text.append("href=\"resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css\">");
		text.append("</head>");
		text.append("<body>");
		text.append("");

		text.append("<div class=\"container\"style=\"width:305px;\">");
		text.append("<div class=\"panel panel-default\">");
		text.append(
				"<div class=\"panel-heading\"> Estacionamento <br><a href=\"index.html\" class=\"btn btn-default\">home</a> ");
		text.append(" <a href=\"Entrada.html\" class=\"btn btn-default\">Nova Entrada</a> ");
		text.append("</div> ");

		text.append("<h6>Placa:<strong>");
		text.append(ticket.getVeiculo().getPlaca());
		text.append("</strong></h6>");

		text.append("<h6>Marca:<strong>");
		text.append(ticket.getVeiculo().getMarca());
		text.append("</strong></h6>");

		text.append("<h6>Modelo:<strong>");
		text.append(ticket.getVeiculo().getModelo());
		text.append("</strong></h6>");

		text.append("<h6>Hora da chegada:<strong>");
		text.append(ticket.getHoraChegada());
		text.append("</strong></h6>");

		text.append("<h6>Status:<strong>");
		text.append(ticket.getStatus());
		text.append("</strong></h6>");

		text.append("<table class=\"table table-bordered table-striped\" style=\"width:300px;\"><thead>");
		text.append("<tr>");
		text.append("<th>Hora</th>");
		text.append("<th>valor</th>");
		text.append("</tr>");
		text.append("</thead><tbody>");

		text.append("<tr>");
		text.append("<td>");
		text.append("ate 15 minutos");
		text.append("</td>");
		text.append("<td>R$");
		text.append("0.00");
		text.append("</td>");
		text.append("</tr>");

		text.append("<tr>");
		text.append("<td>");
		text.append("entre 15 min e 1h");
		text.append("</td>");
		text.append("<td>R$");
		text.append("2.00");
		text.append("</td>");
		text.append("</tr>");

		text.append("<tr>");
		text.append("<td>");
		text.append(" 2h");
		text.append("</td>");
		text.append("<td>R$");
		text.append("5.00");
		text.append("</td>");
		text.append("</tr>");

		text.append("<tr>");
		text.append("<td>");
		text.append("mais que 2h");
		text.append("</td>");
		text.append("<td>R$");
		text.append("7.00");
		text.append("</td>");
		text.append("</tr>");

		text.append("</tbody></table>");
		text.append("</div>");
		text.append("</div>");
		text.append("</body>");
		text.append(" </html>");

		System.out.println(text.toString());
		// writer.print(String.format("Resultado da soma %s", resultado));
		writer.print(String.format(text.toString()));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.write("suporte apenas para metodo post");
	}

}
