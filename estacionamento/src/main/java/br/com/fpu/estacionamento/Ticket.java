package br.com.fpu.estacionamento;

public class Ticket {
	private Veiculo veiculo;
	private String horaChegada;
	private String status;

	public Ticket() {

	}
	public Ticket(Veiculo veiculo, String horaChegada, String pendente) {
		super();
		this.veiculo = veiculo;
		this.horaChegada = horaChegada;
		this.status = pendente;
	}
	public Ticket(Veiculo veiculo) {
		super();
		this.veiculo = veiculo;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public String getHoraChegada() {
		return horaChegada;
	}

	public void setHoraChegada(String horaChegada) {
		this.horaChegada = horaChegada;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String imprimeTicket() {

		String ticket = new String();
		
			
		ticket += "\r\n" + veiculo.getPlaca()+";"
					+ veiculo.getMarca() + ";"
					+ veiculo.getModelo()+ ";"
					+ getHoraChegada()+ ";"
					+getStatus();

		

		return ticket;
	}

}
