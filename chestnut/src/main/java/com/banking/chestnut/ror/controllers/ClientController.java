package com.banking.chestnut.ror.controllers;


import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ClientInfo;
import com.banking.chestnut.models.Location;
import com.banking.chestnut.ror.dto.ClientInfoDto;
import com.banking.chestnut.ror.dto.Info;
import com.banking.chestnut.ror.dto.ClientDto;
import com.banking.chestnut.ror.services.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        PropertyMap<ClientDto, ClientInfo> personMap = new PropertyMap<ClientDto, ClientInfo>() {
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
    ResponseEntity saveClientData(@RequestBody ClientDto clientDto){
        try {
            Optional<Client> check = this.clientService.getByPesel(clientDto.getPesel());
            if(check.isPresent())
                return new ResponseEntity("User already exists", HttpStatus.CONFLICT);
            ClientInfo clientInfo =  modelMapper.map(clientDto, ClientInfo.class);
            Location location = modelMapper.map(clientDto, Location.class);
            clientInfo.setBirthday(this.clientService.extractBirthdayFromPesel(clientInfo.getPesel()));
            this.clientInfoService.save(clientInfo);
            Client client = new Client();
            client.setClientInfoId(clientInfo);
            this.clientService.saveClient(client);
            location.setClientId(client);
            this.locationService.saveLocation(location);
            if(!clientDto.getContacts().isEmpty()){
                clientDto.getContacts().forEach(item -> item.setClientId(client));
                clientDto.getContacts().forEach(item -> this.contactService.saveContact(item));
            }
            if(!clientDto.getDocuments().isEmpty()){
                clientDto.getDocuments().forEach(item -> item.setClientId(client));
                clientDto.getDocuments().forEach(item -> this.documentService.saveDocument(item));
            }
            return new ResponseEntity("Client saved", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Error during save client", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/clients")
    @ResponseBody
    ResponseEntity getAllClients(){
        List<ClientInfoDto> clientInfoDtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            List<Client> clients = this.clientService.getAll();
            if(!clients.isEmpty()){
                clients.forEach(item -> clientInfoDtos.add(new ClientInfoDto(item.getId(), modelMapper.map(item.getClientInfoId(), Info.class))));
                for (int i=0; i < clientInfoDtos.size(); i++){
                    clientInfoDtos.get(i).getInfo().setBirthday(formatter.format(clients.get(i).getClientInfoId().getBirthday()));
                }
                return new ResponseEntity(clientInfoDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity(clientInfoDtos, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e){
            return new ResponseEntity("Error during fetching client data", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    ResponseEntity getClientById(@PathVariable Integer id){
        try {
            Optional<Client> client = this.clientService.getById(id);
            if(client.isPresent()){
                Client result = client.get();
                ClientDto clientDto = modelMapper.map(result.getClientInfoId(), ClientDto.class);
                clientDto.setLocation(result.getLocation());
                clientDto.setDocuments(result.getDocuments());
                clientDto.setContacts(result.getContacts());
                return new ResponseEntity(clientDto, HttpStatus.OK);
            }
            else
                return new ResponseEntity("Client not found", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("Error during fetching client data", HttpStatus.BAD_REQUEST);
        }
    }
}
