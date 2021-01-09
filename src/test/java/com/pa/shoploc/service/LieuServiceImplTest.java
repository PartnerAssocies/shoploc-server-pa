package com.pa.shoploc.service;

import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.exceptions.find.LieuNotFoundException;
import com.pa.shoploc.exceptions.save.UnableToSaveLieuException;
import com.pa.shoploc.repository.LieuRepository;
import com.pa.shoploc.service.impl.LieuServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class LieuServiceImplTest {

    @TestConfiguration
    static class LieuServiceTestContextConfiguration {

        @Bean
        public LieuService lieuService() {
            return new LieuServiceImpl();
        }

    }

    @Autowired
    private LieuService lieuService;

    @MockBean
    private LieuRepository lieuRepository;


    @Before
    public void setUp() throws Exception {
        Lieu l=new Lieu();
        l.setVille("Lille");
        l.setCoordy(13.0);
        l.setCoordx(14.0);
        l.setAdresse("Rue gauthier");
        l.setLid(0);


        Mockito.when(lieuRepository.findById(0)).thenReturn(Optional.of(l));
    }

    @Test
    public void findOneByIdTest() throws Exception {
        Lieu result=lieuService.findOneById(0);

        assertEquals(result.getAdresse(),"Rue gauthier");

    }

    @Test(expected = LieuNotFoundException.class)
    public void findOneByIdTestLieuNotFoundException() throws Exception{
        Lieu result=lieuService.findOneById(15);
    }

    @Test(expected = UnableToSaveLieuException.class)
    public void saveTestException() throws Exception{
        Lieu toSave=new Lieu();
        toSave.setLid(2);
        toSave.setAdresse("Champs Elysée");
        toSave.setCoordx(2);
        toSave.setCoordy(15);
        toSave.setVille("Paris");

        //simuler une erreur bdd
        Mockito.when(lieuRepository.save(Mockito.any(Lieu.class))).thenReturn(null);

        lieuService.save(toSave);

    }

    @Test
    public void saveTest() throws Exception{
        Lieu toSave=new Lieu();
        toSave.setLid(2);
        toSave.setAdresse("Champs Elysée");
        toSave.setCoordx(2);
        toSave.setCoordy(15);
        toSave.setVille("Paris");

        Mockito.when(lieuRepository.save(Mockito.any(Lieu.class))).thenReturn(toSave);

        lieuService.save(toSave);
        Mockito.verify(lieuRepository,Mockito.times(1)).save(toSave);

    }

}
