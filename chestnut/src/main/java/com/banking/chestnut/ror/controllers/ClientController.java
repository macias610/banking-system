package com.banking.chestnut.ror.controllers;


import com.banking.chestnut.models.Clients;
import com.banking.chestnut.models.ClientsInfo;
import com.banking.chestnut.ror.dto.SaveClient;
import com.banking.chestnut.ror.services.IClientInfoService;
import com.banking.chestnut.ror.services.IClientService;
import com.banking.chestnut.ror.services.IContactService;
import com.banking.chestnut.ror.services.IDocumentService;
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

    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ClientController(IClientService clientService, IClientInfoService clientInfoService, IContactService contactService, IDocumentService documentService) {
        this.clientService = clientService;
        this.clientInfoService = clientInfoService;
        this.contactService = contactService;
        this.documentService = documentService;
        PropertyMap<SaveClient, ClientsInfo> personMap = new PropertyMap<SaveClient, ClientsInfo>() {
            protected void configure() {
                map().setNationality(source.getCountry());
                map().setLang(source.getCountry());
            }
        };
        modelMapper.addMappings(personMap);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @PostMapping(value = "/save")
    @ResponseBody
    ResponseEntity saveClientData(@RequestBody SaveClient saveClient){
        try {
            ClientsInfo clientsInfo =  modelMapper.map(saveClient, ClientsInfo.class);
            clientsInfo.setBirthday(this.clientService.extractBirthdayFromPesel(clientsInfo.getPesel()));
            this.clientInfoService.save(clientsInfo);
            Clients client = new Clients();
            client.setClientInfoId(clientsInfo);
            this.clientService.saveClient(client);
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
