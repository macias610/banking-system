package com.banking.chestnut.ror.controllers;


import com.banking.chestnut.helper.AccountNumberHelper;
import com.banking.chestnut.models.*;
import com.banking.chestnut.ror.dto.ClientInfoDto;
import com.banking.chestnut.ror.dto.Info;
import com.banking.chestnut.ror.dto.ClientDto;
import com.banking.chestnut.ror.dto.RawClientInfoDto;
import com.banking.chestnut.ror.services.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/client")
public class ClientController {

    private final int PESEL_LENGTH = 11;

    private IClientService clientService;

    private IClientInfoService clientInfoService;

    private IContactService contactService;

    private IDocumentService documentService;

    private ILocationService locationService;

    private IDataHistoryClientService dataHistoryClientService;

    private static ModelMapper modelMapper = new ModelMapper();

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ClientController(IClientService clientService, IClientInfoService clientInfoService, IContactService contactService,
                            IDocumentService documentService, ILocationService locationService, IDataHistoryClientService dataHistoryClientService) {
        this.clientService = clientService;
        this.clientInfoService = clientInfoService;
        this.contactService = contactService;
        this.documentService = documentService;
        this.locationService = locationService;
        this.dataHistoryClientService = dataHistoryClientService;
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
//            Helper.addProperty(json, "clients", mapper.valueToTree(clientInfoDtos));cf
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
            return getClientResponseEntity(client);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching client data"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/pesel/{pesel}")
    @ResponseBody
    ResponseEntity getClientByPesel(@PathVariable Long pesel){
        try {
            Optional<Client> client = this.clientService.getByPesel(pesel);
            return getClientResponseEntity(client);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching client data"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all")
    @ResponseBody
    ResponseEntity getAll(){
        List<RawClientInfoDto> rawClientInfoDtos = new ArrayList<>();
        try {
            List<Client> clients = this.clientService.getAll();
            clients.forEach(item -> rawClientInfoDtos.add(modelMapper.map(item.getClientInfoId(), RawClientInfoDto.class)));
            for(Client client : clients){
                rawClientInfoDtos.stream().filter(item -> item.getPesel().equals(client.getClientInfoId().getPesel())).findFirst().get().setId(client.getId());
            }

            JsonNode returnData = mapper.valueToTree(rawClientInfoDtos);

            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching client data"), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity getClientResponseEntity(Optional<Client> client) {
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
    }

    @PutMapping(value = "/edit")
    @ResponseBody
    ResponseEntity editClient(@RequestBody ClientDto clientDto){
        try {
            Optional<Client> originalClient = this.clientService.getByPesel(clientDto.getPesel());
            if(!originalClient.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
            else{
                DataHistoryClient dataHistoryClient = new DataHistoryClient();
                dataHistoryClient.setClientId(originalClient.get());
                dataHistoryClient.setBeforeHistory(mapper.writeValueAsString(originalClient.get().prepare()));

                ClientInfo clientInfo =  modelMapper.map(clientDto, ClientInfo.class);
                originalClient.get().getClientInfoId().assignNewValues(clientInfo);
                this.clientInfoService.save(originalClient.get().getClientInfoId());
                Location location = modelMapper.map(clientDto, Location.class);
                originalClient.get().getLocation().assignNewValues(location);
                this.locationService.saveLocation(originalClient.get().getLocation());

                if(!clientDto.getContacts().isEmpty()){
                    List<Contacts> newContacs = clientDto.getContacts().stream().filter(item -> item.getId() == null).collect(Collectors.toList());
                    List<Integer> frontIds = clientDto.getContacts().stream()
                            .filter(item -> item.getId() != null).map(item -> item.getId()).collect(Collectors.toList());
                    for(Contacts contact : originalClient.get().getContacts()){
                        if(frontIds.contains(contact.getId())){
                            contact.assignNewValues(clientDto.getContacts().stream().filter(item -> item.getId().equals(contact.getId())).findFirst().get());
                            this.contactService.saveContact(contact);
                        }
                        else
                            this.contactService.deleteContact(contact);
                    }
                    final Client tmp = originalClient.get();
                    newContacs.forEach(item -> item.setClientId(tmp));
                    newContacs.forEach(item -> this.contactService.saveContact(item));
                }
                if(!clientDto.getDocuments().isEmpty()){
                    List<Document> newDocuments = clientDto.getDocuments().stream().filter(item -> item.getId() == null).collect(Collectors.toList());
                    List<Integer> frontIds = clientDto.getDocuments().stream()
                            .filter(item -> item.getId() != null).map(item -> item.getId()).collect(Collectors.toList());
                    for(Document document : originalClient.get().getDocuments()){
                        if(frontIds.contains(document.getId())){
                            document.assignNewValues(clientDto.getDocuments().stream().filter(item -> item.getId().equals(document.getId())).findFirst().get());
                            this.documentService.saveDocument(document);
                        }
                        else
                            this.documentService.deleteDocument(document);
                    }
                    final Client tmp = originalClient.get();
                    newDocuments.forEach(item -> item.setClientId(tmp));
                    newDocuments.forEach(item -> this.documentService.saveDocument(item));
                }
                originalClient = this.clientService.getByPesel(clientDto.getPesel());
                dataHistoryClient.setAfterHistory(mapper.writeValueAsString(originalClient.get().prepare()));
                this.dataHistoryClientService.saveDataHistoryClient(dataHistoryClient);
                return new ResponseEntity<>(ResponseObject.createSuccess("Client updated"), HttpStatus.OK);
            }

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during update client"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}/status")
    @ResponseBody
    ResponseEntity deleteLockClient(@PathVariable Integer id, @RequestBody JSONObject param){
        Optional<Client> client = this.clientService.getById(id);
        if(!client.isPresent())
            return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
        try {
            if(String.valueOf(param.get("type")).toLowerCase().equals("lock")){
                client.get().setIsActive(false);
                this.clientService.saveClient(client.get());
                return new ResponseEntity<>(ResponseObject.createSuccess("Client locked"), HttpStatus.OK);
            }
            else if(String.valueOf(param.get("type")).toLowerCase().equals("delete")){
                this.clientService.deleteClient(client.get());
                return new ResponseEntity<>(ResponseObject.createSuccess("Client deleted"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(ResponseObject.createError("Invalid param"), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during " + param +" client"), HttpStatus.BAD_REQUEST);
        }
    }


}
