package com.banking.chestnut.ror.controllers;


import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ClientInfo;
import com.banking.chestnut.models.Location;
import com.banking.chestnut.ror.dto.SaveClient;
import com.banking.chestnut.ror.services.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/client")
public class ClientController {

    private IClientService clientService;

    private IClientInfoService clientInfoService;

    private IContactService contactService;

    private IDocumentService documentService;

    private ILocationService locationService;

    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ClientController(IClientService clientService, IClientInfoService clientInfoService, IContactService contactService,
                            IDocumentService documentService, ILocationService locationService) {
        this.clientService = clientService;
        this.clientInfoService = clientInfoService;
        this.contactService = contactService;
        this.documentService = documentService;
        this.locationService = locationService;
        PropertyMap<SaveClient, ClientInfo> personMap = new PropertyMap<SaveClient, ClientInfo>() {
            protected void configure() {
                map().setNationality(source.getCountry());
                map().setLang(source.getCountry());
            }
        };
        modelMapper.addMappings(personMap);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @PostMapping(value = "/save")
    @ResponseBody
    ResponseEntity saveClientData(@RequestBody SaveClient saveClient){
        try {
            ClientInfo clientInfo =  modelMapper.map(saveClient, ClientInfo.class);
            Location location = modelMapper.map(saveClient, Location.class);
            clientInfo.setBirthday(this.clientService.extractBirthdayFromPesel(clientInfo.getPesel()));
            this.clientInfoService.save(clientInfo);
            Client client = new Client();
            client.setClientInfoId(clientInfo);
            this.clientService.saveClient(client);
            location.setClientId(client);
            this.locationService.saveLocation(location);
            if(!saveClient.getContacts().isEmpty()){
                saveClient.getContacts().forEach(item -> item.setClientId(client));
                saveClient.getContacts().forEach(item -> this.contactService.saveContact(item));
            }
            if(!saveClient.getDocuments().isEmpty()){
                saveClient.getDocuments().forEach(item -> item.setClientId(client));
                saveClient.getDocuments().forEach(item -> this.documentService.saveDocument(item));
            }
            return new ResponseEntity("Client saved", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Error during save client", HttpStatus.BAD_REQUEST);
        }
    }
}
