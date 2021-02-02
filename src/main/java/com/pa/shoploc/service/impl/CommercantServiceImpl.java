package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.bo.User;
import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.dto.commercant.RegisterCommercantResponseDTO;
import com.pa.shoploc.enumeration.Role;
import com.pa.shoploc.exceptions.find.*;
import com.pa.shoploc.mapper.DataGouvApiAdresseMapper;
import com.pa.shoploc.repository.CommercantRepository;
import com.pa.shoploc.repository.UserRepository;
import com.pa.shoploc.service.CommercantService;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommercantServiceImpl implements CommercantService {

    private CommercantRepository commercantRepository;
    private UserRepository userRepository;
    private LieuService lieuService;
    private RestTemplate restTemplate;
    private final String GVRNT_API_URL="https://api-adresse.data.gouv.fr/search/";

    @Override
    public RegisterCommercantResponseDTO registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception {


        Commercant c = this.commercantRepository.findById(commercantDTO.getUsername()).orElse(null);
        if(c != null)
            throw new CommercantAlreadyExistException();


        User u = this.userRepository.findById(commercantDTO.getUsername()).orElse(null);
        if(u != null)
            throw new EmailAlreadyExistException();

        Commercant commercant = new Commercant();
        commercant.setUsername(commercantDTO.getUsername());
        commercant.setPassword(commercantDTO.getPassword());
        commercant.setDescription(commercantDTO.getDescription());
        commercant.setImage(commercantDTO.getImage());
        commercant.setRole(commercantDTO.getRole());
        commercant.setSiret(commercantDTO.getSiret());


        Lieu lieuCommercant=lieuService.findOneById(commercantDTO.getLieuId());

        //on va générer les coordonnees du lieu et l'enregistrer dans la table lieu
        generateCoordXY(lieuCommercant);

        commercant.setLieu(lieuCommercant);

        commercant.setLibelleMagasin(commercantDTO.getLibelleMagasin());
        this.commercantRepository.save(commercant);
        return new RegisterCommercantResponseDTO(commercant.getUsername());
    }

    public List<Commercant> listAllCommercants(){
        return this.commercantRepository.findByRole_(Role.COMMERCANT);
    }

    @Override
    public List<Commercant> listAllCommercantsInValidation() {
        return this.commercantRepository.findByRole_(Role.EN_ATTENTE);
    }

    @Override
    public Commercant authorizeCommercant(String username, boolean accept) throws UserNotFoundException {
        if(accept){
           Commercant c= this.commercantRepository.findById(username).orElse(null);
           if(c==null)
               throw new UserNotFoundException();
           c.setRole(Role.COMMERCANT);
           return this.commercantRepository.save(c);
        }else{
            this.userRepository.deleteById(username);
            return null;
        }
    }


    /**
     * Genere les coordonnee x et y à partir d'une adresse
     * @param lieu
     * @return
     */
    private void generateCoordXY(Lieu lieu) throws Exception {
        String url=GVRNT_API_URL+"?q=" +lieu.getAdresse()+" "+lieu.getVille()+"&limit=1";
        DataGouvApiAdresseMapper mapper=restTemplate.getForObject(url, DataGouvApiAdresseMapper.class);

        if(mapper!=null&& mapper.getFeatures()!=null
                &&mapper.getFeatures().get(0)!=null&& mapper.getFeatures().get(0).getGeometry()!=null){

            double[] coordinates=mapper.getFeatures().get(0).getGeometry().getCoordinates();
            lieu.setCoordx(coordinates[1]);
            lieu.setCoordy(coordinates[0]);

            lieuService.save(lieu);
        }else{
            throw new AdresseNotFoundException();
        }


    }

    @Override
    public Commercant findCommercantById(String username) throws CommercantNotFoundException {
        Commercant c= commercantRepository.findById(username).orElse(null);
        if(c==null)
            throw new CommercantNotFoundException();
        return c;
    }

    @Autowired
    public void setCommercantRepository(CommercantRepository commercantRepository) {
        this.commercantRepository = commercantRepository;
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
