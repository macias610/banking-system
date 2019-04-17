package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Document;

public interface IDocumentService {
    Document saveDocument(Document document);
    void deleteDocument(Document document);
}
