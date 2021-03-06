package com.ipiecoles.java.java350.model.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @AfterEach
    public void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void embaucheEmployeXEmploye() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;

        //Then
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        String lastMatricule = employeRepository.findLastMatricule();
        Employe employe = employeRepository.findByMatricule("C" + lastMatricule);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C" + lastMatricule);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 1
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    void embaucheEmployeEmbauchePartiel() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 0.5;

        //Then
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        String lastMatricule = employeRepository.findLastMatricule();
        Employe employe = employeRepository.findByMatricule("C" + lastMatricule);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C" + lastMatricule);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 0.5 = 912.73
        Assertions.assertThat(employe.getSalaire()).isEqualTo(912.73);
    }

    @Test
    void calculPerformanceCommercialNormal() throws EmployeException {
        // Given
        String matricule = "C00000";
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(5);
        employeRepository.save(employe);

        //When
        employeService.calculPerformanceCommercial(matricule, 1000L, 1000L);

        //Then
        Employe employe1 = employeRepository.findByMatricule(matricule);
        Assertions.assertThat(employe1.getPerformance()).isEqualTo(5);
    }
}