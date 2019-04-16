package com.banking.chestnut.ror.controllers;


import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ClientInfo;
import com.banking.chestnut.models.Location;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.ror.dto.ClientInfoDto;
import com.banking.chestnut.ror.dto.Info;
import com.banking.chestnut.ror.dto.ClientDto;
import com.banking.chestnut.ror.services.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping( value = "/client")
public class ClientController {

    private final int PESEL_LENGTH = 11;

    private IClientService clientService;

    private IClientInfoService clientInfoService;

    private IContactService contactService;

    private IDocumentService documentService;

    private ILocationService locationService;

    private static ModelMapper modelMapper = new ModelMapper();

    private static ObjectMapper mapper = new ObjectMapper();

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
            if(check.isPresent()) {
                return new ResponseEntity<>(ResponseObject.createError("User already exists"), HttpStatus.CONFLICT);
            }
            if (clientDto.getPesel().toString().length() < PESEL_LENGTH || !this.clientService.isValidPesel(clientDto.getPesel())){
                return new ResponseEntity<>(ResponseObject.createError("Wrong pesel length"), HttpStatus.BAD_REQUEST);
            }
            ClientInfo clientInfo =  modelMapper.map(clientDto, ClientInfo.class);
            Location location = modelMapper.map(clientDto, Location.class);
            clientInfo.setBirthday(this.clientService.extractBirthdayFromPesel(clientInfo.getPesel()));
            this.clientInfoService.save(clientInfo);
            Client client = new Client();
            client.setUuid(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));
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

            return new ResponseEntity<>(ResponseObject.createSuccess("Client saved"), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during save client"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/clients")
    @ResponseBody
    ResponseEntity getAllClients(){
        List<ClientInfoDto> clientInfoDtos = new ArrayList<>();
        try {
            List<Client> clients = this.clientService.getAll();
            clients.forEach(item -> clientInfoDtos.add(new ClientInfoDto(item.getId(), modelMapper.map(item.getClientInfoId(), Info.class))));

//            Jak jest wiele danych do wyslania to mozna w ten sposob dodawac
//            JsonNode json = Helper.createJson();
//            Helper.addProperty(json, "clients", mapper.valueToTree(clientInfoDtos));
            JsonNode returnData = mapper.valueToTree(clientInfoDtos);

            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching client data"), HttpStatus.BAD_REQUEST);
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
                JsonNode returnData = mapper.valueToTree(clientDto);

                return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching client data"), HttpStatus.BAD_REQUEST);
        }
    }


}
