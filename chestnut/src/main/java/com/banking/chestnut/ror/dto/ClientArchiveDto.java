package com.banking.chestnut.ror.dto;

import com.banking.chestnut.models.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ClientArchiveDto implements Serializable {

    private Integer id;

    private String uuid;

    private Banks bankId;

    private Boolean isActive;

    private Date createdAt;

    private Date deletedAt;

    private CreditCategories creditCategoryId;

    private User introductorId;

    private ClientTypes clientTypeId;

    private ClientInfo clientInfoId;

    private User createdBy;

    private User deletedBy;

    private String clientStatus;

    private transient List<Blacklist> blacklistList;

    private LocationDto location;

    private List<Contacts> contacts;

    private List<Document> documents;

}
