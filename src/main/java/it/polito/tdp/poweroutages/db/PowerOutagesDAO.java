package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Evento;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Evento.EventType;

public class PowerOutagesDAO {
	
	public void loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";
		//List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				//nercList.add(n);
				idMap.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		//return nercList;
	}

	public List<Adiacenza> getAdiacenze(Map<Integer, Nerc> idMap) {
		
		final String sql = "SELECT p1.nerc_id as n1, p2.nerc_id as n2, COUNT(*) AS c " + 
				"FROM poweroutages AS p1, poweroutages AS p2 " + 
				"WHERE p1.nerc_id < p2.nerc_id AND " + 
				"((YEAR(p1.date_event_began) = YEAR(p2.date_event_began) AND  " + 
				"MONTH(p1.date_event_began) <> MONTH(p2.date_event_began)) OR  " + 
				"(YEAR(p1.date_event_began) <> YEAR(p2.date_event_began) AND " + 
				"MONTH(p1.date_event_began) = MONTH(p2.date_event_began))) " + 
				"GROUP BY p1.nerc_id, p2.nerc_id";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Nerc n1 = idMap.get(rs.getInt("n1"));
				Nerc n2 = idMap.get(rs.getInt("n2"));
				result.add(new Adiacenza(n1, n2, (double) rs.getInt("c")));
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public List<Evento> getEvents(Map<Integer, Nerc> idMap) {
		
		final String sql = "SELECT nerc_id AS n, date_event_began AS inizio, date_event_finished AS fine " + 
				"FROM poweroutages";
		List<Evento> events = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Nerc n = idMap.get(rs.getInt("n"));
				//LocalDateTime inizio = rs.getDate("inizio").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime inizio = Instant.ofEpochMilli(rs.getDate("inizio").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime fine = Instant.ofEpochMilli(rs.getDate("fine").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
				//LocalDateTime fine = rs.getDate("fine").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				int giorni = (int) Duration.between(inizio, fine).toDays();
				Evento e1 = new Evento(n, EventType.INIZIO, inizio, giorni);
				Evento e2 = new Evento(n, EventType.FINE, fine, giorni);
				events.add(e1);
				events.add(e2);
			}
			
			conn.close();
			return events;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
