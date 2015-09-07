package br.com.fpu.estacionamento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Nota {
	private Ticket ticket;
	private String tempoParado;
	private String horaSaida;
	private Double Total;
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public String getTempoParado() {
		return tempoParado;
	}
	public void setTempoParado(String tempoParado) {
		this.tempoParado = tempoParado;
	}
	public String getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(String horaSaida) {
		this.horaSaida = horaSaida;
	}
	public Double getTotal() {
		return Total;
	}
	public void setTotal(Double total) {
		Total = total;
	}
	
	public Nota calculoNota(Nota nota){
		
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
         // OU
         SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

         Date data = new Date();

         Calendar cal = Calendar.getInstance();
         cal.setTime(data);
         Date data_atual = cal.getTime();

        // String data_completa = dateFormat.format(data_atual);

         String horaAtual = dateFormat_hora.format(data_atual);
        


         //pega a hora de chegada de cada ticket
         String horaChegada = this.getTicket().getHoraChegada();
        
         //separa a hora em partes a partir do delimitador (:)
         String[] horaChegadaHMS = horaChegada.split(":");
         //String[] horaAtualHMS = horaAtual.split(":");

         Calendar calTempoParado = Calendar.getInstance();
         
         calTempoParado.set(Calendar.HOUR, Integer.parseInt(horaChegadaHMS[0]));
         calTempoParado.set(Calendar.MINUTE, Integer.parseInt(horaChegadaHMS[1]));
         calTempoParado.set(Calendar.SECOND, Integer.parseInt(horaChegadaHMS[2]));

         long diferenca = System.currentTimeMillis() - calTempoParado.getTimeInMillis();
         long diferencaSeg = diferenca / 1000 % 60;
         long diferencaMin = diferenca / (60 * 1000) % 60;
         long diferencaHor = diferenca / (60 * 60 * 1000);

         Calendar calDiferenca = Calendar.getInstance();
         calDiferenca.set(Calendar.HOUR, Integer.parseInt(String.valueOf(diferencaHor)));
         calDiferenca.set(Calendar.MINUTE, Integer.parseInt(String.valueOf(diferencaMin)));
         calDiferenca.set(Calendar.SECOND, Integer.parseInt(String.valueOf(diferencaSeg)));



         Date dDiferenca = calDiferenca.getTime();
         String strDiferenca = dateFormat_hora.format(dDiferenca);
         
         nota.setTempoParado(strDiferenca);
         nota.setHoraSaida(horaAtual);


         Double valorTotal =  calculaTotalTicket( calDiferenca);
         nota.setTotal(valorTotal);
         ticket.setStatus(String.valueOf(StatusTicket.CONCLUIDO));
		return nota;
	}
	
	public Double calculaTotalTicket(Calendar calDiferenca){
        Double valorTotal = 0.0;
        if( (calDiferenca.get(Calendar.MINUTE) < 15)&& (calDiferenca.get(Calendar.HOUR)<1)){
            valorTotal = 0.0;
        }else if((calDiferenca.get(Calendar.MINUTE) > 15)&& (calDiferenca.get(Calendar.HOUR)<1)){
            valorTotal = 2.00;
        }else if((calDiferenca.get(Calendar.MINUTE) <= 59)&& (calDiferenca.get(Calendar.HOUR)<2)){
            valorTotal = 5.00;
        }else {
        	valorTotal = 7.00;
        }


        return valorTotal;
    }
}
