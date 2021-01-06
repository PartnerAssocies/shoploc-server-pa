package com.pa.shoploc.service;

import com.pa.shoploc.bo.RefreshToken;
import com.pa.shoploc.repository.RefreshTokenRepository;
import com.pa.shoploc.service.impl.RefreshTokenServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
public class RefreshTokenServiceImplTest {
    @TestConfiguration
    static class RefreshTokenServiceTestContextConfiguration {

        @Bean
        public RefreshTokenService refreshTokenService() {
            return new RefreshTokenServiceImpl();
        }

    }

    @Autowired
    private RefreshTokenService refreshTokenService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    private static final String UUID="aer-66-DSFSDF-DFSSDF";

    @Before
    public void setUp() throws Exception {
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUsername("dupont@gmail.com");
        refreshToken.setRefreshToken(UUID);


        Mockito.when(refreshTokenRepository.findById(refreshToken.getUsername()))
                .thenReturn(Optional.of(refreshToken));
    }

    @Test
    public void findByIdTest() {

        RefreshToken result=refreshTokenService.findById("dupont@gmail.com");

        assertEquals(result.getRefreshToken(),UUID);

    }

    @Test
    public void saveTest(){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUsername("du@gmail.com");
        refreshToken.setRefreshToken(UUID);

        refreshTokenService.save(refreshToken);

        Mockito.verify(refreshTokenRepository,Mockito.times(1)).save(refreshToken);

    }

    @Test
    public void deleteByIdTest(){
        refreshTokenService.deleteById(UUID);

        Mockito.verify(refreshTokenRepository,Mockito.times(1)).deleteById(UUID);
    }





}
