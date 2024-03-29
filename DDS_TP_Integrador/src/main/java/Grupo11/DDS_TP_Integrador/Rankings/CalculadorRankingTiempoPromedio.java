package Grupo11.DDS_TP_Integrador.Rankings;

import Grupo11.DDS_TP_Integrador.Entidades.Entidad;
import Grupo11.DDS_TP_Integrador.Repositories.RankingMasIncidentesRepository;
import Grupo11.DDS_TP_Integrador.Repositories.RankingPromedioCierreRepository;
import Grupo11.DDS_TP_Integrador.Repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculadorRankingTiempoPromedio extends CalculadorRanking{


    @Autowired
    RankingPromedioCierreRepository rankingPromedioCierreRepository;

    @Autowired
    RankingRepository rankingRepository;

    @Override
    public List<Entidad> calcularRanking(List<Entidad> entidades) {

        LocalDateTime fechaActual = LocalDateTime.now();

        // Obtener la fecha y hora hace 7 días
        LocalDateTime fechaHace7Dias = fechaActual.minusDays(7);

        Comparator<Entidad> comparadorPorPromedioIncidente = Comparator.comparingLong((Entidad e) -> incidenteProvider.promedioIncidentes(e.getIncidentes_reportados_abierto_en_semana(fechaHace7Dias, fechaActual)));


        return entidades.stream()
                .sorted(comparadorPorPromedioIncidente)
                .collect(Collectors.toList());
    }

    List<RankingPromedioCierre> generarRankingPromedioCierre(List<Entidad> entidades, Ranking nuevoRanking)
    {
        LocalDateTime fechaActual = LocalDateTime.now();

        return entidades.stream()
                .map(entidad -> {
                    int posicion = entidades.indexOf(entidad) + 1; // le sumo uno para que arranque desde 1
                    return new RankingPromedioCierre(entidad, posicion, fechaActual, nuevoRanking);
                })
                .collect(Collectors.toList());

    }

    public void guardarRankingPromedioCierre(List<Entidad> entidades, Ranking nuevoRanking){
        rankingRepository.save(nuevoRanking);
        List<RankingPromedioCierre> ranking_promedio_cierre = this.generarRankingPromedioCierre(entidades, nuevoRanking);
        rankingPromedioCierreRepository.saveAll(ranking_promedio_cierre);
    }


}
