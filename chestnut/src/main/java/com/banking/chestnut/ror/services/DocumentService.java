package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Document;
import com.banking.chestnut.ror.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentService implements IDocumentService {

    private DocumentRepository documentRepository;

    @Autowired

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document saveDocument(Document document) {
        this.documentRepository.save(document);
        return document;
    }
}
