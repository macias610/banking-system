package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Documents;
import com.banking.chestnut.ror.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class DocumentService implements IDocumentService {

    private DocumentRepository documentRepository;

    @Autowired

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Documents saveDocument(Documents document) {
        this.documentRepository.save(document);
        return document;
    }
}
