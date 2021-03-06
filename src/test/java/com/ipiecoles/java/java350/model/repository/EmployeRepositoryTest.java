package com.ipiecoles.java.java350.model.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatriculeVide() {
        //Given

        //When
        String lasMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lasMatricule).isNull();
    }

    @Test
    void findLastMatricule3Employe() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("M00110");
        employeRepository.save(employe);
        Employe employe1 = new Employe();
        employe1.setMatricule("T00010");
        employeRepository.save(employe1);
        Employe employe2 = new Employe();
        employe2.setMatricule("E00000");
        employeRepository.save(employe2);
        //When
        String lasMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lasMatricule).isEqualTo("00110");
    }

    @Test
    void findLastMatriculeNull() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(null);
        employeRepository.save(employe);
        //When
        String lasMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lasMatricule).isNull();
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithNormal() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C00001");
        employe.setPerformance(4);
        employeRepository.save(employe);

        Employe employe1 = new Employe();
        employe1.setMatricule("C00002");
        employe1.setPerformance(6);
        employeRepository.save(employe1);

        Employe employe2 = new Employe();
        employe2.setMatricule("C00003");
        employe2.setPerformance(8);
        employeRepository.save(employe2);

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(avg).isEqualTo(6);
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithNoC() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("E00001");
        employe.setPerformance(4);
        employeRepository.save(employe);

        Employe employe1 = new Employe();
        employe1.setMatricule("D00002");
        employe1.setPerformance(6);
        employeRepository.save(employe1);

        Employe employe2 = new Employe();
        employe2.setMatricule("T00003");
        employe2.setPerformance(8);
        employeRepository.save(employe2);

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(avg).isNull();
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithPerfNull() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C00001");
        employe.setPerformance(null);
        employeRepository.save(employe);

        Employe employe1 = new Employe();
        employe1.setMatricule("C00002");
        employe1.setPerformance(null);
        employeRepository.save(employe1);

        Employe employe2 = new Employe();
        employe2.setMatricule("C00003");
        employe2.setPerformance(null);
        employeRepository.save(employe2);

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(avg).isNull();
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithPerfNotNull() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("C00001");
        employe.setPerformance(4);
        employeRepository.save(employe);

        Employe employe1 = new Employe();
        employe1.setMatricule("C00002");
        employe1.setPerformance(null);
        employeRepository.save(employe1);

        Employe employe2 = new Employe();
        employe2.setMatricule("C00003");
        employe2.setPerformance(6);
        employeRepository.save(employe2);

        //When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(avg).isEqualTo(5);
    }
}